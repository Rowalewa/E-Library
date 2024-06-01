package com.example.e_library.ui.theme.screens.deliveryReturn

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.e_library.R
import com.example.e_library.data.DeliveryViewModel
import com.example.e_library.models.Attendant
import com.example.e_library.models.Delivery
import com.example.e_library.ui.theme.screens.attendant.AttendantBottomAppBar
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelAppTopBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeliveryReturnConfirm(
    navController: NavHostController,
    attendantId: String,
    deliveryPersonnelId: String,
    deliveryCartOrderId: String,
    deliveryId:String,
    clientId: String,
    bookId: String
){
    val context = LocalContext.current
    val today = LocalDate.now().toString()
    val attendantProfilePicturePictureUrl by remember {
        mutableStateOf("")
    }
    val attendantFullName by remember {
        mutableStateOf("")
    }
    val attendantEmailAddress by remember {
        mutableStateOf("")
    }
    val attendantPhoneNumber by remember {
        mutableStateOf("")
    }
    val deliveryPersonnelProfilePictureUrl by remember {
        mutableStateOf("")
    }
    val deliveryPersonnelFullName by remember {
        mutableStateOf("")
    }
    val deliveryPersonnelEmailAddress by remember {
        mutableStateOf("")
    }
    val deliveryPersonnelPhoneNumber by remember {
        mutableStateOf("")
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
    val deliveryClientProfilePictureUrl by remember {
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
    val deliveryDepartureDate by remember {
        mutableStateOf("")
    }
    val deliveryArrivalDate by remember {
        mutableStateOf("")
    }
    val deliveryClientDeliveredDate by remember {
        mutableStateOf("")
    }
    val deliverySetReturnDate by remember {
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
    var mAttendantProfilePictureUrl by remember {
        mutableStateOf(TextFieldValue(attendantProfilePicturePictureUrl))
    }
    var mAttendantFullName by remember {
        mutableStateOf(TextFieldValue(attendantFullName))
    }
    var mAttendantEmailAddress by remember {
        mutableStateOf(TextFieldValue(attendantEmailAddress))
    }
    var mAttendantPhoneNumber by remember {
        mutableStateOf(TextFieldValue(attendantPhoneNumber))
    }
    var mDeliveryPersonnelProfilePictureUrl by remember {
        mutableStateOf(TextFieldValue(deliveryPersonnelProfilePictureUrl))
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
    var mDeliveryClientProfilePictureUrl by remember {
        mutableStateOf(TextFieldValue(deliveryClientProfilePictureUrl))
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
    var mDeliveryDepartureDate by remember {
        mutableStateOf(TextFieldValue(deliveryDepartureDate))
    }
    var mDeliveryArrivalDate by remember {
        mutableStateOf(TextFieldValue(deliveryArrivalDate))
    }
    var mDeliveryClientDeliveredDate by remember {
        mutableStateOf(TextFieldValue(deliveryClientDeliveredDate))
    }
    var mDeliverySetReturnDate by remember {
        mutableStateOf(TextFieldValue(deliverySetReturnDate))
    }
    val mDeliveryReturnDate by remember {
        mutableStateOf(TextFieldValue(today))
    }

    Log.d("Firebase", "Delivery ID: $deliveryId")
    val currentDeliveryDataRef = FirebaseDatabase.getInstance().getReference().child("Delivery/$clientId/$deliveryId")
    val deliveryPath = "Delivery/$deliveryId"
    Log.d("Firebase", "Database Reference Path: $deliveryPath")
    Log.d("Firebase", "Fetching delivery with ID: $deliveryId")
    currentDeliveryDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val delivery = snapshot.getValue(Delivery::class.java)
                if (delivery != null) {
                    mDeliveryPersonnelProfilePictureUrl = TextFieldValue(delivery.deliveryPersonnelProfilePictureUrl)
                    mDeliveryPersonnelFullName = TextFieldValue(delivery.deliveryPersonnelFullName)
                    mDeliveryPersonnelEmailAddress = TextFieldValue(delivery.deliveryPersonnelEmailAddress)
                    mDeliveryPersonnelPhoneNumber = TextFieldValue(delivery.deliveryPersonnelPhoneNumber)
                    mDeliveryBookTitle = TextFieldValue(delivery.deliveryBookTitle)
                    mDeliveryBookAuthor = TextFieldValue(delivery.deliveryBookAuthor)
                    mDeliveryBookISBNNumber = TextFieldValue(delivery.deliveryBookISBNNumber)
                    mDeliveryBookGenre = TextFieldValue(delivery.deliveryBookGenre)
                    mDeliveryBookPublisher = TextFieldValue(delivery.deliveryBookPublisher)
                    mDeliveryBookSynopsis = TextFieldValue(delivery.deliveryBookSynopsis)
                    mDeliveryBookImageUrl = TextFieldValue(delivery.deliveryBookImageUrl)
                    mDeliveryBookQuantity = TextFieldValue(delivery.deliveryBookQuantity.toString())
                    mDeliveryClientProfilePictureUrl = TextFieldValue(delivery.deliveryClientProfilePictureUrl)
                    mDeliveryClientFullName = TextFieldValue(delivery.deliveryClientFullName)
                    mDeliveryClientEmailAddress = TextFieldValue(delivery.deliveryClientEmailAddress)
                    mDeliveryClientPhoneNumber = TextFieldValue(delivery.deliveryClientPhoneNumber)
                    mDeliveryCartOrderDate = TextFieldValue(delivery.deliveryCartOrderDate)
                    mDeliveryCartOrderStatus = TextFieldValue(delivery.deliveryCartOrderStatus)
                    mDeliveryLocationName = TextFieldValue(delivery.deliveryLocationName)
                    mDeliveryLocationDistanceInKm = TextFieldValue(delivery.deliveryLocationDistanceInKm)
                    mDeliveryLocationPrice = TextFieldValue(delivery.deliveryLocationPrice)
                    mDeliveryDepartureDate = TextFieldValue(delivery.deliveryDepartureDate)
                    mDeliveryArrivalDate = TextFieldValue(delivery.deliveryArrivalDate)
                    mDeliveryClientDeliveredDate = TextFieldValue(delivery.deliveryClientDeliveredDate)
                    mDeliverySetReturnDate = TextFieldValue(delivery.deliverySetReturnDate)
                } else {
                    Log.e("Firebase", "Failed to parse delivery data")
                }
            } else {
                Log.e("Firebase", "Snapshot does not exist")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    })

    Log.d("Firebase", "Attendant ID: $attendantId")
    val currentAttendantDataRef = FirebaseDatabase.getInstance().getReference().child("Attendant/$attendantId")
    val attendantPath = "Attendant/$attendantId"
    Log.d("Firebase", "Database Reference Path: $attendantPath")
    Log.d("Firebase", "Fetching Attendant with ID: $attendantId")
    currentAttendantDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val attendant = snapshot.getValue(Attendant::class.java)
                if (attendant != null) {
                    mAttendantProfilePictureUrl= TextFieldValue(attendant.attendantProfilePictureUrl)
                    mAttendantFullName = TextFieldValue(attendant.fullName)
                    mAttendantEmailAddress = TextFieldValue(attendant.email)
                    mAttendantPhoneNumber = TextFieldValue(attendant.phoneNumber)
                } else {
                    Log.e("Firebase", "Failed to parse attendant data")
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
            painter = painterResource(id = R.drawable.delivery_return_confirm),
            contentDescription = "View Client Wallpaper",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
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
                    .fillMaxWidth()
            ){
                Text(
                    text = "Delivery Return \uD83D\uDED2",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Blue
                )

                OutlinedTextField(
                    value = mAttendantFullName,
                    onValueChange = { mAttendantFullName = it },
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
                    value = mAttendantEmailAddress,
                    onValueChange = { mAttendantEmailAddress = it },
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
                    value = mAttendantPhoneNumber,
                    onValueChange = { mAttendantPhoneNumber = it },
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
                    value = mDeliveryPersonnelFullName,
                    onValueChange = { mDeliveryPersonnelFullName = it },
                    label = { Text("Courier Name") },
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
                    label = { Text("Courier Email Address") },
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
                    label = { Text("Courier Phone Number") },
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
                    value = mDeliveryCartOrderStatus,
                    onValueChange = { mDeliveryCartOrderStatus = it },
                    label = { Text("Current Order Status") },
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
                    value = mDeliveryDepartureDate,
                    onValueChange = { mDeliveryDepartureDate = it },
                    label = { Text("Delivery Departure Date") },
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
                    value = mDeliveryClientDeliveredDate,
                    onValueChange = { mDeliveryClientDeliveredDate = it },
                    label = { Text("Client Delivered Date") },
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
                    value = mDeliveryArrivalDate,
                    onValueChange = { mDeliveryArrivalDate = it },
                    label = { Text("Delivery Arrival Date") },
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
                    deliveryViewModel.submitDeliveryReturnDetails(
                        attendantId,
                        mAttendantProfilePictureUrl.text.trim(),
                        mAttendantFullName.text.trim(),
                        mAttendantEmailAddress.text.trim(),
                        mAttendantPhoneNumber.text.trim(),
                        deliveryPersonnelId,
                        mDeliveryPersonnelProfilePictureUrl.text.trim(),
                        mDeliveryPersonnelFullName.text.trim(),
                        mDeliveryPersonnelEmailAddress.text.trim(),
                        mDeliveryPersonnelPhoneNumber.text.trim(),
                        bookId,
                        mDeliveryBookTitle.text.trim(),
                        mDeliveryBookAuthor.text.trim(),
                        mDeliveryBookISBNNumber.text.trim(),
                        mDeliveryBookGenre.text.trim(),
                        mDeliveryBookPublisher.text.trim(),
                        mDeliveryBookSynopsis.text.trim(),
                        mDeliveryBookImageUrl.text.trim(),
                        mDeliveryBookQuantity.text.toIntOrNull()?: 0,
                        clientId,
                        mDeliveryClientProfilePictureUrl.text.trim(),
                        mDeliveryClientFullName.text.trim(),
                        mDeliveryClientEmailAddress.text.trim(),
                        mDeliveryClientPhoneNumber.text.trim(),
                        mDeliveryLocationName.text.trim(),
                        mDeliveryLocationPrice.text.trim(),
                        mDeliveryLocationDistanceInKm.text.trim(),
                        mDeliveryCartOrderDate.text.trim(),
                        deliveryCartOrderStatus = "Depot Returned",
                        deliveryCartOrderId,
                        mDeliveryDepartureDate.text.trim(),
                        mDeliveryArrivalDate.text.trim(),
                        mDeliveryClientDeliveredDate.text.trim(),
                        deliveryId,
                        mDeliverySetReturnDate.text.trim(),
                        mDeliveryReturnDate.text.trim(),
                        deliveryReturnFine = 0,
                        deliveryReturnLibraryFine = 0,
                        deliveryReturnDepartureDate = "",
                        deliveryReturnArrivalDate = ""
                    )
                }) {
                    Text(
                        text = "Confirm"
                    )
                }
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            AttendantBottomAppBar(navController, attendantId)
        }
    }
}
