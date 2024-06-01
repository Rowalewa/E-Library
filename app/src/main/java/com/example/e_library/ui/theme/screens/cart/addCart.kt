package com.example.e_library.ui.theme.screens.cart

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_library.R
import com.example.e_library.data.DeliveryViewModel
import com.example.e_library.models.Books
import com.example.e_library.models.Clients
import com.example.e_library.models.DeliveryLocation
import com.example.e_library.ui.theme.screens.borrowing.ClientAppTopBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddCartScreen(
    navController: NavController,
    clientId: String,
    bookId: String
){
    val context = LocalContext.current
    val today = LocalDate.now().toString()

    var cartOrderBookId by remember {
        mutableStateOf(TextFieldValue(bookId))
    }
    val cartOrderBookTitle by remember {
        mutableStateOf("")
    }
    val cartOrderBookAuthor by remember {
        mutableStateOf("")
    }
    val cartOrderBookISBNNumber by remember {
        mutableStateOf("")
    }
    val cartOrderBookGenre by remember {
        mutableStateOf("")
    }
    val cartOrderBookPublisher by remember {
        mutableStateOf("")
    }
    val cartOrderBookSynopsis by remember {
        mutableStateOf("")
    }
    val cartOrderBookImageUrl by remember {
        mutableStateOf("")
    }
    val cartOrderBookQuantity by remember {
        mutableStateOf("")
    }
    val cartOrderClientProfilePictureUrl by remember {
        mutableStateOf("")
    }
    val cartOrderClientFullName by remember {
        mutableStateOf("")
    }
    val cartOrderClientEmailAddress by remember {
        mutableStateOf("")
    }
    val cartOrderClientPhoneNumber by remember {
        mutableStateOf("")
    }
    val cartOrderDate by remember {
        mutableStateOf(today)
    }
    var mCartOrderBookTitle by remember {
        mutableStateOf(TextFieldValue(cartOrderBookTitle))
    }
    var mCartOrderBookAuthor by remember {
        mutableStateOf(TextFieldValue(cartOrderBookAuthor))
    }
    var mCartOrderBookISBNNumber by remember {
        mutableStateOf(TextFieldValue(cartOrderBookISBNNumber))
    }
    var mCartOrderBookGenre by remember {
        mutableStateOf(TextFieldValue(cartOrderBookGenre))
    }
    var mCartOrderBookPublisher by remember {
        mutableStateOf(TextFieldValue(cartOrderBookPublisher))
    }
    var mCartOrderBookSynopsis by remember {
        mutableStateOf(TextFieldValue(cartOrderBookSynopsis))
    }
    var mCartOrderBookImageUrl by remember {
        mutableStateOf(TextFieldValue(cartOrderBookImageUrl))
    }
    var mCartOrderBookQuantity by remember {
        mutableStateOf(TextFieldValue(cartOrderBookQuantity))
    }
    var mCartOrderClientProfilePictureUrl by remember {
        mutableStateOf(TextFieldValue(cartOrderClientProfilePictureUrl))
    }
    var mCartOrderClientFullName by remember {
        mutableStateOf(TextFieldValue(cartOrderClientFullName))
    }
    var mCartOrderClientEmailAddress by remember {
        mutableStateOf(TextFieldValue(cartOrderClientEmailAddress))
    }
    var mCartOrderClientPhoneNumber by remember {
        mutableStateOf(TextFieldValue(cartOrderClientPhoneNumber))
    }
    var mCartOrderDate by remember {
        mutableStateOf(TextFieldValue(cartOrderDate))
    }


    val locations = listOf(
        DeliveryLocation("Westlands", 4.5, -1.2635, 36.8021),
        DeliveryLocation("Buruburu", 7.8, -1.2870, 36.8697),
        DeliveryLocation("Kileleshwa", 3.9, -1.2921, 36.7684),
        DeliveryLocation("Lang'ata", 7.9, -1.3395, 36.7682),
        DeliveryLocation("Karen", 10.2, -1.3267, 36.7111),
        DeliveryLocation("Lavington", 4.9, -1.2725, 36.7789),
        DeliveryLocation("Parklands", 3.6, -1.2623, 36.8116),
        DeliveryLocation("South B", 5.9, -1.3121, 36.8226),
        DeliveryLocation("Eastleigh", 7.3, -1.2734, 36.8422),
        DeliveryLocation("Ngong Road", 6.2, -1.3019, 36.7474),
        DeliveryLocation("Rongai", 19.4, -1.3951, 36.7447),
        DeliveryLocation("Kasarani", 11.5, -1.2244, 36.9053),
        DeliveryLocation("Donholm", 9.3, -1.2977, 36.8914),
        DeliveryLocation("Thika Road", 9.7, -1.1958, 36.9318),
        DeliveryLocation("Mombasa Road", 15.7, -1.3601, 36.8982),
        DeliveryLocation("Ruaka", 12.3, -1.1974, 36.7672),
        DeliveryLocation("Juja", 23.5, -1.1082, 37.0158),
        DeliveryLocation("Kahawa", 15.2, -1.1807, 36.9279),
        DeliveryLocation("Kiambu Road", 10.3, -1.2322, 36.8349),
        DeliveryLocation("Githurai", 11.9, -1.2082, 36.9024)
    )

//    val locations = listOf(
//        DeliveryLocation("Westlands", -1.2635, 36.8021).apply {
//            val (distance, price) = calculateRoadDistanceAndPriceFromCbd(cbdLatitude, cbdLongitude)
//            deliveryLocationDistanceInKm = distance
//            deliveryLocationPrice = price
//        },
//        DeliveryLocation("Buruburu", -1.2870, 36.8697).apply {
//            val (distance, price) = calculateRoadDistanceAndPriceFromCbd(cbdLatitude, cbdLongitude)
//            deliveryLocationDistanceInKm = distance
//            deliveryLocationPrice = price
//        },
//        DeliveryLocation("Kileleshwa", -1.2921, 36.7684).apply {
//            val (distance, price) = calculateRoadDistanceAndPriceFromCbd(cbdLatitude, cbdLongitude)
//            deliveryLocationDistanceInKm = distance
//            deliveryLocationPrice = price
//        },
//        DeliveryLocation("Lang'ata", -1.3395, 36.7682).apply {
//            val (distance, price) = calculateRoadDistanceAndPriceFromCbd(cbdLatitude, cbdLongitude)
//            deliveryLocationDistanceInKm = distance
//            deliveryLocationPrice = price
//        },
//        DeliveryLocation("Karen", -1.3267, 36.7111).apply {
//            val (distance, price) = calculateRoadDistanceAndPriceFromCbd(cbdLatitude, cbdLongitude)
//            deliveryLocationDistanceInKm = distance
//            deliveryLocationPrice = price
//        },
//        // Add the remaining locations using the same pattern
//    )


    var isDeliveryLocationExpanded by remember {
        mutableStateOf(false)
    }
    val deliveryLocation by remember {
        mutableStateOf(locations[0])
    }

    var mCartOrderLocationName by remember {
        mutableStateOf(deliveryLocation.deliveryLocationName)
    }
    var mCartOrderLocationPrice by remember {
        mutableDoubleStateOf(deliveryLocation.deliveryLocationPrice)
    }
    var mCartOrderLocationDistanceInKm by remember {
        mutableDoubleStateOf(deliveryLocation.deliveryLocationDistanceInKm)
    }


    Log.d("Firebase", "Book ID: $bookId")
    val currentBookDataRef = FirebaseDatabase.getInstance().getReference().child("Books/$bookId")
    val bookPath = "Books/$bookId"
    Log.d("Firebase", "Database Reference Path: $bookPath")
    Log.d("Firebase", "Fetching book with ID: $bookId")
    currentBookDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val book = snapshot.getValue(Books::class.java)
                if (book != null) {
                    cartOrderBookId = TextFieldValue(book.bookId)
                    mCartOrderBookTitle = TextFieldValue(book.bookTitle)
                    mCartOrderBookAuthor = TextFieldValue(book.bookAuthor)
                    mCartOrderBookISBNNumber = TextFieldValue(book.bookISBNNumber)
                    mCartOrderBookGenre = TextFieldValue(book.bookGenre)
                    mCartOrderBookPublisher = TextFieldValue(book.bookPublisher)
                    mCartOrderBookSynopsis = TextFieldValue(book.bookSynopsis)
                    mCartOrderBookImageUrl = TextFieldValue(book.bookImageUrl)

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

    Log.d("Firebase", "Client ID: $clientId")
    val currentClientDataRef = FirebaseDatabase.getInstance().getReference().child("Client/$clientId")
    val clientPath = "Client/$clientId"
    Log.d("Firebase", "Database Reference Path: $clientPath")
    Log.d("Firebase", "Fetching client with ID: $clientId")
    currentClientDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val client = snapshot.getValue(Clients::class.java)
                if (client != null) {
                    mCartOrderClientProfilePictureUrl = TextFieldValue(client.clientProfilePictureUrl)
                    mCartOrderClientFullName = TextFieldValue(client.fullName)
                    mCartOrderClientEmailAddress = TextFieldValue(client.email)
                    mCartOrderClientPhoneNumber = TextFieldValue(client.phoneNumber)
                } else {
                    Log.e("Firebase", "Failed to parse client data")
                }
            } else {
                Log.e("Firebase", "Snapshot does not exist")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    })

    Box {
        Image(
            painter = painterResource(id = R.drawable.add_cart),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column {
            Box (
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth()
            ){
                ClientAppTopBar(navController, clientId)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true,
                        reverseScrolling = false
                    )
                    .background(color = Color.Yellow)
            ) {
                Text(
                    text = "CART \uD83D\uDED2",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Blue
                )

                OutlinedTextField(
                    value = mCartOrderClientFullName,
                    onValueChange = {mCartOrderClientFullName = it},
                    label = { Text("Name") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderClientEmailAddress,
                    onValueChange = {mCartOrderClientEmailAddress = it},
                    label = { Text("Email Address") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderClientPhoneNumber,
                    onValueChange = {mCartOrderClientPhoneNumber = it},
                    label = { Text("Phone Number") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderBookTitle,
                    onValueChange = {mCartOrderBookTitle = it},
                    label = { Text("Book Title") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderBookAuthor,
                    onValueChange = {mCartOrderBookAuthor = it},
                    label = { Text("Book Author") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderBookISBNNumber,
                    onValueChange = {mCartOrderBookISBNNumber = it},
                    label = { Text("Book ISBN Number") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderBookGenre,
                    onValueChange = {mCartOrderBookGenre = it},
                    label = { Text("Book Genre") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderBookPublisher,
                    onValueChange = {mCartOrderBookPublisher = it},
                    label = { Text("Book Publisher") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderBookSynopsis,
                    onValueChange = {mCartOrderBookSynopsis = it},
                    label = { Text("Book Synopsis") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = mCartOrderBookQuantity,
                    onValueChange = {mCartOrderBookQuantity = it},
                    label = { Text("Book Ordered Quantity") },
                    placeholder = { Text("1") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                Text(
                    text = "Default value here unmodified is set to 1"
                )
                OutlinedTextField(
                    value = mCartOrderDate,
                    onValueChange = {mCartOrderDate = it},
                    label = { Text("Order Date") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                Row(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                        .background(color = Color.Cyan)
                ) {
                    Text(
                        text = "Delivery Location:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = Color.Black
                    )
                    ExposedDropdownMenuBox(
                        expanded = isDeliveryLocationExpanded,
                        onExpandedChange = { isDeliveryLocationExpanded = !isDeliveryLocationExpanded }
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
                            value = deliveryLocation.deliveryLocationName,
                            onValueChange = {deliveryLocation.deliveryLocationName = it},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDeliveryLocationExpanded) },
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
                            expanded = isDeliveryLocationExpanded,
                            onDismissRequest = { isDeliveryLocationExpanded = false }
                        ) {
                            locations.forEach { location ->
                                DropdownMenuItem(
                                    text = { Text(text = location.deliveryLocationName) },
                                    onClick = {
                                        deliveryLocation.deliveryLocationName = location.deliveryLocationName
                                        deliveryLocation.deliveryLocationDistanceInKm = location.deliveryLocationDistanceInKm
                                        mCartOrderLocationName = deliveryLocation.deliveryLocationName
                                        mCartOrderLocationDistanceInKm = deliveryLocation.deliveryLocationDistanceInKm
                                        mCartOrderLocationPrice = deliveryLocation.deliveryLocationPrice
                                        isDeliveryLocationExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }


                    }
                }
                Text(text = "Currently Selected: ${deliveryLocation.deliveryLocationName} ${deliveryLocation.deliveryLocationDistanceInKm}Km \n ${deliveryLocation.deliveryLocationPrice}Ksh")
                OutlinedTextField(
                    value = mCartOrderLocationName,
                    onValueChange = {mCartOrderLocationName = it},
                    label = { Text("Location") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderLocationDistanceInKm.toString(),
                    onValueChange = { deliveryLocation.deliveryLocationDistanceInKm = deliveryLocation.deliveryLocationDistanceInKm},
                    label = { Text("Distance(KM)") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                OutlinedTextField(
                    value = mCartOrderLocationPrice.toString(),
                    onValueChange = {mCartOrderLocationPrice = it.toDoubleOrNull()?: 0.0},
                    label = { Text("Price(Ksh)") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    )
                )
                Button(onClick = {
                    val deliveryViewModel = DeliveryViewModel(navController, context)
                    deliveryViewModel.makeCartOrder(
                        cartOrderBookId.text.trim(),
                        mCartOrderBookTitle.text.trim(),
                        mCartOrderBookAuthor.text.trim(),
                        mCartOrderBookISBNNumber.text.trim(),
                        mCartOrderBookGenre.text.trim(),
                        mCartOrderBookPublisher.text.trim(),
                        mCartOrderBookSynopsis.text.trim(),
                        mCartOrderBookImageUrl.text.trim(),
                        mCartOrderBookQuantity.text.toIntOrNull()?: 1,
                        mCartOrderClientProfilePictureUrl.text.trim(),
                        clientId,
                        mCartOrderClientFullName.text.trim(),
                        mCartOrderClientEmailAddress.text.trim(),
                        mCartOrderClientPhoneNumber.text.trim(),
                        mCartOrderLocationName.trim(),
                        mCartOrderLocationPrice.toString().trim(),
                        mCartOrderLocationDistanceInKm.toString().trim(),
                        mCartOrderDate.text.trim(),
                        cartOrderStatus = "Ordered"
                    ) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add to Cart")
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

}