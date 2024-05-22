@file:Suppress("DEPRECATION")

package com.example.e_library.data

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.e_library.models.Books
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

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