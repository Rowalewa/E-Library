@file:Suppress("DEPRECATION")

package com.example.e_library.ui.theme.screens.books

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_library.R
import com.example.e_library.data.BooksViewModel
import com.example.e_library.navigation.ROUTE_VIEW_ALL_BOOKS
import com.example.e_library.ui.theme.ELibraryTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBooksScreen(navController: NavHostController, staffId: String){
    val  bookConditionOptions = listOf("New", "Used", "Damaged")
    var isOpen by remember {
        mutableStateOf(false)
    }
    var bookCondition by remember {
        mutableStateOf(bookConditionOptions[0])
    }
    val  bookGenreOptions = listOf(
        "Fiction",
        "Non-Fiction",
        "Mystery",
        "Thriller",
        "Romance",
        "Science-Fiction",
        "Fantasy",
        "Horror",
        "Historical Fiction",
        "Biography",
        "Autobiography",
        "Memoir",
        "Self Help",
        "Travel",
        "Business",
        "Cooking",
        "Art",
        "History",
        "Religion",
        "Romance",
        "Science",
        "Poetry",
        "Drama",
        "Comics",
        "Philosophy",
        "Kids"
    )
    var isBookGenreExpanded by remember {
        mutableStateOf(false)
    }
    var bookGenre by remember {
        mutableStateOf(bookGenreOptions[0])
    }
    val  bookAcquisitionMethodOptions = listOf(
        "Purchase",
        "Donation",
        "Exchange(Institutional)",
        "Exchange(Individual)",
        "Subscription Service",
        "InterLibrary",
        "Field Collection",
        "Gift Shop",
        "Complimentary Copy",
        "Government Deposit",
        "Digitization",
        "Cultural Exchange",
    )
    var isBookAcquisitionMethodExpanded by remember {
        mutableStateOf(false)
    }
    var bookAcquisitionMethod by remember {
        mutableStateOf(bookAcquisitionMethodOptions[0])
    }
    val context = LocalContext.current
    var bookTitle by remember { mutableStateOf(TextFieldValue("")) }
    var bookAuthor by remember { mutableStateOf(TextFieldValue("")) }
    var bookPrice by remember { mutableStateOf(TextFieldValue("")) }
    var bookPublisher by remember { mutableStateOf(TextFieldValue("")) }
    var bookPublicationDate by remember { mutableStateOf(TextFieldValue("")) }
    var isBookPublicationDateExpanded by remember { mutableStateOf(false) }
    var bookShelfNumber by remember { mutableStateOf(TextFieldValue("")) }
    var bookSynopsis by remember { mutableStateOf(TextFieldValue("")) }
    var bookEdition by remember { mutableStateOf(TextFieldValue("")) }
    var bookLanguage by remember { mutableStateOf(TextFieldValue("")) }
    var bookNumberOfPages by remember { mutableStateOf(TextFieldValue("")) }
    var bookISBNNumber by remember { mutableStateOf(TextFieldValue("")) }
    var bookYearOfPublication by remember { mutableStateOf(TextFieldValue("")) }
    var bookQuantity by remember { mutableStateOf(TextFieldValue("")) }
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.add_books),
            contentDescription = "Add Books Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column{
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                StaffAppTopBar(navController, staffId)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState(),
                        enabled = true,
                        reverseScrolling = false
                    )
            ) {
                Text(
                    text = "Add Books",
                    fontSize = 30.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                OutlinedTextField(
                    value = bookTitle,
                    onValueChange = { bookTitle = it },
                    label = { Text(text = "Book Title *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookAuthor,
                    onValueChange = { bookAuthor = it },
                    label = { Text(text = "Book Author *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookPrice,
                    onValueChange = { bookPrice = it },
                    label = { Text(text = "Book Price *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookQuantity,
                    onValueChange = { bookQuantity = it },
                    label = { Text(text = "Book Quantity *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookPublisher,
                    onValueChange = { bookPublisher = it },
                    label = { Text(text = "Book Publisher *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookPublicationDate,
                    onValueChange = { bookPublicationDate = it },
                    label = { Text("Book Publication Date") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { isBookPublicationDateExpanded = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                        }
                    }
                )

                if (isBookPublicationDateExpanded) {
                    val today = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val selectedDate = Calendar.getInstance()
                            selectedDate.set(year, month, day)
                            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            bookPublicationDate = TextFieldValue(sdf.format(selectedDate.time))
                            isBookPublicationDateExpanded = false
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                        .border(width = Dp.Hairline, color = Color.Black)
                        .background(color = Color.White)
                ) {
                    Text(
                        text = "Book Condition:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = Color.Magenta
                    )
                    ExposedDropdownMenuBox(
                        expanded = isOpen,
                        onExpandedChange = { isOpen = !isOpen }
                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                ),
                            value = bookCondition,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isOpen) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Magenta,
                                unfocusedTextColor = Color.Red,
                                focusedContainerColor = Color.Cyan,
                                unfocusedContainerColor = Color.Green,
                                disabledContainerColor = Color.White,
                                focusedLabelColor = Color.Green,
                                unfocusedLabelColor = Color.Magenta
                            ),
                        )
                        ExposedDropdownMenu(
                            expanded = isOpen,
                            onDismissRequest = { isOpen = false }) {
                            bookConditionOptions.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = { Text(text = text) },
                                    onClick = { bookCondition = bookConditionOptions[index] },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                }
                Text(text = "Currently Selected: $bookCondition")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = bookShelfNumber,
                    onValueChange = { bookShelfNumber = it },
                    label = { Text(text = "Book Shelf Number *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = bookSynopsis,
                    onValueChange = { bookSynopsis = it },
                    label = { Text(text = "Book Synopsis *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookEdition,
                    onValueChange = { bookEdition = it },
                    label = { Text(text = "Book Edition *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookLanguage,
                    onValueChange = { bookLanguage = it },
                    label = { Text(text = "Book Language *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookNumberOfPages,
                    onValueChange = { bookNumberOfPages = it },
                    label = { Text(text = "Book Number Of Pages *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = bookISBNNumber,
                    onValueChange = { bookISBNNumber = it },
                    label = { Text(text = "Book ISBN Number *") }, // international standard book number
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                        .border(width = Dp.Hairline, color = Color.Black)
                        .background(color = Color.White)
                ) {
                    Text(
                        text = "Book Genre:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = Color.Magenta
                    )
                    ExposedDropdownMenuBox(expanded = isBookGenreExpanded,
                        onExpandedChange = { isBookGenreExpanded = !isBookGenreExpanded }
                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                ),
                            value = bookGenre,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isBookGenreExpanded) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Magenta,
                                unfocusedTextColor = Color.Red,
                                focusedContainerColor = Color.Cyan,
                                unfocusedContainerColor = Color.Green,
                                disabledContainerColor = Color.White,
                                focusedLabelColor = Color.Green,
                                unfocusedLabelColor = Color.Magenta
                            ),
                        )
                        ExposedDropdownMenu(
                            expanded = isBookGenreExpanded,
                            onDismissRequest = { isBookGenreExpanded = false }) {
                            bookGenreOptions.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = { Text(text = text) },
                                    onClick = { bookGenre = bookGenreOptions[index] },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                }
                Text(text = "Currently Selected: $bookGenre")
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                        .border(width = Dp.Hairline, color = Color.Black)
                        .background(color = Color.White)
                ) {
                    Text(
                        text = "Book Acquisition Method:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = Color.Magenta
                    )
                    ExposedDropdownMenuBox(expanded = isBookAcquisitionMethodExpanded,
                        onExpandedChange = {
                            isBookAcquisitionMethodExpanded = !isBookAcquisitionMethodExpanded
                        }
                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                ),
                            value = bookAcquisitionMethod,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isBookAcquisitionMethodExpanded) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Magenta,
                                unfocusedTextColor = Color.Red,
                                focusedContainerColor = Color.Cyan,
                                unfocusedContainerColor = Color.Green,
                                disabledContainerColor = Color.White,
                                focusedLabelColor = Color.Green,
                                unfocusedLabelColor = Color.Magenta
                            ),
                        )
                        ExposedDropdownMenu(
                            expanded = isBookAcquisitionMethodExpanded,
                            onDismissRequest = { isBookAcquisitionMethodExpanded = false }) {
                            bookAcquisitionMethodOptions.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = { Text(text = text) },
                                    onClick = {
                                        bookAcquisitionMethod = bookAcquisitionMethodOptions[index]
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                }
                Text(text = "Currently Selected: $bookAcquisitionMethod")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = bookYearOfPublication,
                    onValueChange = { bookYearOfPublication = it },
                    label = { Text(text = "Book Year Of Publication *") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Cyan,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp,
                            top = 0.dp
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                ImagePicker(
                    context,
                    navController,
                    bookTitle.text.trim(),
                    bookAuthor.text.trim(),
                    bookYearOfPublication.text.trim(),
                    bookPrice.text.trim(),
                    bookISBNNumber.text.trim(),
                    bookPublisher.text.trim(),
                    bookPublicationDate.text.trim(),
                    bookGenre.trim(),
                    bookEdition.text.trim(),
                    bookLanguage.text.trim(),
                    bookNumberOfPages.text.trim(),
                    bookAcquisitionMethod.trim(),
                    bookCondition.trim(),
                    bookShelfNumber.text.trim(),
                    bookSynopsis.text.trim(),
                    bookQuantity.text.toIntOrNull() ?: 0,
                    staffId
                )
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            StaffBottomAppBar(navController, staffId)
        }
    }
}
@Composable
fun ImagePicker(
    context: Context,
    navController: NavHostController,
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
    staffId: String
) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    Column(modifier = Modifier) {
        if (hasImage && imageUri != null) {
            val bitmap = MediaStore.Images.Media.
            getBitmap(context.contentResolver,imageUri)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Selected image",
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 0.dp,
                    bottom = 0.dp
                ))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    )
                    .clip(shape = CutCornerShape(30.dp)),
                colors = ButtonDefaults.buttonColors(Color.Yellow)

            ) {
                Text(
                    text = "Select Image",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                val productRepository = BooksViewModel(navController,context)
                productRepository.saveBook(
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
                    imageUri,
                    bookQuantity
                )

            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 0.dp,
                        bottom = 10.dp
                    )
                    .clip(shape = CutCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(Color.Green)

            ) {
                Text(
                    text = "Upload",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            }
            Button(
                onClick = { navController.navigate("$ROUTE_VIEW_ALL_BOOKS/$staffId") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    ),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(
                    text = "View Book Uploads",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            }

        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Add Books Screen Preview"
)
@Composable
fun AddBooksScreenPreview(){
    ELibraryTheme {
        AddBooksScreen(navController = rememberNavController(), staffId = "")
    }
}