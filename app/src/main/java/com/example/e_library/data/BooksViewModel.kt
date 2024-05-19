@file:Suppress("DEPRECATION")

package com.example.e_library.data

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.e_library.models.Books
import com.example.e_library.models.BorrowingBook
import com.example.e_library.models.Clients
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Suppress("NAME_SHADOWING")
class BooksViewModel (
    var navController: NavHostController,
    var context: Context
){
    private var progress: ProgressDialog = ProgressDialog(context)
    private  var progressUpdate: ProgressDialog = ProgressDialog(context)
    private  var progressDelete: ProgressDialog = ProgressDialog(context)
    private  var progressLoad: ProgressDialog = ProgressDialog(context)

    init {
        progress.setTitle("Saving \uD83D\uDCBE")
        progress.setMessage("Please wait...")
        progressUpdate.setTitle("Updating")
        progressUpdate.setTitle("Please wait...")
        progressDelete.setTitle("Deleting")
        progressDelete.setTitle("Please wait...")
        progressLoad.setTitle("Loading")
        progressLoad.setTitle("Please wait...")
    }
    fun saveBook(
        bookTitle: String,
        bookAuthor: String,
        bookYearOfPublication: String,
        bookPrice: String,
        bookISBNNumber: String,
        bookPublisher: String,
        bookPublicationDate: String,
        bookGenre: String,
        bookEdition: String,
        bookLanguage: String,
        bookNumberOfPages: String,
        bookAcquisitionMethod: String,
        bookCondition: String,
        bookShelfNumber: String,
        bookSynopsis: String,
        filePath: Uri?,
        bookQuantity: Int // Add quantity as an integer parameter
    ) {
        val id = System.currentTimeMillis().toString()
        val storageReference = FirebaseStorage.getInstance().getReference().child("Books/$id")
        if (filePath != null) {
            storageReference.putFile(filePath).addOnCompleteListener { task ->
                progress.show()
                if (bookTitle.isBlank() || bookAuthor.isBlank() || bookCondition.isBlank() || bookPrice.isBlank() ||
                    bookISBNNumber.isBlank() || bookPublisher.isBlank() || bookPublicationDate.isBlank() ||
                    bookEdition.isBlank() || bookLanguage.isBlank() || bookNumberOfPages.isBlank() || bookAcquisitionMethod.isBlank() ||
                    bookYearOfPublication.isBlank() || bookShelfNumber.isBlank() || bookSynopsis.isBlank()
                ) {
                    progress.dismiss()
                    Toast.makeText(context, "Fill all the fields please", Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                } else if (bookISBNNumber.length != 10 && bookISBNNumber.length != 13) {
                    progress.dismiss()
                    Toast.makeText(context, "Invalid ISBN Number", Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                } else if (task.isSuccessful) {
                    val dbRef = FirebaseDatabase.getInstance().getReference().child("Books")
                    // Check if ISBN Number already exists
                    dbRef.orderByChild("bookISBNNumber").equalTo(bookISBNNumber)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    progress.dismiss()
                                    Toast.makeText(
                                        context,
                                        "ISBN Number already exists",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.popBackStack()
                                } else {
                                    progress.dismiss()
                                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                                        val bookImageUrl = uri.toString()
                                        val houseData = Books(
                                            bookTitle,
                                            bookAuthor,
                                            bookCondition,
                                            bookPrice,
                                            bookISBNNumber,
                                            bookPublisher,
                                            bookPublicationDate,
                                            bookGenre,
                                            bookEdition,
                                            bookLanguage,
                                            bookNumberOfPages,
                                            bookAcquisitionMethod,
                                            bookYearOfPublication,
                                            bookShelfNumber,
                                            bookSynopsis,
                                            bookImageUrl,
                                            bookQuantity,
                                            id // Pass quantity to the Books constructor
                                        )
                                        val dbRef = FirebaseDatabase.getInstance().getReference()
                                            .child("Books/$id")
                                        dbRef.setValue(houseData)
                                        val toast = Toast.makeText(
                                            context,
                                            "Upload successful",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.setGravity(Gravity.CENTER, 0, 0)
                                        toast.show()
                                        navController.navigateUp()
                                    }
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                progress.dismiss()
                                Toast.makeText(
                                    context,
                                    "Error checking ISBN Number: ${databaseError.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                } else {
                    progress.dismiss()
                    Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }else{
            progress.dismiss()
            Toast.makeText(context, "You have to choose an image", Toast.LENGTH_LONG).show()
            return
        }
    }



    fun viewBooks(
        book: MutableState<Books>,
        books: SnapshotStateList<Books>
    ): SnapshotStateList<Books> {
        val ref = FirebaseDatabase.getInstance().getReference().child("Books")
        progressLoad.show()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progressLoad.dismiss()
                books.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Books::class.java)
                    book.value = value!!
                    books.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return books
    }

    fun getBorrowedBooksForClient(
        clientId: String,
        callback: (List<BorrowingBook>) -> Unit
    ) {
        val ref = FirebaseDatabase.getInstance().getReference().child("BorrowedBooks").child(clientId)
        val borrowedBooks = mutableListOf<BorrowingBook>()

        Log.d("BorrowedBooks","Client ID is: $clientId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                borrowedBooks.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(BorrowingBook::class.java)
                    value?.let {
                        borrowedBooks.add(it)
                    }
                }
                Log.d("BorrowedBooks", "Fetched ${borrowedBooks.size} books for client $clientId")
                Toast.makeText(context, "${borrowedBooks.size} books are borrowed", Toast.LENGTH_LONG).show()
                callback(borrowedBooks) // Invoke the callback with the fetched data
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("BorrowedBooks", "Error fetching borrowed books: ${error.message}")
                callback(emptyList()) // Handle the error by returning an empty list
            }
        })
    }

    fun updateBook(
        bookId: String,
        bookTitle: String,
        bookAuthor: String,
        bookYearOfPublication: String,
        bookPrice: String,
        bookISBNNumber: String,
        bookPublisher: String,
        bookPublicationDate: String,
        bookGenre: String,
        bookEdition: String,
        bookLanguage: String,
        bookNumberOfPages: String,
        bookAcquisitionMethod: String,
        bookCondition: String,
        bookShelfNumber: String,
        bookSynopsis: String,
        bookQuantity: Int,
        filePath: Uri?
    ) {
        progressUpdate.show()
        val storageReference = FirebaseStorage.getInstance().getReference().child("Books/$bookId")
        val updateData = Books(
            bookTitle,
            bookAuthor,
            bookYearOfPublication,
            bookPrice,
            bookISBNNumber,
            bookPublisher,
            bookPublicationDate,
            bookGenre,
            bookEdition,
            bookLanguage,
            bookNumberOfPages,
            bookAcquisitionMethod,
            bookCondition,
            bookShelfNumber,
            bookSynopsis,
            "",
            bookQuantity,// Placeholder for bookImageUrl
            bookId
        )

        // Update book details in Firebase Realtime Database
        if (filePath != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference().child("Books/$bookId")
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val book = snapshot.getValue(Books::class.java)
                    dbRef.setValue(updateData).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // If an image file is provided, update the image in Firebase Storage
                            filePath.let { fileUri ->
                                storageReference.putFile(fileUri).addOnCompleteListener { storageTask ->
                                    if (storageTask.isSuccessful) {
                                        progressUpdate.dismiss()
                                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                                            val imageUrl = uri.toString()
                                            updateData.bookImageUrl = imageUrl
                                            val newBookQuantity = (book?.bookQuantity ?: 0) + bookQuantity
                                            dbRef.setValue(updateData) // Update the book entry with the image URL
                                            dbRef.child("bookQuantity").setValue(newBookQuantity)
                                        }
                                    } else {
                                        progressUpdate.dismiss()
                                        Toast.makeText(context, "Upload Failure", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                            }

                            // Show success message
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                            navController.navigateUp()
                        } else {
                            // Handle database update error
                            progressUpdate.dismiss()
                            Toast.makeText(
                                context,
                                "ERROR: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    progressUpdate.dismiss()
                    // Handle database error
                    Toast.makeText(context, "ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })

        } else{
            progressUpdate.show()
            val dbRef = FirebaseDatabase.getInstance().getReference().child("Books/$bookId")
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val book = snapshot.getValue(Books::class.java)
                    val existingImageUrl = book?.bookImageUrl ?: ""

                    val updateData = Books(
                        bookTitle,
                        bookAuthor,
                        bookYearOfPublication,
                        bookPrice,
                        bookISBNNumber,
                        bookPublisher,
                        bookPublicationDate,
                        bookGenre,
                        bookEdition,
                        bookLanguage,
                        bookNumberOfPages,
                        bookAcquisitionMethod,
                        bookCondition,
                        bookShelfNumber,
                        bookSynopsis,
                        existingImageUrl,
                        bookQuantity,// Retain the existing image URL if filePath is null
                        bookId
                    )

                    dbRef.setValue(updateData).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Show success message
                            progressUpdate.dismiss()
                            val newBookQuantity = (book?.bookQuantity ?: 0) + bookQuantity
                            dbRef.child("bookQuantity").setValue(newBookQuantity)
                            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()

                            navController.navigateUp()
                        } else {
                            // Handle database update error
                            progressUpdate.dismiss()
                            Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    progressUpdate.dismiss()
                    // Handle database error
                    Toast.makeText(context, "ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
            )
            progressUpdate.dismiss()
            Toast.makeText(context, "Success with Image Retained", Toast.LENGTH_LONG).show()
        }
    }



    fun deleteBook(bookId: String) {
        val delRef = FirebaseDatabase.getInstance().getReference().child("Books/$bookId")
        progressDelete.show()
        delRef.removeValue().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                progressDelete.dismiss()
                Log.d("DeleteBook", "Book deleted")
                Toast.makeText(context, "Book deleted", Toast.LENGTH_SHORT).show()
            } else {
                progressDelete.dismiss()
                Log.e("DeleteBook", "Error deleting book", task.exception)
                Toast.makeText(context, "Error deleting book: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
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
        returnDate: String
    ) {
        progressLoad.show()
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId)

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
                                                            returnDate
                                                        )

                                                        val borrowedRef =
                                                            FirebaseDatabase.getInstance()
                                                                .getReference("BorrowedBooks")
                                                                .child(clientId).push()

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
        bookId: String
    ) {
        progressLoad.show()
        val borrowedRef = FirebaseDatabase.getInstance().getReference().child("BorrowedBooks").child(clientId)
        borrowedRef.orderByChild("bookId").equalTo(bookId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val borrowedBook = childSnapshot.getValue(BorrowingBook::class.java)
                    if (borrowedBook != null && borrowedBook.clientId == clientId) {
                        val bookRef = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId)
                        bookRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            @RequiresApi(Build.VERSION_CODES.O)
                            override fun onDataChange(bookSnapshot: DataSnapshot) {
                                val book = bookSnapshot.getValue(Books::class.java)
                                if (book != null) {
                                    val dueDate = LocalDate.parse(borrowedBook.returnDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                    val today = LocalDate.now()
                                    val daysLate = dueDate.until(today, ChronoUnit.DAYS)
                                    val fine = if (daysLate > 0) {
                                        // Calculate fine (e.g., $1 per day late)
                                        daysLate * 2.0
                                    } else {
                                        0.0
                                    }

                                    val newQuantity = book.bookQuantity + 1 // Increment quantity
                                    bookRef.child("bookQuantity").setValue(newQuantity)
                                    val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId)
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
                                                    clientRef.child("clientStatus").setValue(newClientStatus)
                                                    Toast.makeText(context, "Client Status changed to Fined", Toast.LENGTH_LONG).show()
                                                }
                                            } else {
                                                progressLoad.dismiss()
                                                Log.e(ContentValues.TAG, "Client not found for ID: $clientId")
                                                Toast.makeText(context, "Client not found for id: $clientId", Toast.LENGTH_LONG).show()
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            progressLoad.dismiss()
                                            Toast.makeText(context, "Failed to read client data", Toast.LENGTH_LONG).show()
                                            Log.e(ContentValues.TAG, "Failed to read client data.", error.toException())
                                        }
                                    })

                                    childSnapshot.ref.removeValue()
                                    progressLoad.dismiss()
                                    Toast.makeText(context, "Book successfully returned. Fine: Ksh.$fine", Toast.LENGTH_LONG).show()
                                    navController.navigateUp()
                                } else {
                                    progressLoad.dismiss()
                                    Toast.makeText(context, "Failed to return book: Book details not found", Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                progressLoad.dismiss()
                                Toast.makeText(context, "Failed to return book: ${error.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
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

    fun payFine(clientId: String) {
        val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId)
        clientRef.child("clientStatus").setValue("Active")
        clientRef.child("fine").setValue(0.0)
        Toast.makeText(context, "Fine Paid Successfully", Toast.LENGTH_SHORT).show()
    }
    fun suspendClient(clientId: String) {
        val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId)
        clientRef.child("clientStatus").setValue("Suspended")
        Toast.makeText(context, "Account Suspended Successfully", Toast.LENGTH_SHORT).show()
    }
    fun activateClient(clientId: String) {
        val clientRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId)
        clientRef.child("clientStatus").setValue("Active")
        Toast.makeText(context, "Account Activated Successfully", Toast.LENGTH_SHORT).show()
    }
}