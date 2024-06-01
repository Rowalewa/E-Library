@file:Suppress("DEPRECATION")

package com.example.e_library.data

import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import com.example.e_library.models.Books
import com.example.e_library.models.CartOrder
import com.example.e_library.models.Clients
import com.example.e_library.models.Delivery
import com.example.e_library.models.DeliveryReturn
import com.example.e_library.navigation.ROUTE_ATTENDANT_VIEW_CLIENT_DELIVERY
import com.example.e_library.navigation.ROUTE_VIEW_CART_CLIENT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Suppress("DEPRECATION")
class DeliveryViewModel(
    var navController: NavController,
    private var context: Context
) {
    private var progress: ProgressDialog = ProgressDialog(context)
    private var progressDelete: ProgressDialog = ProgressDialog(context)
    init {
        progress.setTitle("Processing")
        progress.setMessage("Please Wait...")
        progressDelete.setTitle("Removing from cart")
        progressDelete.setMessage("Please Wait...")
    }
    fun makeCartOrder(
        cartOrderBookId: String,
        cartOrderBookTitle: String,
        cartOrderBookAuthor: String,
        cartOrderBookISBNNumber: String,
        cartOrderBookGenre: String,
        cartOrderBookPublisher: String,
        cartOrderBookSynopsis: String,
        cartOrderBookImageUrl: String,
        cartOrderBookQuantity: Int,
        cartOrderClientProfilePictureUrl: String,
        clientId: String,
        cartOrderClientFullName: String,
        cartOrderClientEmailAddress: String,
        cartOrderClientPhoneNumber: String,
        cartOrderLocationName: String,
        cartOrderLocationPrice: String,
        cartOrderLocationDistanceInKm: String,
        cartOrderDate: String,
        cartOrderStatus: String
    ){
        progress.show()
        val cartOrderId = System.currentTimeMillis().toString()
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(cartOrderBookId)
        val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId)

        // Fetch the current status of the client
        clientRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(clientSnapshot: DataSnapshot) {
                val clientStatus = clientSnapshot.child("clientStatus").getValue(String::class.java)

                if (clientStatus == "Fined" || clientStatus == "Suspended") {
                    progress.dismiss()
                    Toast.makeText(context, "Cannot add to cart, client $clientStatus.", Toast.LENGTH_LONG).show()
                    return
                } else {
                    // Fetch the current borrowed books count of the client
                    val borrowedBooksRef = FirebaseDatabase.getInstance().getReference("BorrowedBooks")
                    borrowedBooksRef.orderByChild("clientId").equalTo(clientId)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(borrowedSnapshot: DataSnapshot) {
                                val currentBorrowedCount = borrowedSnapshot.childrenCount.toInt()

                                // Define the maximum number of books a client can borrow
                                val maxBooksToBorrow = 5 // Change this to your desired maximum

                                if (currentBorrowedCount >= maxBooksToBorrow) {
                                    progress.dismiss()
                                    Toast.makeText(context, "Cannot add to cart, maximum books borrowed.", Toast.LENGTH_SHORT).show()
//                            return
                                    navController.popBackStack()
                                } else {
                                    // Fetch the current status and quantity of the book
                                    bookRef.addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            val book = snapshot.getValue(Books::class.java)
                                            if (book != null) {
                                                if (book.bookQuantity > 0) {
                                                    val cartOrderData = CartOrder(
                                                        cartOrderBookId,
                                                        cartOrderBookTitle,
                                                        cartOrderBookAuthor,
                                                        cartOrderBookISBNNumber,
                                                        cartOrderBookGenre,
                                                        cartOrderBookPublisher,
                                                        cartOrderBookSynopsis,
                                                        cartOrderBookImageUrl,
                                                        cartOrderBookQuantity,
                                                        cartOrderClientProfilePictureUrl,
                                                        clientId,
                                                        cartOrderClientFullName,
                                                        cartOrderClientEmailAddress,
                                                        cartOrderClientPhoneNumber,
                                                        cartOrderLocationName,
                                                        cartOrderLocationPrice,
                                                        cartOrderLocationDistanceInKm,
                                                        cartOrderDate,
                                                        cartOrderStatus,
                                                        cartOrderId
                                                    )

                                                    val cartOrderRef = FirebaseDatabase.getInstance().getReference("CartOrders").child(clientId).child(cartOrderId)

                                                    cartOrderRef.setValue(cartOrderData)
                                                        .addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                progress.dismiss()
                                                                Toast.makeText(context, "Book Added to cart", Toast.LENGTH_SHORT).show()
                                                                navController.navigateUp()
                                                            } else {
                                                                progress.dismiss()
                                                                Toast.makeText(context, "Failed to add book to cart", Toast.LENGTH_SHORT).show()
                                                            }
                                                        }
                                                } else {
                                                    progress.dismiss()
                                                    Toast.makeText(context, "Book is out of stock", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                progress.dismiss()
                                                Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            progress.dismiss()
                                            Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                progress.dismiss()
                                Toast.makeText(context, "Failed to fetch borrowed books", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progress.dismiss()
                Toast.makeText(context, "Failed to fetch client status", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getCartBooksForClient(
        clientId: String,
        callback: (List<CartOrder>) -> Unit
    ) {
        progress.show()
        val ref = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(clientId)
        val cartOrdersBooks = mutableListOf<CartOrder>()

        Log.d("CartBooks", "Client ID is: $clientId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartOrdersBooks.clear()
                progress.dismiss()
                for (snap in snapshot.children) {
                    val value = snap.getValue(CartOrder::class.java)
                    value?.let {
                        cartOrdersBooks.add(it)
                    }
                }
                Toast.makeText(context, "Books in cart: ${cartOrdersBooks.size}", Toast.LENGTH_LONG).show()
                callback(cartOrdersBooks) // Invoke the callback with the fetched data
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CartBooks", "Error fetching cart books: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }

    fun getDeliveryDetails(
        clientId: String,
        callback: (List<Delivery>) -> Unit
    ) {
        progress.show()
        val ref = FirebaseDatabase.getInstance().getReference().child("Delivery").child(clientId)
        val deliveryDetails = mutableListOf<Delivery>()

        Log.d("CartBooks", "Client ID is: $clientId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deliveryDetails.clear()
                progress.dismiss()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Delivery::class.java)
                    value?.let {
                        deliveryDetails.add(it)
                    }
                }
                Toast.makeText(context, "Delivery items: ${deliveryDetails.size}", Toast.LENGTH_LONG).show()
                callback(deliveryDetails) // Invoke the callback with the fetched data
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Delivery", "Error fetching delivery books: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }

    fun getDeliveryReturnDetails(
        deliveryPersonnelId: String,
        callback: (List<DeliveryReturn>) -> Unit
    ) {
        progress.show()
        val ref = FirebaseDatabase.getInstance().getReference().child("DeliveryReturn").child(deliveryPersonnelId)
        val deliveryReturnDetails = mutableListOf<DeliveryReturn>()

        Log.d("Delivery Return", "Delivery Personnel ID is: $deliveryPersonnelId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deliveryReturnDetails.clear()
                progress.dismiss()
                for (snap in snapshot.children) {
                    val value = snap.getValue(DeliveryReturn::class.java)
                    value?.let {
                        deliveryReturnDetails.add(it)
                    }
                }
                Toast.makeText(context, "Delivery items: ${deliveryReturnDetails.size}", Toast.LENGTH_LONG).show()
                callback(deliveryReturnDetails) // Invoke the callback with the fetched data
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Delivery", "Error fetching delivery books: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }


    fun removeFromCartAndDeliveryAndDeliveryReturn(
        cartOrderId: String,
        bookId: String,
        clientId: String,
        deliveryId: String,
        deliveryPersonnelId: String,
        deliveryReturnId: String,
        deliveryBookQuantity: Int
    ){
        val delRef = FirebaseDatabase.getInstance().getReference().child("CartOrders/$clientId/$cartOrderId")
        val delRefDeliveryClient = FirebaseDatabase.getInstance().getReference().child("Delivery/$clientId/$deliveryId")
        val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders/$clientId")
        val bookRef = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId)
        val deliveryReturnRef = FirebaseDatabase.getInstance().getReference("DeliveryReturn/$deliveryPersonnelId")
        val delRefDeliveryReturn = FirebaseDatabase.getInstance().getReference("DeliveryReturn/$deliveryPersonnelId/$deliveryReturnId")
        progressDelete.show()
        bookRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(bookSnapshot: DataSnapshot) {
                val book = bookSnapshot.getValue(Books::class.java)
                if (book != null) {
                    progressDelete.dismiss()
                    deliveryReturnRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val deliveryReturn = snapshot.getValue(DeliveryReturn::class.java)
                            if (deliveryReturn != null) {
                                progressDelete.dismiss()
                                Toast.makeText(context, "Processing...", Toast.LENGTH_SHORT).show()
                                if (deliveryReturn.deliveryReturnLibraryFine == 0) {
                                    progressDelete.dismiss()
                                    val newBookQuantity = book.bookQuantity + deliveryBookQuantity
                                    bookRef.child("bookQuantity").setValue(newBookQuantity).addOnCompleteListener { bookTask ->
                                        if (bookTask.isSuccessful) {
                                            Log.i("Firebase Database", "Book ID is: $bookId")
                                            Log.i("Firebase Database", "Delivery Book Quantity is: $deliveryBookQuantity")
                                            Log.i("Firebase Database", "New Book Quantity is: $newBookQuantity")
                                            delRefDeliveryReturn.removeValue().addOnCompleteListener { deliveryReturnTask ->
                                                if (deliveryReturnTask.isSuccessful) {
                                                    progressDelete.dismiss()
                                                    cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onDataChange(snapshot: DataSnapshot) {
                                                            val cartOrder = snapshot.getValue(CartOrder::class.java)
                                                            if (cartOrder != null) {
                                                                progressDelete.dismiss()
                                                                delRef.removeValue().addOnCompleteListener { task ->
                                                                    if (task.isSuccessful) {
                                                                        progressDelete.dismiss()
                                                                        delRefDeliveryClient.removeValue().addOnCompleteListener { delDelivery ->
                                                                            if (delDelivery.isSuccessful) {
                                                                                progressDelete.dismiss()
                                                                                Log.d("Delete Book Cart Item", "Book record deleted")
                                                                                Toast.makeText(context, "Book record deleted", Toast.LENGTH_SHORT).show()
                                                                                navController.navigateUp()
                                                                            } else {
                                                                                progressDelete.dismiss()
                                                                                Toast.makeText(context, "Could not delete book record.", Toast.LENGTH_SHORT).show()
                                                                            }
                                                                            }
                                                                    } else {
                                                                        progressDelete.dismiss()
                                                                        Toast.makeText(context, "Could not delete book record", Toast.LENGTH_SHORT).show()
                                                                    }
                                                                }
                                                            } else {
                                                                progressDelete.dismiss()
                                                                Toast.makeText(context, "Cart Order not found", Toast.LENGTH_SHORT).show()
                                                            }
                                                        }

                                                            override fun onCancelled(error: DatabaseError) {
                                                                progressDelete.dismiss()
                                                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                                            }
                                                        })
                                                } else {
                                                    progressDelete.dismiss()
                                                    Toast.makeText(context, "Record Deletion Failed", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }else{
                                            progressDelete.dismiss()
                                            Toast.makeText(context, "Deletion returns false, book quantity could not be changed", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }else{
                                    progressDelete.dismiss()
                                    Toast.makeText(context, "Client has a fine of: ${deliveryReturn.deliveryReturnLibraryFine}. Cannot be deleted, payment first \uD83E\uDD72.", Toast.LENGTH_LONG).show()
                                }
                            }else{
                                progressDelete.dismiss()
                                Toast.makeText(context, "Delivery Return Record not found", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            progressDelete.dismiss()
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
                }else{
                    progressDelete.dismiss()
                    Toast.makeText(context, "Book is null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun removeFromCart(
        cartOrderId: String,
        bookId: String,
        clientId: String
    ){
        val delRef = FirebaseDatabase.getInstance().getReference().child("CartOrders/$clientId/$cartOrderId")
        val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders/$clientId")
        val bookRef = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId)
        progressDelete.show()
        bookRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(bookSnapshot: DataSnapshot) {
                val book = bookSnapshot.getValue(Books::class.java)
                if (book != null) {
                    cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val cartOrder = snapshot.getValue(CartOrder::class.java)
                            if (cartOrder != null) {
                                delRef.removeValue().addOnCompleteListener { task ->
                                    progressDelete.dismiss()
                                    if (task.isSuccessful) {
                                        progressDelete.dismiss()
                                        Log.d("Delete Book Cart Item", "Book removed from cart")
                                        Toast.makeText(context, "Book removed from cart", Toast.LENGTH_SHORT).show()
                                        navController.navigate("$ROUTE_VIEW_CART_CLIENT/$clientId")
                                    } else {
                                        progressDelete.dismiss()
                                        Log.e("Delete Book Cart Item", "Error removing book from cart", task.exception)
                                        Toast.makeText(context, "Error removing book: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }else{
                                progressDelete.dismiss()
                                Toast.makeText(context, "Cart Order not found", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            progressDelete.dismiss()
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        }
                    })
                }else{
                    progressDelete.dismiss()
                    Toast.makeText(context, "Book is null", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getCartClientsForDeliveryPersonnel(
        callback: (List<CartOrder>) -> Unit
    ) {
        progress.show()
        val ref = FirebaseDatabase.getInstance().getReference("CartOrders")
        val uniqueClients = mutableMapOf<String, CartOrder>()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                uniqueClients.clear()
                progress.dismiss()
                for (clientSnapshot in snapshot.children) {
                    for (orderSnapshot in clientSnapshot.children) {
                        val cartOrder = orderSnapshot.getValue(CartOrder::class.java)
                        cartOrder?.let {
                            uniqueClients[it.clientId] = it
                        }
                    }
                }
                callback(uniqueClients.values.toList())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CartOrders", "Error fetching cart orders: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }

    fun getDeliveryClientsForAttendant(
        callback: (List<Delivery>) -> Unit
    ) {
        progress.show()
        val ref = FirebaseDatabase.getInstance().getReference("Delivery")
        val uniqueClients = mutableMapOf<String, Delivery>()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                uniqueClients.clear()
                progress.dismiss()
                for (clientSnapshot in snapshot.children) {
                    for (orderSnapshot in clientSnapshot.children) {
                        val delivery = orderSnapshot.getValue(Delivery::class.java)
                        delivery?.let {
                            uniqueClients[it.deliveryClientId] = it
                        }
                    }
                }
                callback(uniqueClients.values.toList())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CartOrders", "Error fetching cart orders: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }


    fun getDeliveryPersonnelInDelivery(
        callback: (List<DeliveryReturn>) -> Unit
    ) {
        progress.show()
        val ref = FirebaseDatabase.getInstance().getReference("DeliveryReturn")
        val uniqueCourier = mutableMapOf<String, DeliveryReturn>()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                uniqueCourier.clear()
                progress.dismiss()
                for (courierSnapshot in snapshot.children) {
                    for (orderSnapshot in courierSnapshot.children) {
                        val delivery = orderSnapshot.getValue(DeliveryReturn::class.java)
                        delivery?.let {
                            uniqueCourier[it.deliveryPersonnelId] = it
                        }
                    }
                }
                callback(uniqueCourier.values.toList())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CartOrders", "Error fetching cart orders: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }

    fun submitDeliveryDetails(
        deliveryPersonnelId: String,
        deliveryPersonnelProfilePictureUrl: String,
        deliveryPersonnelFullName: String,
        deliveryPersonnelEmailAddress: String,
        deliveryPersonnelPhoneNumber: String,
        deliveryBookId: String,
        deliveryBookTitle: String,
        deliveryBookAuthor: String,
        deliveryBookISBNNumber: String,
        deliveryBookGenre: String,
        deliveryBookPublisher: String,
        deliveryBookSynopsis: String,
        deliveryBookImageUrl: String,
        deliveryBookQuantity: Int,
        deliveryClientId: String,
        deliveryClientProfilePictureUrl: String,
        deliveryClientFullName: String,
        deliveryClientEmailAddress: String,
        deliveryClientPhoneNumber: String,
        deliveryLocationName: String,
        deliveryLocationPrice: String,
        deliveryLocationDistanceInKm: String,
        deliveryCartOrderDate: String,
        deliveryCartOrderStatus: String,
        deliveryCartOrderId: String,
        deliveryDepartureDate: String,
        deliveryArrivalDate: String,
        deliveryClientDeliveredDate: String,
        deliverySetReturnDate: String,
        deliveryReturnDate: String
    ){
        progress.show()
        val deliveryId = System.currentTimeMillis().toString()
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(deliveryBookId)
        bookRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val book = snapshot.getValue(Books::class.java)
                if (book != null) {
                    val deliveryDetailsData = Delivery(
                        deliveryPersonnelId,
                        deliveryPersonnelProfilePictureUrl,
                        deliveryPersonnelFullName,
                        deliveryPersonnelEmailAddress,
                        deliveryPersonnelPhoneNumber,
                        deliveryBookId,
                        deliveryBookTitle,
                        deliveryBookAuthor,
                        deliveryBookISBNNumber,
                        deliveryBookGenre,
                        deliveryBookPublisher,
                        deliveryBookSynopsis,
                        deliveryBookImageUrl,
                        deliveryBookQuantity,
                        deliveryClientId,
                        deliveryClientProfilePictureUrl,
                        deliveryClientFullName,
                        deliveryClientEmailAddress,
                        deliveryClientPhoneNumber,
                        deliveryLocationName,
                        deliveryLocationPrice,
                        deliveryLocationDistanceInKm,
                        deliveryCartOrderDate,
                        deliveryCartOrderStatus,
                        deliveryCartOrderId,
                        deliveryDepartureDate,
                        deliveryArrivalDate,
                        deliveryClientDeliveredDate,
                        deliverySetReturnDate,
                        deliveryReturnDate,
                        deliveryId
                    )

                    val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(deliveryClientId).child(deliveryCartOrderId)

                    cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val cartOrder = snapshot.getValue(CartOrder::class.java)
                            if (cartOrder != null) {
                                val newStatus = "Depot Delivery"
                                cartOrderRef.child("cartOrderStatus").setValue(newStatus).addOnCompleteListener {statusTask->
                                    if (statusTask.isSuccessful) {
                                        progress.dismiss()
                                        val deliveryRef = FirebaseDatabase.getInstance().getReference("Delivery").child(deliveryClientId).child(deliveryId)
                                        deliveryRef.setValue(deliveryDetailsData).addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                progress.dismiss()
                                                val newQuantity = book.bookQuantity - deliveryBookQuantity
                                                bookRef.child("bookQuantity").setValue(newQuantity).addOnCompleteListener { quantityTask ->
                                                    if (quantityTask.isSuccessful) {
                                                        progress.dismiss()
                                                        Toast.makeText(context, "Book Added to cart", Toast.LENGTH_SHORT).show()
                                                        navController.navigateUp()
                                                    } else {
                                                        progress.dismiss()
                                                        Toast.makeText(context, "Failed to add book to cart", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            } else {
                                                progress.dismiss()
                                                Toast.makeText(context, "Failed to add book to cart", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }else{
                                        Toast.makeText(context, "Failed to change status to delivery", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                Toast.makeText(context, "Cart order not found", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            progress.dismiss()
                            Toast.makeText(context, "Failed to fetch cart details", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    progress.dismiss()
                    Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progress.dismiss()
                Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // change it for use by attendant
    fun deliveryCourierApproval(
        clientId: String,
        cartOrderId: String,
        deliveryId: String,
        attendantId: String
    ){
        progress.show()
        val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(clientId).child(cartOrderId)
        val deliveryRef = FirebaseDatabase.getInstance().getReference().child("Delivery").child(clientId).child(deliveryId)
        cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartOrdersSnapshot = snapshot.getValue(CartOrder::class.java)
                if (cartOrdersSnapshot == null){
                    progress.dismiss()
                    Toast.makeText(context, "Failed to fetch cart order; null", Toast.LENGTH_SHORT).show()
                }else{
                    val newCartOrderStatus = "Depot Delivered"
                    cartOrderRef.child("cartOrderStatus").setValue(newCartOrderStatus).addOnCompleteListener {cartTask->
                        if (cartTask.isSuccessful) {
                            progress.dismiss()
                            deliveryRef.child("deliveryCartOrderStatus").setValue(newCartOrderStatus).addOnCompleteListener {deliveryStatusTask->
                                if (deliveryStatusTask.isSuccessful) {
                                    progress.dismiss()
                                    val today = LocalDate.now().toString()
                                    deliveryRef.child("deliveryArrivalDate").setValue(today).addOnCompleteListener {dateTask->
                                        if (dateTask.isSuccessful) {
                                            progress.dismiss()
                                            Toast.makeText(context, "Arrival Date successfully set to: $today", Toast.LENGTH_LONG).show()
                                            navController.navigate("$ROUTE_ATTENDANT_VIEW_CLIENT_DELIVERY/$attendantId/$clientId")
                                        }else{
                                            progress.dismiss()
                                            Toast.makeText(context, "Arrival Date could not be set to today", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }else{
                                    progress.dismiss()
                                    Toast.makeText(context, "Failed to change delivery status to delivered", Toast.LENGTH_LONG).show()
                                }
                            }
                        }else{
                            progress.dismiss()
                            Toast.makeText(context, "Failed to change cart order status to delivered", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch cart order status", Toast.LENGTH_SHORT).show()
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitDeliveryReturnDetails(
        attendantId: String,
        attendantProfilePictureUrl: String,
        attendantFullName: String,
        attendantEmail: String,
        attendantPhoneNumber: String,
        deliveryPersonnelId: String,
        deliveryPersonnelProfilePictureUrl: String,
        deliveryPersonnelFullName: String,
        deliveryPersonnelEmailAddress: String,
        deliveryPersonnelPhoneNumber: String,
        deliveryBookId: String,
        deliveryBookTitle: String,
        deliveryBookAuthor: String,
        deliveryBookISBNNumber: String,
        deliveryBookGenre: String,
        deliveryBookPublisher: String,
        deliveryBookSynopsis: String,
        deliveryBookImageUrl: String,
        deliveryBookQuantity: Int,
        deliveryClientId: String,
        deliveryClientProfilePictureUrl: String,
        deliveryClientFullName: String,
        deliveryClientEmailAddress: String,
        deliveryClientPhoneNumber: String,
        deliveryLocationName: String,
        deliveryLocationPrice: String,
        deliveryLocationDistanceInKm: String,
        deliveryCartOrderDate: String,
        deliveryCartOrderStatus: String,
        deliveryCartOrderId: String,
        deliveryDepartureDate: String,
        deliveryArrivalDate: String,
        deliveryClientDeliveredDate: String,
        deliveryId: String,
        deliverySetReturnDate: String,
        deliveryReturnDate: String,
        deliveryReturnFine: Int,
        deliveryReturnLibraryFine: Int,
        deliveryReturnDepartureDate: String,
        deliveryReturnArrivalDate: String
    ) {
        progress.show()
        val deliveryReturnId = System.currentTimeMillis().toString()
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(deliveryBookId)

        bookRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val book = snapshot.getValue(Books::class.java)
                if (book != null) {
                    val deliveryDetailsData = DeliveryReturn(
                        attendantId,
                        attendantProfilePictureUrl,
                        attendantFullName,
                        attendantEmail,
                        attendantPhoneNumber,
                        deliveryPersonnelId,
                        deliveryPersonnelProfilePictureUrl,
                        deliveryPersonnelFullName,
                        deliveryPersonnelEmailAddress,
                        deliveryPersonnelPhoneNumber,
                        deliveryBookId,
                        deliveryBookTitle,
                        deliveryBookAuthor,
                        deliveryBookISBNNumber,
                        deliveryBookGenre,
                        deliveryBookPublisher,
                        deliveryBookSynopsis,
                        deliveryBookImageUrl,
                        deliveryBookQuantity,
                        deliveryClientId,
                        deliveryClientProfilePictureUrl,
                        deliveryClientFullName,
                        deliveryClientEmailAddress,
                        deliveryClientPhoneNumber,
                        deliveryLocationName,
                        deliveryLocationPrice,
                        deliveryLocationDistanceInKm,
                        deliveryCartOrderDate,
                        deliveryCartOrderStatus,
                        deliveryCartOrderId,
                        deliveryDepartureDate,
                        deliveryArrivalDate,
                        deliveryClientDeliveredDate,
                        deliveryId,
                        deliverySetReturnDate,
                        deliveryReturnDate,
                        deliveryReturnFine,
                        deliveryReturnLibraryFine,
                        deliveryReturnDepartureDate,
                        deliveryReturnArrivalDate,
                        deliveryReturnId
                    )

                    val cartOrderRef = FirebaseDatabase.getInstance().getReference("CartOrders").child(deliveryClientId).child(deliveryCartOrderId)
                    val deliveryRef = FirebaseDatabase.getInstance().getReference("Delivery").child(deliveryClientId).child(deliveryId)
                    val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(deliveryClientId)

                    cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val cartOrder = snapshot.getValue(CartOrder::class.java)
                            if (cartOrder != null) {
                                val newStatus = "Depot Returned"
                                deliveryRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val deliveryItem = snapshot.getValue(Delivery::class.java)
                                        if (deliveryItem != null) {
                                            deliveryRef.child("deliveryCartOrderStatus").setValue(newStatus).addOnCompleteListener { deliveryStatusTask ->
                                                if (deliveryStatusTask.isSuccessful) {
                                                    cartOrderRef.child("cartOrderStatus").setValue(newStatus).addOnCompleteListener { statusTask ->
                                                        if (statusTask.isSuccessful) {
                                                            deliveryRef.child("deliveryReturnDate").setValue(deliveryReturnDate).addOnCompleteListener { dateTask ->
                                                                if (dateTask.isSuccessful) {
                                                                    clientRef.addListenerForSingleValueEvent(object : ValueEventListener{
                                                                        override fun onDataChange(snapshot: DataSnapshot) {
                                                                            val client = snapshot.getValue(Clients::class.java)
                                                                            if (client != null) {
                                                                                val setReturnDate = LocalDate.parse(deliveryItem.deliverySetReturnDate)
                                                                                val today = LocalDate.now()
                                                                                val daysLate = setReturnDate.until(today, ChronoUnit.DAYS)
                                                                                val returnFine = if (daysLate > 0) {
                                                                                    daysLate * 2.0
                                                                                } else {
                                                                                    0.0
                                                                                }
                                                                                /* Calculate fine (e.g., $1 per day late) */
                                                                                val newClientStatus = if (returnFine > 0) { "Fined" } else { "Active" }

                                                                                val newReturnFine = client.fine + returnFine
                                                                                clientRef.child("fine").setValue(newReturnFine).addOnCompleteListener { fineTask ->
                                                                                    if (fineTask.isSuccessful) {
                                                                                        progress.dismiss()
                                                                                        Log.d("Firebase", "New fine for client is: $newReturnFine")
                                                                                        clientRef.child("clientStatus").setValue(newClientStatus).addOnCompleteListener { clientStatusTask ->
                                                                                            if (clientStatusTask.isSuccessful) {
                                                                                                progress.dismiss()
                                                                                                Log.d("Firebase", "New status for client is: $newClientStatus")
                                                                                                val deliveryReturnRef = FirebaseDatabase.getInstance().getReference("DeliveryReturn").child(deliveryPersonnelId).child(deliveryReturnId)
                                                                                                deliveryReturnRef.setValue(deliveryDetailsData).addOnCompleteListener { task ->
                                                                                                    progress.dismiss()
                                                                                                    if (task.isSuccessful) {
                                                                                                        progress.dismiss()
                                                                                                        deliveryRef.child("deliveryReturnFine").setValue(returnFine).addOnCompleteListener { returnFineTask ->
                                                                                                            if (returnFineTask.isSuccessful) {
                                                                                                                progress.dismiss()
                                                                                                                Toast.makeText(context, "Book Returned to Pickup point successfully", Toast.LENGTH_SHORT).show()
                                                                                                                navController.navigateUp()
                                                                                                            }else{
                                                                                                                progress.dismiss()
                                                                                                                Toast.makeText(context, "Failed to set fine for delivery return.", Toast.LENGTH_LONG).show()
                                                                                                            }
                                                                                                        }
                                                                                                    } else {
                                                                                                        progress.dismiss()
                                                                                                        Toast.makeText(context, "Failed to process return to pickup point", Toast.LENGTH_SHORT).show()
                                                                                                    }
                                                                                                }
                                                                                            }else{
                                                                                                progress.dismiss()
                                                                                                Toast.makeText(context, "Failed to set client Status to: $newClientStatus", Toast.LENGTH_LONG).show()
                                                                                            }
                                                                                        }
                                                                                    }else{
                                                                                        progress.dismiss()
                                                                                        Toast.makeText(context, "New Fine for client is $newReturnFine", Toast.LENGTH_LONG).show()
                                                                                    }
                                                                                }
                                                                            }else{
                                                                                progress.dismiss()
                                                                                Toast.makeText(context, "Client Not Found", Toast.LENGTH_LONG).show()
                                                                            }
                                                                        }

                                                                        override fun onCancelled(error: DatabaseError) {
                                                                            progress.dismiss()
                                                                            Toast.makeText(context, "Failed to fetch client details", Toast.LENGTH_SHORT).show()
                                                                        }

                                                                    })
                                                                }else {
                                                                    progress.dismiss()
                                                                    Toast.makeText(context, "Return Date could not be set in the delivery node", Toast.LENGTH_LONG).show()
                                                                }
                                                            }
                                                        } else {
                                                            progress.dismiss()
                                                            Toast.makeText(context, "Failed to change status to delivery", Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                } else {
                                                    progress.dismiss()
                                                    Toast.makeText(context, "Delivery Status could not be changed", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        } else {
                                            progress.dismiss()
                                            Toast.makeText(context, "Delivery Item not found", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        progress.dismiss()
                                        Toast.makeText(context, "Failed to fetch delivery details", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            } else {
                                progress.dismiss()
                                Toast.makeText(context, "Cart order not found", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            progress.dismiss()
                            Toast.makeText(context, "Failed to fetch cart details", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    progress.dismiss()
                    Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progress.dismiss()
                Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
            }
        })
    }



    fun clientDeliveredApproval(
        clientId: String,
        cartOrderId: String,
        deliveryId: String,
        attendantId: String
    ){
        progress.show()
        val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(clientId).child(cartOrderId)
        val deliveryRef = FirebaseDatabase.getInstance().getReference().child("Delivery").child(clientId).child(deliveryId)
        cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartOrdersSnapshot = snapshot.getValue(CartOrder::class.java)
                if (cartOrdersSnapshot == null){
                    progress.dismiss()
                    Toast.makeText(context, "Failed to fetch cart order; null", Toast.LENGTH_SHORT).show()
                }else{
                    val newCartOrderStatus = "Client Delivered"
                    cartOrderRef.child("cartOrderStatus").setValue(newCartOrderStatus).addOnCompleteListener {cartTask->
                        if (cartTask.isSuccessful) {
                            progress.dismiss()
                            deliveryRef.child("deliveryCartOrderStatus").setValue(newCartOrderStatus).addOnCompleteListener {deliveryStatusTask->
                                if (deliveryStatusTask.isSuccessful) {
                                    progress.dismiss()
                                    val today = LocalDate.now().toString()
                                    deliveryRef.child("deliveryClientDeliveredDate").setValue(today).addOnCompleteListener { dateTask->
                                        if (dateTask.isSuccessful) {
                                            progress.dismiss()
                                            Log.d("Firebase", "Cart Order Status is: $newCartOrderStatus")
                                            val deliverySetReturnDate = calculateDeliverySetReturnDate(today)
                                            Log.d("Firebase", "Delivery Set Return Date is: $deliverySetReturnDate")
                                            deliveryRef.child("deliverySetReturnDate").setValue(deliverySetReturnDate).addOnCompleteListener {deliveryReturnDateTask->
                                                if (deliveryReturnDateTask.isSuccessful) {
                                                    progress.dismiss()
                                                    Toast.makeText(context, "Delivery Set Return Date successfully set to: $deliverySetReturnDate", Toast.LENGTH_LONG).show()
                                                    navController.navigate("$ROUTE_ATTENDANT_VIEW_CLIENT_DELIVERY/$attendantId/$clientId")
                                                }else{
                                                    progress.dismiss()
                                                    Toast.makeText(context, "Delivery Return Date could not be set", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }else{
                                            progress.dismiss()
                                            Toast.makeText(context, "Arrival Date could not be set to today", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }else{
                                    progress.dismiss()
                                    Toast.makeText(context, "Failed to change delivery status to delivered", Toast.LENGTH_LONG).show()
                                }
                            }
                        }else{
                            progress.dismiss()
                            Toast.makeText(context, "Failed to change cart order status to delivered", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch cart order status", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun libraryDeliveryApproval(
        clientId: String,
        cartOrderId: String,
        deliveryId: String,
        attendantId: String,
        deliveryPersonnelId: String,
        deliveryReturnId: String
    ){
        progress.show()
        val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(clientId).child(cartOrderId)
        val deliveryRef = FirebaseDatabase.getInstance().getReference().child("Delivery").child(clientId).child(deliveryId)
        val deliveryReturnRef = FirebaseDatabase.getInstance().getReference().child("DeliveryReturn").child(deliveryPersonnelId).child(deliveryReturnId)
        cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartOrdersSnapshot = snapshot.getValue(CartOrder::class.java)
                if (cartOrdersSnapshot == null){
                    progress.dismiss()
                    Toast.makeText(context, "Failed to fetch cart order; null", Toast.LENGTH_SHORT).show()
                }else{
                    val newStatus = "Library Delivery"
                    cartOrderRef.child("cartOrderStatus").setValue(newStatus).addOnCompleteListener { cartTask->
                        if (cartTask.isSuccessful) {
                            progress.dismiss()
                            deliveryRef.child("deliveryCartOrderStatus").setValue(newStatus).addOnCompleteListener { deliveryStatusTask->
                                if (deliveryStatusTask.isSuccessful) {
                                    progress.dismiss()
                                    val today = LocalDate.now().toString()
                                    progress.dismiss()
                                    Log.d("Firebase", "Delivery Return Departure Date is: $today")
                                    deliveryReturnRef.child("deliveryReturnDepartureDate").setValue(today).addOnCompleteListener {deliveryReturnDepartureDateTask->
                                        if (deliveryReturnDepartureDateTask.isSuccessful) {
                                            progress.dismiss()
                                            deliveryReturnRef.child("deliveryCartOrderStatus").setValue(newStatus).addOnCompleteListener { statusDeliveryReturnTask ->
                                                progress.dismiss()
                                                if (statusDeliveryReturnTask.isSuccessful) {
                                                    progress.dismiss()
                                                    Toast.makeText(context, "Delivery Return Departure Date successfully set to: $today", Toast.LENGTH_LONG).show()
                                                    navController.navigate("$ROUTE_ATTENDANT_VIEW_CLIENT_DELIVERY/$attendantId/$clientId")
                                                }else{
                                                    progress.dismiss()
                                                    Toast.makeText(context, "Delivery Status for return not set", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }else{
                                            progress.dismiss()
                                            Toast.makeText(context, "Delivery Return Date could not be set", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }else{
                                    progress.dismiss()
                                    Toast.makeText(context, "Failed to change delivery status to delivered", Toast.LENGTH_LONG).show()
                                }
                            }
                        }else{
                            progress.dismiss()
                            Toast.makeText(context, "Failed to change cart order status to delivered", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch cart order status", Toast.LENGTH_SHORT).show()
            }

        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateDeliverySetReturnDate(deliveryClientDeliveredDate: String): String {
        // Implement my logic to calculate the return date based on the delivered date
        val clientDeliveredDateFormatted = LocalDate.parse(deliveryClientDeliveredDate)
        val deliverySetReturnDate = clientDeliveredDateFormatted.plusDays(16)  // Adding 16 days
        return deliverySetReturnDate.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitDeliveryLibraryReturnDetails(
        attendantId: String,
        attendantProfilePictureUrl: String,
        attendantFullName: String,
        attendantEmail: String,
        attendantPhoneNumber: String,
        deliveryPersonnelId: String,
        deliveryPersonnelProfilePictureUrl: String,
        deliveryPersonnelFullName: String,
        deliveryPersonnelEmailAddress: String,
        deliveryPersonnelPhoneNumber: String,
        deliveryBookId: String,
        deliveryBookTitle: String,
        deliveryBookAuthor: String,
        deliveryBookISBNNumber: String,
        deliveryBookGenre: String,
        deliveryBookPublisher: String,
        deliveryBookSynopsis: String,
        deliveryBookImageUrl: String,
        deliveryBookQuantity: Int,
        deliveryClientId: String,
        deliveryClientProfilePictureUrl: String,
        deliveryClientFullName: String,
        deliveryClientEmailAddress: String,
        deliveryClientPhoneNumber: String,
        deliveryLocationName: String,
        deliveryLocationPrice: String,
        deliveryLocationDistanceInKm: String,
        deliveryCartOrderDate: String,
        deliveryCartOrderStatus: String,
        deliveryCartOrderId: String,
        deliveryDepartureDate: String,
        deliveryArrivalDate: String,
        deliveryClientDeliveredDate: String,
        deliveryId: String,
        deliverySetReturnDate: String,
        deliveryReturnDate: String,
        deliveryReturnFine: Int,
        deliveryReturnLibraryFine: Int,
        deliveryReturnDepartureDate: String,
        deliveryReturnArrivalDate: String,
        deliveryReturnId: String
    ) {
        progress.show()
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(deliveryBookId)

        bookRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val book = snapshot.getValue(Books::class.java)
                if (book != null) {
                    val deliveryDetailsData = DeliveryReturn(
                        attendantId,
                        attendantProfilePictureUrl,
                        attendantFullName,
                        attendantEmail,
                        attendantPhoneNumber,
                        deliveryPersonnelId,
                        deliveryPersonnelProfilePictureUrl,
                        deliveryPersonnelFullName,
                        deliveryPersonnelEmailAddress,
                        deliveryPersonnelPhoneNumber,
                        deliveryBookId,
                        deliveryBookTitle,
                        deliveryBookAuthor,
                        deliveryBookISBNNumber,
                        deliveryBookGenre,
                        deliveryBookPublisher,
                        deliveryBookSynopsis,
                        deliveryBookImageUrl,
                        deliveryBookQuantity,
                        deliveryClientId,
                        deliveryClientProfilePictureUrl,
                        deliveryClientFullName,
                        deliveryClientEmailAddress,
                        deliveryClientPhoneNumber,
                        deliveryLocationName,
                        deliveryLocationPrice,
                        deliveryLocationDistanceInKm,
                        deliveryCartOrderDate,
                        deliveryCartOrderStatus,
                        deliveryCartOrderId,
                        deliveryDepartureDate,
                        deliveryArrivalDate,
                        deliveryClientDeliveredDate,
                        deliveryId,
                        deliverySetReturnDate,
                        deliveryReturnDate,
                        deliveryReturnFine,
                        deliveryReturnLibraryFine,
                        deliveryReturnDepartureDate,
                        deliveryReturnArrivalDate,
                        deliveryReturnId
                    )

                    val cartOrderRef = FirebaseDatabase.getInstance().getReference("CartOrders").child(deliveryClientId).child(deliveryCartOrderId)
                    val deliveryRef = FirebaseDatabase.getInstance().getReference("Delivery").child(deliveryClientId).child(deliveryId)
                    val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(deliveryClientId)
                    val deliveryReturn = FirebaseDatabase.getInstance().getReference("DeliveryReturn").child(deliveryPersonnelId).child(deliveryReturnId)

                    cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val cartOrder = snapshot.getValue(CartOrder::class.java)
                            if (cartOrder != null) {
                                val newStatus = "Library Returned"
                                deliveryRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val deliveryItem = snapshot.getValue(Delivery::class.java)
                                        if (deliveryItem != null) {
                                            deliveryRef.child("deliveryCartOrderStatus").setValue(newStatus).addOnCompleteListener { deliveryStatusTask ->
                                                if (deliveryStatusTask.isSuccessful) {
                                                    cartOrderRef.child("cartOrderStatus").setValue(newStatus).addOnCompleteListener { statusTask ->
                                                        if (statusTask.isSuccessful) {
                                                            deliveryRef.child("deliveryReturnDate").setValue(deliveryReturnDate).addOnCompleteListener { dateTask ->
                                                                if (dateTask.isSuccessful) {
                                                                    clientRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                                                        override fun onDataChange(snapshot: DataSnapshot) {
                                                                            val client = snapshot.getValue(Clients::class.java)
                                                                            if (client != null) {
                                                                                if (deliveryReturnLibraryFine > 0) {
                                                                                    val newClientStatus = "Fined"
                                                                                    val newReturnFine = client.fine + deliveryReturnLibraryFine
                                                                                    Log.d("Firebase Database", "Delivery Return Library Fine is: $deliveryReturnLibraryFine")
                                                                                    Log.d("Firebase Database", "Total fine is: $newReturnFine")
                                                                                    clientRef.child("fine").setValue(newReturnFine).addOnCompleteListener { fineTask ->
                                                                                        if (fineTask.isSuccessful) {
                                                                                            progress.dismiss()
                                                                                            Log.d("Firebase", "New fine for client is: $newReturnFine")
                                                                                            clientRef.child("clientStatus").setValue(newClientStatus).addOnCompleteListener { clientStatusTask ->
                                                                                                if (clientStatusTask.isSuccessful) {
                                                                                                    progress.dismiss()
                                                                                                    Log.d("Firebase", "New status for client is: $newClientStatus")
                                                                                                    val deliveryReturnRef = FirebaseDatabase.getInstance().getReference("DeliveryReturn").child(deliveryPersonnelId).child(deliveryReturnId)
                                                                                                    deliveryReturnRef.setValue(deliveryDetailsData).addOnCompleteListener { task ->
                                                                                                            progress.dismiss()
                                                                                                            if (task.isSuccessful) {
                                                                                                                progress.dismiss()
                                                                                                                deliveryReturn.child("deliveryCartOrderStatus").setValue(newStatus).addOnCompleteListener { deliveryReturnStatusTask ->
                                                                                                                    if (deliveryReturnStatusTask.isSuccessful) {
                                                                                                                        progress.dismiss()
                                                                                                                        Toast.makeText(context, "Book Returned to library successfully", Toast.LENGTH_SHORT).show()
                                                                                                                        navController.navigateUp()
                                                                                                                    }else{
                                                                                                                        progress.dismiss()
                                                                                                                        Toast.makeText(context, "Unable to process status to library delivered on library return node", Toast.LENGTH_LONG).show()
                                                                                                                    }
                                                                                                                }
                                                                                                            } else {
                                                                                                                progress.dismiss()
                                                                                                                Toast.makeText(context, "Failed to process return to pickup point", Toast.LENGTH_SHORT).show()
                                                                                                            }
                                                                                                        }
                                                                                                } else {
                                                                                                    progress.dismiss()
                                                                                                    Toast.makeText(context, "Failed to set client Status to: $newClientStatus", Toast.LENGTH_LONG).show()
                                                                                                }
                                                                                            }
                                                                                        } else {
                                                                                            progress.dismiss()
                                                                                            Toast.makeText(context, "New Fine for client is $newReturnFine", Toast.LENGTH_LONG).show()
                                                                                        }
                                                                                    }
                                                                                }else if (deliveryReturnLibraryFine == 0){
                                                                                    val newClientStatus = "Active"
                                                                                    val newReturnFine = client.fine + deliveryReturnFine
                                                                                    Log.d("Firebase Database", "Delivery Return Fine is: $deliveryReturnFine")
                                                                                    Log.d("Firebase Database", "Total fine is: $newReturnFine")
                                                                                    clientRef.child("fine").setValue(newReturnFine).addOnCompleteListener { fineTask ->
                                                                                        if (fineTask.isSuccessful) {
                                                                                            progress.dismiss()
                                                                                            Log.d("Firebase", "New fine for client is: $newReturnFine")
                                                                                            clientRef.child("clientStatus").setValue(newClientStatus).addOnCompleteListener { clientStatusTask ->
                                                                                                if (clientStatusTask.isSuccessful) {
                                                                                                    progress.dismiss()
                                                                                                    Log.d("Firebase", "New status for client is: $newClientStatus")
                                                                                                    val deliveryReturnRef = FirebaseDatabase.getInstance().getReference("DeliveryReturn").child(deliveryPersonnelId).child(deliveryReturnId)
                                                                                                    deliveryReturnRef.setValue(deliveryDetailsData).addOnCompleteListener { task ->
                                                                                                        progress.dismiss()
                                                                                                        if (task.isSuccessful) {
                                                                                                            progress.dismiss()
                                                                                                            deliveryReturn.child("deliveryCartOrderStatus").setValue(newStatus).addOnCompleteListener { deliveryReturnStatusTask ->
                                                                                                                if (deliveryReturnStatusTask.isSuccessful) {
                                                                                                                    progress.dismiss()
                                                                                                                    Toast.makeText(context, "Book Returned to library successfully", Toast.LENGTH_SHORT).show()
                                                                                                                    navController.navigateUp()
                                                                                                                }else{
                                                                                                                    progress.dismiss()
                                                                                                                    Toast.makeText(context, "Unable to process status to library delivered on library return node", Toast.LENGTH_LONG).show()
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            progress.dismiss()
                                                                                                            Toast.makeText(context, "Failed to process return to library", Toast.LENGTH_SHORT).show()
                                                                                                        }
                                                                                                    }
                                                                                                } else {
                                                                                                    progress.dismiss()
                                                                                                    Toast.makeText(context, "Failed to set client Status to: $newClientStatus", Toast.LENGTH_LONG).show()
                                                                                                }
                                                                                            }
                                                                                        } else {
                                                                                            progress.dismiss()
                                                                                            Toast.makeText(context, "New Fine for client is $newReturnFine", Toast.LENGTH_LONG).show()
                                                                                        }
                                                                                    }
                                                                                }else{
                                                                                    progress.dismiss()
                                                                                    Toast.makeText(context, "You have entered invalid library fine data, Is it an Integer?", Toast.LENGTH_LONG).show()
                                                                                }
                                                                            } else {
                                                                                progress.dismiss()
                                                                                Toast.makeText(context, "Client Not Found", Toast.LENGTH_LONG).show()
                                                                            }
                                                                        }

                                                                        override fun onCancelled(error: DatabaseError) {
                                                                            progress.dismiss()
                                                                            Toast.makeText(context, "Failed to fetch client details", Toast.LENGTH_SHORT).show()
                                                                        }

                                                                    })
                                                                } else {
                                                                    progress.dismiss()
                                                                    Toast.makeText(context, "Return Date could not be set in the delivery node", Toast.LENGTH_LONG).show()
                                                                }
                                                            }
                                                        } else {
                                                            progress.dismiss()
                                                            Toast.makeText(context, "Failed to change status to delivery", Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                } else {
                                                    progress.dismiss()
                                                    Toast.makeText(context, "Delivery Status could not be changed", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        } else {
                                            progress.dismiss()
                                            Toast.makeText(context, "Delivery Item not found", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        progress.dismiss()
                                        Toast.makeText(context, "Failed to fetch delivery details", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            } else {
                                progress.dismiss()
                                Toast.makeText(context, "Cart order not found", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            progress.dismiss()
                            Toast.makeText(context, "Failed to fetch cart details", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    progress.dismiss()
                    Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progress.dismiss()
                Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
            }
        })
    }

}