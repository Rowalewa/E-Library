@file:Suppress("DEPRECATION")

package com.example.e_library.data

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import com.example.e_library.models.Books
import com.example.e_library.models.BorrowingBook
import com.example.e_library.models.CartOrder
import com.example.e_library.models.Clients
import com.example.e_library.navigation.ROUTE_VIEW_CLIENT_BORROWS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Suppress("DEPRECATION")
class TransactionViewModel(
    var navController: NavHostController,
    var context: Context
) {
    private var progressLoad: ProgressDialog = ProgressDialog(context)

    init {
        progressLoad.setTitle("Loading")
        progressLoad.setTitle("Please wait...")
    }

    fun borrowBook(
        bookId: String,
        clientId: String,
        bookTitle: String,
        bookAuthor: String,
        bookISBNNumber: String,
        bookGenre: String,
        bookPublisher: String,
        bookSynopsis: String,
        bookImageUrl: String,
        borrowDate: String,
        returnDate: String,
        borrowMeans: String,
        borrowStatus: String
    ) {
        progressLoad.show()
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId)
        val borrowId = System.currentTimeMillis().toString()

        // Fetch the current status of the client
        if (borrowDate.isNotBlank() || returnDate.isNotBlank()) {
            clientRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(clientSnapshot: DataSnapshot) {
                    val clientStatus =
                        clientSnapshot.child("clientStatus").getValue(String::class.java)

                    if (clientStatus == "Fined" || clientStatus == "Suspended") {
                        progressLoad.dismiss()
                        Toast.makeText(
                            context,
                            "Cannot borrow, client $clientStatus.",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    } else {

                        // Fetch the current borrowed books count of the client
                        val borrowedBooksRef =
                            FirebaseDatabase.getInstance().getReference("BorrowedBooks")
                        borrowedBooksRef.orderByChild("clientId").equalTo(clientId)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(borrowedSnapshot: DataSnapshot) {
                                    val currentBorrowedCount =
                                        borrowedSnapshot.childrenCount.toInt()

                                    // Define the maximum number of books a client can borrow
                                    val maxBooksToBorrow = 3 // Change this to your desired maximum

                                    if (currentBorrowedCount >= maxBooksToBorrow) {
                                        progressLoad.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Cannot borrow, maximum books borrowed.",
                                            Toast.LENGTH_SHORT
                                        ).show()
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
                                                        val borrowedBookData = BorrowingBook(
                                                            clientId,
                                                            bookId,
                                                            bookTitle,
                                                            bookAuthor,
                                                            bookISBNNumber,
                                                            bookGenre,
                                                            bookPublisher,
                                                            bookSynopsis,
                                                            bookImageUrl,
                                                            borrowDate,
                                                            returnDate,
                                                            borrowMeans,
                                                            borrowStatus,
                                                            borrowId
                                                        )

                                                        val borrowedRef = FirebaseDatabase.getInstance().getReference("BorrowedBooks").child(clientId).child(borrowId)

                                                        borrowedRef.setValue(borrowedBookData)
                                                            .addOnCompleteListener { task ->
                                                                if (task.isSuccessful) {
                                                                    // Update the book quantity and status
                                                                    val newQuantity =
                                                                        book.bookQuantity - 1
                                                                    bookRef.child("bookQuantity")
                                                                        .setValue(newQuantity)
                                                                    bookRef.child("bookStatus")
                                                                        .setValue("Borrowed")
                                                                        .addOnCompleteListener {
                                                                            if (it.isSuccessful) {
                                                                                progressLoad.dismiss()
                                                                                Toast.makeText(
                                                                                    context,
                                                                                    "Book successfully borrowed",
                                                                                    Toast.LENGTH_SHORT
                                                                                ).show()
                                                                                navController.navigateUp()
                                                                            } else {
                                                                                progressLoad.dismiss()
                                                                                Toast.makeText(
                                                                                    context,
                                                                                    "Failed to update book status",
                                                                                    Toast.LENGTH_SHORT
                                                                                ).show()
                                                                            }
                                                                        }
                                                                } else {
                                                                    progressLoad.dismiss()
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Failed to borrow book",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }
                                                    } else {
                                                        progressLoad.dismiss()
                                                        Toast.makeText(
                                                            context,
                                                            "Book is out of stock",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                } else {
                                                    progressLoad.dismiss()
                                                    Toast.makeText(
                                                        context,
                                                        "Failed to fetch book details",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                progressLoad.dismiss()
                                                Toast.makeText(
                                                    context,
                                                    "Failed to fetch book details",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        })
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    progressLoad.dismiss()
                                    Toast.makeText(
                                        context,
                                        "Failed to fetch borrowed books",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    progressLoad.dismiss()
                    Toast.makeText(context, "Failed to fetch client status", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }else{
            progressLoad.dismiss()
            Toast.makeText(context, "You have to fill in both the return and borrow dates", Toast.LENGTH_LONG).show()
            return
        }
    }

    fun returnBook(
        clientId: String,
        bookId: String,
        borrowId: String
    ) {
        progressLoad.show()
        val borrowedRef = FirebaseDatabase.getInstance().getReference().child("BorrowedBooks").child(clientId)
        borrowedRef.orderByChild("bookId").equalTo(bookId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val borrowedBook = childSnapshot.getValue(BorrowingBook::class.java)
                    if (borrowedBook != null && borrowedBook.clientId == clientId) {
                        if (borrowedBook.borrowDate.isNotBlank()  && borrowedBook.returnDate.isNotBlank()) {
                            Log.d("FirebaseDatabase", "Borrow id is: ${borrowedBook.borrowId}")
                            val bookRef =
                                FirebaseDatabase.getInstance().getReference().child("Books")
                                    .child(bookId)
                            bookRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                @RequiresApi(Build.VERSION_CODES.O)
                                override fun onDataChange(bookSnapshot: DataSnapshot) {
                                    val book = bookSnapshot.getValue(Books::class.java)
                                    if (book != null) {
                                        val dueDate =
                                            LocalDate.parse(borrowedBook.returnDate)
                                        val today = LocalDate.now()
                                        val daysLate = dueDate.until(today, ChronoUnit.DAYS)
                                        val fine = if (daysLate > 0) {
                                            // Calculate fine (e.g., $1 per day late)
                                            daysLate * 2.0
                                        } else {
                                            0.0
                                        }

                                        val newQuantity =
                                            book.bookQuantity + 1 // Increment quantity
                                        bookRef.child("bookQuantity").setValue(newQuantity)
                                        val clientRef =
                                            FirebaseDatabase.getInstance().getReference("Client")
                                                .child(clientId)
                                        clientRef.addListenerForSingleValueEvent(object :
                                            ValueEventListener {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                val client = snapshot.getValue(Clients::class.java)
                                                if (client != null) {
                                                    val newFine = client.fine + fine
                                                    val newClientStatus = if (fine > 0) {
                                                        "Fined"
                                                    } else {
                                                        "Active"
                                                    }

                                                    clientRef.child("fine").setValue(newFine)

                                                    if (client.clientStatus != newClientStatus) {
                                                        progressLoad.dismiss()
                                                        clientRef.child("clientStatus")
                                                            .setValue(newClientStatus)
                                                        Toast.makeText(
                                                            context,
                                                            "Client Status changed to Fined",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                } else {
                                                    progressLoad.dismiss()
                                                    Log.e(
                                                        ContentValues.TAG,
                                                        "Client not found for ID: $clientId"
                                                    )
                                                    Toast.makeText(
                                                        context,
                                                        "Client not found for id: $clientId",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                progressLoad.dismiss()
                                                Toast.makeText(
                                                    context,
                                                    "Failed to read client data",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                Log.e(
                                                    ContentValues.TAG,
                                                    "Failed to read client data.",
                                                    error.toException()
                                                )
                                            }
                                        })

                                        childSnapshot.ref.removeValue()
                                        progressLoad.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Book successfully returned. Fine: Ksh.$fine",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigateUp()
                                    } else {
                                        progressLoad.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Failed to return book: Book details not found",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    progressLoad.dismiss()
                                    Toast.makeText(
                                        context,
                                        "Failed to return book: ${error.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                        }else{
                            Toast.makeText(context, "Book cannot be returned.\n" +
                                    " No borrow date or return date. \n" +
                                    "Delivery Not Approved.", Toast.LENGTH_LONG).show()
                        }
                        return
                    }
                }
                progressLoad.dismiss()
                Toast.makeText(context, "Failed to return book: Book not borrowed by client", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                progressLoad.dismiss()
                Toast.makeText(context, "Failed to return book: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBorrowedBooksForClient(
        clientId: String,
        callback: (List<BorrowingBook>) -> Unit
    ) {
        val ref = FirebaseDatabase.getInstance().getReference().child("BorrowedBooks").child(clientId)
        val borrowedBooks = mutableListOf<BorrowingBook>()
        val borrowedBooksClient = mutableListOf<BorrowingBook>() // Separate list for Client borrowed books
        val borrowedBooksStaff = mutableListOf<BorrowingBook>()  // Separate list for Staff borrowed books

        Log.d("BorrowedBooks", "Client ID is: $clientId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                borrowedBooks.clear()
                borrowedBooksClient.clear()
                borrowedBooksStaff.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(BorrowingBook::class.java)
                    value?.let {
                        borrowedBooks.add(it)
                        when (it.borrowMeans) {
                            "Client" -> borrowedBooksClient.add(it)
                            "Staff" -> borrowedBooksStaff.add(it)
                            else -> Toast.makeText(context, "Unknown borrow means: ${it.borrowMeans}", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                val toastMessage = "Client borrowed books: ${borrowedBooksClient.size}, Staff borrowed books: ${borrowedBooksStaff.size}"
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()

                Log.d("BorrowedBooks", "Fetched ${borrowedBooks.size} books for client $clientId")
                callback(borrowedBooks) // Invoke the callback with the fetched data
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("BorrowedBooks", "Error fetching borrowed books: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }

    fun borrowBookClient(
        bookId: String,
        clientId: String,
        bookTitle: String,
        bookAuthor: String,
        bookISBNNumber: String,
        bookGenre: String,
        bookPublisher: String,
        bookSynopsis: String,
        bookImageUrl: String,
        borrowDate: String,
        returnDate: String,
        borrowMeans: String,
        borrowStatus: String
    ) {
        progressLoad.show()
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId)
        val borrowId = System.currentTimeMillis().toString()

        // Fetch the current status of the client
        clientRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(clientSnapshot: DataSnapshot) {
                val clientStatus =
                    clientSnapshot.child("clientStatus").getValue(String::class.java)

                if (clientStatus == "Fined" || clientStatus == "Suspended") {
                    progressLoad.dismiss()
                    Toast.makeText(context, "Cannot borrow, client $clientStatus.", Toast.LENGTH_LONG).show()
                    return
                } else {

                    // Fetch the current borrowed books count of the client
                    val borrowedBooksRef = FirebaseDatabase.getInstance().getReference("BorrowedBooks")
                    borrowedBooksRef.orderByChild("clientId").equalTo(clientId)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(borrowedSnapshot: DataSnapshot) {
                                val currentBorrowedCount =
                                    borrowedSnapshot.childrenCount.toInt()

                                // Define the maximum number of books a client can borrow
                                val maxBooksToBorrow = 3 // Change this to your desired maximum

                                if (currentBorrowedCount >= maxBooksToBorrow) {
                                    progressLoad.dismiss()
                                    Toast.makeText(context, "Cannot borrow, maximum books borrowed.", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                    return
                                } else {
                                    // Fetch the current status and quantity of the book
                                    bookRef.addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            val book = snapshot.getValue(Books::class.java)
                                            if (book != null) {
                                                if (book.bookQuantity > 0) {
                                                    val borrowedBookData = BorrowingBook(
                                                        clientId,
                                                        bookId,
                                                        bookTitle,
                                                        bookAuthor,
                                                        bookISBNNumber,
                                                        bookGenre,
                                                        bookPublisher,
                                                        bookSynopsis,
                                                        bookImageUrl,
                                                        borrowDate,
                                                        returnDate,
                                                        borrowMeans,
                                                        borrowStatus,
                                                        borrowId
                                                    )

                                                    val borrowedRef = FirebaseDatabase.getInstance().getReference("BorrowedBooks").child(clientId).child(borrowId)
                                                    borrowedRef.setValue(borrowedBookData)
                                                        .addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                // Update the book quantity and status
                                                                val newQuantity =
                                                                    book.bookQuantity - 1
                                                                bookRef.child("bookQuantity").setValue(newQuantity)
                                                                bookRef.child("bookStatus").setValue("Borrowed").addOnCompleteListener {
                                                                        if (it.isSuccessful) {
                                                                            progressLoad.dismiss()
                                                                            Toast.makeText(context, "Book successfully borrowed", Toast.LENGTH_SHORT).show()
                                                                            navController.navigateUp()
                                                                        } else {
                                                                            progressLoad.dismiss()
                                                                            Toast.makeText(context, "Failed to update book status", Toast.LENGTH_SHORT).show()
                                                                        }
                                                                    }
                                                            } else {
                                                                progressLoad.dismiss()
                                                                Toast.makeText(context, "Failed to borrow book", Toast.LENGTH_SHORT).show()
                                                            }
                                                        }
                                                } else {
                                                    progressLoad.dismiss()
                                                    Toast.makeText(context, "Book is out of stock", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                progressLoad.dismiss()
                                                Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            progressLoad.dismiss()
                                            Toast.makeText(context, "Failed to fetch book details", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                progressLoad.dismiss()
                                Toast.makeText(context, "Failed to fetch borrowed books", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressLoad.dismiss()
                Toast.makeText(context, "Failed to fetch client status", Toast.LENGTH_SHORT).show()
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateReturnDate(borrowDate: String): String {
        // Implement your logic to calculate the return date based on the borrow date
        val borrowDateFormatted = LocalDate.parse(borrowDate)
        val returnDate = borrowDateFormatted.plusDays(14)  // Adding 14 days
        return returnDate.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deliveryApproval(
        clientId: String,
        borrowId: String
    ) {
        val borrowClientRef = FirebaseDatabase.getInstance().getReference("BorrowedBooks").child(clientId).child(borrowId)
        borrowClientRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val borrowedBooksClient = snapshot.getValue(BorrowingBook::class.java)
                if (borrowedBooksClient == null) {
                    Toast.makeText(context, "Failed to fetch client status", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    val borrowStatus = "Delivered"
                    borrowClientRef.child("borrowStatus").setValue(borrowStatus)
                    Log.d("Firebase", "Borrow Status is: $borrowStatus")

                    val today = LocalDate.now().toString()
                    Log.d("Firebase", "Today is: $today")
                    borrowClientRef.child("borrowDate").setValue(today)
                    Log.d("Firebase", "Borrow date set to: $today")

                    val newReturnDate = calculateReturnDate(today)
                    Log.d("Firebase", "Return Date is: $newReturnDate")
                    borrowClientRef.child("returnDate").setValue(newReturnDate)
                    Log.d("Firebase", "Return date set to: $newReturnDate")

                    Toast.makeText(context, "Book Delivered Successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate("$ROUTE_VIEW_CLIENT_BORROWS/$clientId")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch client status", Toast.LENGTH_SHORT).show()
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