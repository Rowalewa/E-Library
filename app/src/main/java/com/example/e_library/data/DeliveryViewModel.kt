@file:Suppress("DEPRECATION")

package com.example.e_library.data

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.example.e_library.models.Books
import com.example.e_library.models.CartOrder
import com.example.e_library.models.Delivery
import com.example.e_library.navigation.ROUTE_COURIER_VIEW_CLIENT_DELIVERY
import com.example.e_library.navigation.ROUTE_VIEW_CART_CLIENT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

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
        val ref = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(clientId)
        val cartOrdersBooks = mutableListOf<CartOrder>()

        Log.d("CartBooks", "Client ID is: $clientId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartOrdersBooks.clear()
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
        val ref = FirebaseDatabase.getInstance().getReference().child("Delivery").child(clientId)
        val deliveryDetails = mutableListOf<Delivery>()

        Log.d("CartBooks", "Client ID is: $clientId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deliveryDetails.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Delivery::class.java)
                    value?.let {
                        deliveryDetails.add(it)
                    }
                }
                Toast.makeText(context, "Deliveries in process: ${deliveryDetails.size}", Toast.LENGTH_LONG).show()
                callback(deliveryDetails) // Invoke the callback with the fetched data
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Delivery", "Error fetching delivery books: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }


    fun removeFromCartAndDelivery(
        cartOrderId: String,
        bookId: String,
        clientId: String,
        deliveryId: String
    ){
        val delRef = FirebaseDatabase.getInstance().getReference().child("CartOrders/$clientId/$cartOrderId")
        val delRefDelivery = FirebaseDatabase.getInstance().getReference().child("Delivery/$clientId/$deliveryId")
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
                                    delRefDelivery.removeValue().addOnCompleteListener {delDelivery->
                                        if (delDelivery.isSuccessful) {
                                            progressDelete.dismiss()
                                            if (task.isSuccessful) {
                                                progressDelete.dismiss()
                                                Log.d(
                                                    "Delete Book Cart Item",
                                                    "Book removed from cart"
                                                )
                                                Toast.makeText(
                                                    context,
                                                    "Book removed from cart",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate("$ROUTE_VIEW_CART_CLIENT/$clientId")
                                            } else {
                                                progressDelete.dismiss()
                                                Log.e(
                                                    "Delete Book Cart Item",
                                                    "Error removing book from cart",
                                                    task.exception
                                                )
                                                Toast.makeText(
                                                    context,
                                                    "Error removing book: ${task.exception?.message}",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }else{
                                            progressDelete.dismiss()
                                            Toast.makeText(context, "Could not delete delivery details", Toast.LENGTH_LONG).show()
                                        }
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
                                        Log.d(
                                            "Delete Book Cart Item",
                                            "Book removed from cart"
                                        )
                                        Toast.makeText(
                                            context,
                                            "Book removed from cart",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("$ROUTE_VIEW_CART_CLIENT/$clientId")
                                    } else {
                                        progressDelete.dismiss()
                                        Log.e(
                                            "Delete Book Cart Item",
                                            "Error removing book from cart",
                                            task.exception
                                        )
                                        Toast.makeText(
                                            context,
                                            "Error removing book: ${task.exception?.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
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
        val ref = FirebaseDatabase.getInstance().getReference("CartOrders")
        val uniqueClients = mutableMapOf<String, CartOrder>()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                uniqueClients.clear()
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

    fun deliveryCourierApproval(
        clientId: String,
        cartOrderId: String,
        deliveryId: String,
        deliveryPersonnelId: String
    ){
        val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(clientId).child(cartOrderId)
        val deliveryRef = FirebaseDatabase.getInstance().getReference().child("Delivery").child(clientId).child(deliveryId)
        cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartOrdersSnapshot = snapshot.getValue(CartOrder::class.java)
                if (cartOrdersSnapshot == null){
                    Toast.makeText(context, "Failed to fetch cart order; null", Toast.LENGTH_SHORT).show()
                }else{
                    val newCartOrderStatus = "Delivered"
                    cartOrderRef.child("cartOrderStatus").setValue(newCartOrderStatus).addOnCompleteListener {cartTask->
                        if (cartTask.isSuccessful) {
                            deliveryRef.child("deliveryCartOrderStatus").setValue(newCartOrderStatus).addOnCompleteListener {deliveryStatusTask->
                                if (deliveryStatusTask.isSuccessful) {
                                    val today = LocalDate.now().toString()
                                    deliveryRef.child("deliveryArrivalDate").setValue(today).addOnCompleteListener {dateTask->
                                        if (dateTask.isSuccessful) {
                                            Log.d("Firebase", "Cart Order Status is: $newCartOrderStatus")
                                            navController.navigate("$ROUTE_COURIER_VIEW_CLIENT_DELIVERY/$clientId/$deliveryPersonnelId")
                                        }else{
                                            Toast.makeText(context, "Arrival Date could not be set to today", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }else{
                                    Toast.makeText(context, "Failed to change delivery status to delivered", Toast.LENGTH_LONG).show()
                                }
                            }
                        }else{
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
        deliveryArrivalDate: String
    ){
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
                        deliveryId
                    )

                    val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(deliveryClientId).child(deliveryCartOrderId)

                    cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val cartOrder = snapshot.getValue(CartOrder::class.java)
                            if (cartOrder != null) {
                                val newStatus = "Delivery"
                                cartOrderRef.child("cartOrderStatus").setValue(newStatus).addOnCompleteListener {statusTask->
                                    if (statusTask.isSuccessful) {
                                        val deliveryRef =
                                            FirebaseDatabase.getInstance().getReference("Delivery")
                                                .child(deliveryClientId).child(deliveryId)
                                        deliveryRef.setValue(deliveryDetailsData)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    val newQuantity = book.bookQuantity - deliveryBookQuantity
                                                    bookRef.child("bookQuantity").setValue(newQuantity).addOnCompleteListener {quantitytask->
                                                        if (quantitytask.isSuccessful) {
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


}