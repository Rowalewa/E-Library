package com.example.e_library.ui.theme.screens.cart

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.e_library.models.Books
import com.example.e_library.models.CartOrder
import com.example.e_library.models.DeliveryPersonnel
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelAppTopBar
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelBottomAppBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeliveryDetails(navController: NavHostController, deliveryPersonnelId: String, cartOrderId:String, clientId: String, bookId: String) {
    val context = LocalContext.current
    val today = LocalDate.now().toString()
    val deliveryPersonnelFullName by remember {
        mutableStateOf("")
    }
    val deliveryPersonnelEmailAddress by remember {
        mutableStateOf("")
    }
    val deliveryPersonnelPhoneNumber by remember {
        mutableStateOf("")
    }
    var deliveryBookId by remember {
        mutableStateOf(TextFieldValue(bookId))
    }
    val deliveryBookTitle by remember {
        mutableStateOf("")
    }
    val deliveryBookAuthor by remember {
        mutableStateOf("")
    }
    val deliveryBookISBNNumber by remember {
        mutableStateOf("")
    }
    val deliveryBookGenre by remember {
        mutableStateOf("")
    }
    val deliveryBookPublisher by remember {
        mutableStateOf("")
    }
    val deliveryBookSynopsis by remember {
        mutableStateOf("")
    }
    val deliveryBookImageUrl by remember {
        mutableStateOf("")
    }
    val deliveryBookQuantity by remember {
        mutableStateOf("")
    }
    val deliveryClientFullName by remember {
        mutableStateOf("")
    }
    val deliveryClientEmailAddress by remember {
        mutableStateOf("")
    }
    val deliveryClientPhoneNumber by remember {
        mutableStateOf("")
    }
    val deliveryCartOrderDate by remember {
        mutableStateOf("")
    }
    val deliveryCartOrderStatus by remember {
        mutableStateOf("")
    }
    val deliveryLocationName by remember {
        mutableStateOf("")
    }
    val deliveryLocationDistanceInKm by remember {
        mutableStateOf("")
    }
    val deliveryLocationPrice by remember {
        mutableStateOf("")
    }
    var deliveryDepartureDate by remember {
        mutableStateOf(today)
    }
    var mDeliveryPersonnelFullName by remember {
        mutableStateOf(TextFieldValue(deliveryPersonnelFullName))
    }
    var mDeliveryPersonnelEmailAddress by remember {
        mutableStateOf(TextFieldValue(deliveryPersonnelEmailAddress))
    }
    var mDeliveryPersonnelPhoneNumber by remember {
        mutableStateOf(TextFieldValue(deliveryPersonnelPhoneNumber))
    }
    var mDeliveryBookTitle by remember {
        mutableStateOf(TextFieldValue(deliveryBookTitle))
    }
    var mDeliveryBookAuthor by remember {
        mutableStateOf(TextFieldValue(deliveryBookAuthor))
    }
    var mDeliveryBookISBNNumber by remember {
        mutableStateOf(TextFieldValue(deliveryBookISBNNumber))
    }
    var mDeliveryBookGenre by remember {
        mutableStateOf(TextFieldValue(deliveryBookGenre))
    }
    var mDeliveryBookPublisher by remember {
        mutableStateOf(TextFieldValue(deliveryBookPublisher))
    }
    var mDeliveryBookSynopsis by remember {
        mutableStateOf(TextFieldValue(deliveryBookSynopsis))
    }
    var mDeliveryBookImageUrl by remember {
        mutableStateOf(TextFieldValue(deliveryBookImageUrl))
    }
    var mDeliveryBookQuantity by remember {
        mutableStateOf(TextFieldValue(deliveryBookQuantity))
    }
    var mDeliveryClientFullName by remember {
        mutableStateOf(TextFieldValue(deliveryClientFullName))
    }
    var mDeliveryClientEmailAddress by remember {
        mutableStateOf(TextFieldValue(deliveryClientEmailAddress))
    }
    var mDeliveryClientPhoneNumber by remember {
        mutableStateOf(TextFieldValue(deliveryClientPhoneNumber))
    }
    var mDeliveryCartOrderDate by remember {
        mutableStateOf(TextFieldValue(deliveryCartOrderDate))
    }
    var mDeliveryCartOrderStatus by remember {
        mutableStateOf(TextFieldValue(deliveryCartOrderStatus))
    }
    var mDeliveryLocationName by remember {
        mutableStateOf(TextFieldValue(deliveryLocationName))
    }
    var mDeliveryLocationPrice by remember {
        mutableStateOf(TextFieldValue(deliveryLocationPrice))
    }
    var mDeliveryLocationDistanceInKm by remember {
        mutableStateOf(TextFieldValue(deliveryLocationDistanceInKm))
    }

    Log.d("Firebase", "Cart Order ID: $cartOrderId")
    val currentCartOrderDataRef = FirebaseDatabase.getInstance().getReference().child("CartOrders/$clientId/$cartOrderId")
    val cartOrderPath = "CartOrders/$cartOrderId"
    Log.d("Firebase", "Database Reference Path: $cartOrderPath")
    Log.d("Firebase", "Fetching cart order with ID: $cartOrderId")
    currentCartOrderDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val cartOrder = snapshot.getValue(CartOrder::class.java)
                if (cartOrder != null) {
                    deliveryBookId = TextFieldValue(cartOrder.cartOrderBookId)
                    mDeliveryBookTitle = TextFieldValue(cartOrder.cartOrderBookTitle)
                    mDeliveryBookAuthor = TextFieldValue(cartOrder.cartOrderBookAuthor)
                    mDeliveryBookISBNNumber = TextFieldValue(cartOrder.cartOrderBookISBNNumber)
                    mDeliveryBookGenre = TextFieldValue(cartOrder.cartOrderBookGenre)
                    mDeliveryBookPublisher = TextFieldValue(cartOrder.cartOrderBookPublisher)
                    mDeliveryBookSynopsis = TextFieldValue(cartOrder.cartOrderBookSynopsis)
                    mDeliveryBookImageUrl = TextFieldValue(cartOrder.cartOrderBookImageUrl)
                    mDeliveryBookQuantity = TextFieldValue(cartOrder.cartOrderBookQuantity.toString())
                    mDeliveryClientFullName = TextFieldValue(cartOrder.cartOrderClientFullName)
                    mDeliveryClientEmailAddress = TextFieldValue(cartOrder.cartOrderClientEmailAddress)
                    mDeliveryClientPhoneNumber = TextFieldValue(cartOrder.cartOrderClientPhoneNumber)
                    mDeliveryCartOrderDate = TextFieldValue(cartOrder.cartOrderDate)
                    mDeliveryCartOrderStatus = TextFieldValue(cartOrder.cartOrderStatus)
                    mDeliveryLocationName = TextFieldValue(cartOrder.cartOrderLocationName)
                    mDeliveryLocationDistanceInKm = TextFieldValue(cartOrder.cartOrderLocationDistanceInKm)
                    mDeliveryLocationPrice = TextFieldValue(cartOrder.cartOrderLocationPrice)
                } else {
                    Log.e("Firebase", "Failed to parse cart order data")
                }
            } else {
                Log.e("Firebase", "Snapshot does not exist")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    })

    Log.d("Firebase", "Delivery Personnel ID: $deliveryPersonnelId")
    val currentBookDataRef = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel/$deliveryPersonnelId")
    val deliveryPersonnelPath = "DeliveryPersonnel/$deliveryPersonnelId"
    Log.d("Firebase", "Database Reference Path: $deliveryPersonnelPath")
    Log.d("Firebase", "Fetching Delivery Personnel with ID: $deliveryPersonnelId")
    currentBookDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val deliveryPersonnel = snapshot.getValue(DeliveryPersonnel::class.java)
                if (deliveryPersonnel != null) {
                    mDeliveryPersonnelFullName = TextFieldValue(deliveryPersonnel.fullName)
                    mDeliveryPersonnelEmailAddress = TextFieldValue(deliveryPersonnel.email)
                    mDeliveryPersonnelPhoneNumber = TextFieldValue(deliveryPersonnel.phoneNumber)
                } else {
                    Log.e("Firebase", "Failed to parse delivery personnel data")
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
        Column {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                DeliveryPersonnelAppTopBar(navController, deliveryPersonnelId)
            }
            Column (
                modifier = Modifier
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true,
                        reverseScrolling = false
                    )
                    .background(color = Color.Transparent)
            ){
                Text(
                    text = "Delivery \uD83D\uDED2",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Blue
                )

                OutlinedTextField(
                    value = mDeliveryPersonnelFullName,
                    onValueChange = { mDeliveryPersonnelFullName = it },
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
                    value = mDeliveryPersonnelEmailAddress,
                    onValueChange = { mDeliveryPersonnelEmailAddress = it },
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
                    value = mDeliveryPersonnelPhoneNumber,
                    onValueChange = { mDeliveryPersonnelPhoneNumber = it },
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
                    value = mDeliveryClientFullName,
                    onValueChange = { mDeliveryClientFullName = it },
                    label = { Text("Client Name") },
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
                    value = mDeliveryClientEmailAddress,
                    onValueChange = { mDeliveryClientEmailAddress = it },
                    label = { Text("Client Email Address") },
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
                    value = mDeliveryClientPhoneNumber,
                    onValueChange = { mDeliveryClientPhoneNumber = it },
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
                    value = mDeliveryBookTitle,
                    onValueChange = { mDeliveryBookTitle = it },
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
                    value = mDeliveryBookAuthor,
                    onValueChange = { mDeliveryBookAuthor = it },
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
                    value = mDeliveryBookISBNNumber,
                    onValueChange = { mDeliveryBookISBNNumber = it },
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
                    value = mDeliveryBookGenre,
                    onValueChange = { mDeliveryBookGenre = it },
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
                    value = mDeliveryBookPublisher,
                    onValueChange = { mDeliveryBookPublisher = it },
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
                    value = mDeliveryBookSynopsis,
                    onValueChange = { mDeliveryBookSynopsis = it },
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
                    value = mDeliveryBookQuantity,
                    onValueChange = { mDeliveryBookQuantity = it },
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
                OutlinedTextField(
                    value = mDeliveryCartOrderDate,
                    onValueChange = { mDeliveryCartOrderDate = it },
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
                OutlinedTextField(
                    value = mDeliveryLocationName,
                    onValueChange = { mDeliveryLocationName = it },
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
                    value = mDeliveryLocationDistanceInKm,
                    onValueChange = { mDeliveryLocationDistanceInKm = it },
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
                    value = mDeliveryLocationPrice,
                    onValueChange = { mDeliveryLocationPrice = it },
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
                OutlinedTextField(
                    value = deliveryDepartureDate,
                    onValueChange = { deliveryDepartureDate = it },
                    label = { Text("Delivery Departure Date") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.Black,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Red
                    ),
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            DeliveryPersonnelBottomAppBar(navController, deliveryPersonnelId)
        }
    }
}
