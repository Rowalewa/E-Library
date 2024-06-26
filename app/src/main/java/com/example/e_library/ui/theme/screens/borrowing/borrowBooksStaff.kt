package com.example.e_library.ui.theme.screens.borrowing

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.e_library.R
import com.example.e_library.data.TransactionViewModel
import com.example.e_library.models.Books
import com.example.e_library.ui.theme.screens.books.StaffAppTopBar
import com.example.e_library.ui.theme.screens.books.StaffBottomAppBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun BorrowBooksStaffScreen(navController: NavHostController, bookId: String, clientId: String, staffId: String){
    val context = LocalContext.current
    val transactionViewModel = TransactionViewModel(navController, context)

    var mBookId by remember {
        mutableStateOf(TextFieldValue(bookId))
    }
    val bookTitle by remember {
        mutableStateOf("")
    }
    val bookAuthor by remember {
        mutableStateOf("")
    }
    val bookISBNNumber by remember {
        mutableStateOf("")
    }
    val bookGenre by remember {
        mutableStateOf("")
    }
    val bookPublisher by remember {
        mutableStateOf("")
    }
    val bookSynopsis by remember {
        mutableStateOf("")
    }
    val bookImageUrl by remember {
        mutableStateOf("")
    }
    var mBookTitle by remember {
        mutableStateOf(TextFieldValue(bookTitle))
    }
    var mBookAuthor by remember {
        mutableStateOf(TextFieldValue(bookAuthor))
    }
    var mBookISBNNumber by remember {
        mutableStateOf(TextFieldValue(bookISBNNumber))
    }
    var mBookGenre by remember {
        mutableStateOf(TextFieldValue(bookGenre))
    }
    var mBookPublisher by remember {
        mutableStateOf(TextFieldValue(bookPublisher))
    }
    var mBookSynopsis by remember {
        mutableStateOf(TextFieldValue(bookSynopsis))
    }
    var mBookImageUrl by remember {
        mutableStateOf(TextFieldValue(bookImageUrl))
    }
    var borrowDate by remember { mutableStateOf(TextFieldValue("")) }
    var isBorrowDateExpanded by remember { mutableStateOf(false) }
    var returnDate by remember { mutableStateOf(TextFieldValue("")) }
    var isReturnDateExpanded by remember { mutableStateOf(false) }

    Log.d("Firebase", "Book ID: $bookId")
    val currentDataRef = FirebaseDatabase.getInstance().getReference().child("Books/$bookId")
    val path = "Books/$bookId"
    Log.d("Firebase", "Database Reference Path: $path")
    Log.d("Firebase", "Fetching book with ID: $bookId")
    currentDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val book = snapshot.getValue(Books::class.java)
                if (book != null) {
                    mBookId = TextFieldValue(book.bookId)
                    mBookTitle = TextFieldValue(book.bookTitle)
                    mBookAuthor = TextFieldValue(book.bookAuthor)
                    mBookISBNNumber = TextFieldValue(book.bookISBNNumber)
                    mBookGenre = TextFieldValue(book.bookGenre)
                    mBookPublisher = TextFieldValue(book.bookPublisher)
                    mBookSynopsis = TextFieldValue(book.bookSynopsis)
                    mBookImageUrl = TextFieldValue(book.bookImageUrl)

                } else {
                    Log.e("Firebase", "Failed to parse book data")
                }
            } else {
                Log.e("Firebase", "Snapshot does not exist")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    })


    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.borrow_books),
            contentDescription = "Borrow books image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                StaffAppTopBar(navController, staffId)
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ){
                OutlinedTextField(
                    value = mBookId,
                    onValueChange = { mBookId = it },
                    label = { Text("Book ID") }
                )
                OutlinedTextField(
                    value = clientId,
                    onValueChange = { },
                    label = { Text("Client ID") }
                )
                OutlinedTextField(
                    value = borrowDate,
                    onValueChange = { borrowDate = it },
                    label = { Text("Borrow Date") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { isBorrowDateExpanded = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                        }
                    }
                )

                if (isBorrowDateExpanded) {
                    val today = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val selectedDate = Calendar.getInstance()
                            selectedDate.set(year, month, day)
                            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            borrowDate = TextFieldValue(sdf.format(selectedDate.time))
                            isBorrowDateExpanded = false
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                OutlinedTextField(
                    value = returnDate,
                    onValueChange = { returnDate = it },
                    label = { Text("Return Date") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { isReturnDateExpanded = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                        }
                    }
                )

                if (isReturnDateExpanded) {
                    val today = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val selectedDate = Calendar.getInstance()
                            selectedDate.set(year, month, day)
                            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            returnDate = TextFieldValue(sdf.format(selectedDate.time))
                            isReturnDateExpanded = false
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                Button(
                    onClick = {
                        transactionViewModel.borrowBook(
                            mBookId.text.trim(),
                            clientId,
                            mBookTitle.text.trim(),
                            mBookAuthor.text.trim(),
                            mBookISBNNumber.text.trim(),
                            mBookGenre.text.trim(),
                            mBookPublisher.text.trim(),
                            mBookSynopsis.text.trim(),
                            mBookImageUrl.text.trim(),
                            borrowDate.text.trim(),
                            returnDate.text.trim(),
                            borrowMeans = "Staff",
                            borrowStatus = "Delivered"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Borrow Book")
                }
            }
            Spacer(modifier = Modifier.height(150.dp))
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            StaffBottomAppBar(navController, staffId)
        }
    }
}