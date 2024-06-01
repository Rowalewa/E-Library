package com.example.e_library.ui.theme.screens.clients

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.data.BooksViewModel
import com.example.e_library.models.Books
import com.example.e_library.navigation.ROUTE_ADD_CART
import com.example.e_library.navigation.ROUTE_BORROW_BOOKS_CLIENT
import com.example.e_library.ui.theme.screens.borrowing.ClientAppTopBar
import com.example.e_library.ui.theme.screens.borrowing.ClientBottomAppBar

@Composable
fun ViewAllBooksClient(navController: NavHostController, clientId: String){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.view_books_guest),
            contentDescription = "View Books",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                ClientAppTopBar(navController, clientId)
            }
            val context = LocalContext.current
            val booksRepository = BooksViewModel(navController, context)
            val emptyBookState = remember {
                mutableStateOf(
                    Books(
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        0,
                        ""
                    )
                )
            }
            val emptyBookListState = remember { mutableStateListOf<Books>() }

            val books = booksRepository.viewBooks(emptyBookState, emptyBookListState)


            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "BOOKS",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(5.dp))
                var searchText by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier
                        .border(
                            width = Dp.Hairline,
                            shape = CutCornerShape(10.dp),
                            color = Color.Black
                        )
                        .background(color = Color.Cyan, shape = CutCornerShape(10.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("Search") },
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 0.dp,
                            top = 2.dp,
                            bottom = 5.dp
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Red,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = Color.Black ,
                            unfocusedLabelColor = Color.DarkGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Magenta
                        )
                    )
                    IconButton(onClick = { searchText = "" }) {
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "Clear Search",
                            tint = Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                @Composable
                fun BookItem(
                    bookTitle: String,
                    bookAuthor: String,
                    bookPrice: String,
                    bookISBNNumber: String,
                    bookPublisher: String,
                    bookPublicationDate: String,
                    bookGenre: String,
                    bookEdition: String,
                    bookLanguage: String,
                    bookNumberOfPages: String,
                    bookAcquisitionMethod: String,
                    bookShelfNumber: String,
                    bookSynopsis: String,
                    bookImageUrl: String,
                    bookQuantity: Int,
                    bookId: String
                ) {

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            top = 0.dp,
                            end = 20.dp,
                            bottom = 0.dp
                        )
                        .clip(shape = CutCornerShape(20.dp))
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(color = Color.Green)
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(bookImageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(400.dp)
                                    .padding(18.dp)
                            )
                            Text(
                                text = "Book Title: $bookTitle",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Author: $bookAuthor",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Price: $bookPrice",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Quantity: $bookQuantity",
                                color = Color.Black
                            )
                            Text(
                                text = "Book ISBN Number: $bookISBNNumber",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Publisher: $bookPublisher",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Publication Date: $bookPublicationDate",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Genre: $bookGenre",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Edition: $bookEdition",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Language: $bookLanguage",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Number of Pages: $bookNumberOfPages",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Acquisition Method: $bookAcquisitionMethod",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Shelf Number: $bookShelfNumber",
                                color = Color.Black
                            )
                            Text(
                                text = "Book Synopsis: $bookSynopsis",
                                color = Color.Black
                            )
                            Button(
                                onClick = { navController.navigate("$ROUTE_BORROW_BOOKS_CLIENT/$bookId/$clientId") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 20.dp,
                                        end = 0.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    ),
                                colors = ButtonDefaults.buttonColors(Color.Red)
                            ) {
                                Text(text = "Borrow")
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = { navController.navigate("$ROUTE_ADD_CART/$clientId/$bookId") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 20.dp,
                                        end = 0.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    ),
                                colors = ButtonDefaults.buttonColors(Color.Blue)
                            ) {
                                Text(text = "Add to Cart")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(150.dp))
                }

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        start = 0.dp,
                        end = 0.dp,
                        bottom = 20.dp,
                        top = 0.dp
                    )
                ) {
                    val filteredBooks = books.filter {
                        it.bookTitle.contains(searchText, ignoreCase = true) ||
                                it.bookAuthor.contains(searchText, ignoreCase = true) ||
                                it.bookGenre.contains(searchText, ignoreCase = true) ||
                                it.bookPublisher.contains(searchText, ignoreCase = true) ||
                                it.bookAcquisitionMethod.contains(searchText, ignoreCase = true) ||
                                it.bookEdition.contains(searchText, ignoreCase = true) ||
                                it.bookISBNNumber.contains(searchText, ignoreCase = true)
                    }
                    items(filteredBooks){
                        BookItem(
                            bookTitle = it.bookTitle,
                            bookAuthor = it.bookAuthor,
                            bookPrice = it.bookPrice,
                            bookISBNNumber = it.bookISBNNumber,
                            bookPublisher = it.bookPublisher,
                            bookPublicationDate = it.bookPublicationDate,
                            bookGenre = it.bookGenre,
                            bookEdition = it.bookEdition,
                            bookLanguage = it.bookLanguage,
                            bookNumberOfPages = it.bookNumberOfPages,
                            bookAcquisitionMethod = it.bookAcquisitionMethod,
                            bookShelfNumber = it.bookShelfNumber,
                            bookSynopsis = it.bookSynopsis,
                            bookImageUrl = it.bookImageUrl,
                            bookQuantity = it.bookQuantity,
                            bookId = it.bookId
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ClientBottomAppBar(navController, clientId)
        }
    }
}