@file:Suppress("DEPRECATION")

package com.example.e_library.data

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.example.e_library.models.Books
import com.example.e_library.models.CartOrder
import com.example.e_library.navigation.ROUTE_VIEW_CART_CLIENT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
        progress.setTitle("Removing from cart")
        progress.setMessage("Please Wait...")
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
                                                                // Update the book quantity and status
                                                                val newQuantity = book.bookQuantity - cartOrderBookQuantity
                                                                bookRef.child("bookQuantity").setValue(newQuantity).addOnCompleteListener {
                                                                    if (it.isSuccessful) {
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
                                val newQuantity = book.bookQuantity + cartOrder.cartOrderBookQuantity // Increment quantity
                                bookRef.child("bookQuantity").setValue(newQuantity)
                                delRef.removeValue().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        progressDelete.dismiss()
                                        Log.d("Delete Book Cart Item", "Book removed from cart")
                                        Toast.makeText(context, "Book removed from cart", Toast.LENGTH_SHORT).show()
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

    fun viewCartOrders(
        cartOrder: MutableState<CartOrder>,
        cartOrders: SnapshotStateList<CartOrder>
    ): SnapshotStateList<CartOrder> {
        val ref = FirebaseDatabase.getInstance().getReference().child("CartOrders")
        progress.show()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progress.dismiss()
                cartOrders.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(CartOrder::class.java)
                    cartOrder.value = value!!
                    cartOrders.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return cartOrders
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

    fun pickUpApproval(
        clientId: String,
        cartOrderId: String
    ){
        val cartOrderRef = FirebaseDatabase.getInstance().getReference().child("CartOrders").child(clientId).child(cartOrderId)
        cartOrderRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartOrdersSnapshot = snapshot.getValue(CartOrder::class.java)
                if (cartOrdersSnapshot == null){
                    Toast.makeText(context, "Failed to fetch cart order; null", Toast.LENGTH_SHORT).show()
                }else{
                    val newCartOrderStatus = "Checked Out"
                    cartOrderRef.child("cartOrderStatus").setValue(newCartOrderStatus)
                    Log.d("Firebase", "Cart Order Status is: $newCartOrderStatus")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch cart order status", Toast.LENGTH_SHORT).show()
            }

        })
    }

}