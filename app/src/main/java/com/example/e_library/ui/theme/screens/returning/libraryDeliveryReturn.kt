package com.example.e_library.ui.theme.screens.returning

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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.e_library.models.DeliveryReturn
import com.example.e_library.models.Staff
import com.example.e_library.ui.theme.screens.attendant.AttendantBottomAppBar
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelAppTopBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LibraryDeliveryReturn(
    navController: NavHostController,
    staffId: String,
    deliveryPersonnelId: String,
    attendantId: String,
    clientId: String,
    deliveryReturnId: String,
    deliveryId: String,
    deliveryCartOrderId: String,
    bookId: String
){
    val context = LocalContext.current
    val today = LocalDate.now().toString()
    val staffProfilePicturePictureUrl by remember {
        mutableStateOf("")
    }
    val staffFullName by remember {
        mutableStateOf("")
    }
    val staffEmailAddress by remember {
        mutableStateOf("")
    }
    val staffPhoneNumber by remember {
        mutableStateOf("")
    }
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
    val deliveryReturnBookTitle by remember {
        mutableStateOf("")
    }
    val deliveryReturnBookAuthor by remember {
        mutableStateOf("")
    }
    val deliveryReturnBookISBNNumber by remember {
        mutableStateOf("")
    }
    val deliveryReturnBookGenre by remember {
        mutableStateOf("")
    }
    val deliveryReturnBookPublisher by remember {
        mutableStateOf("")
    }
    val deliveryReturnBookSynopsis by remember {
        mutableStateOf("")
    }
    val deliveryReturnBookImageUrl by remember {
        mutableStateOf("")
    }
    val deliveryReturnBookQuantity by remember {
        mutableStateOf("")
    }
    val clientProfilePictureUrl by remember {
        mutableStateOf("")
    }
    val clientFullName by remember {
        mutableStateOf("")
    }
    val clientEmailAddress by remember {
        mutableStateOf("")
    }
    val clientPhoneNumber by remember {
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
    val deliveryReturnDate by remember {
        mutableStateOf("")
    }
    val deliveryReturnFine by remember {
        mutableStateOf("")
    }
    val deliveryReturnLibraryFine by remember {
        mutableStateOf("")
    }
    val deliveryReturnDepartureDate by remember {
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
    var mStaffProfilePictureUrl by remember {
        mutableStateOf(TextFieldValue(staffProfilePicturePictureUrl))
    }
    var mStaffFullName by remember {
        mutableStateOf(TextFieldValue(staffFullName))
    }
    var mStaffEmailAddress by remember {
        mutableStateOf(TextFieldValue(staffEmailAddress))
    }
    var mStaffPhoneNumber by remember {
        mutableStateOf(TextFieldValue(staffPhoneNumber))
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
    var mDeliveryReturnBookTitle by remember {
        mutableStateOf(TextFieldValue(deliveryReturnBookTitle))
    }
    var mDeliveryReturnBookAuthor by remember {
        mutableStateOf(TextFieldValue(deliveryReturnBookAuthor))
    }
    var mDeliveryReturnBookISBNNumber by remember {
        mutableStateOf(TextFieldValue(deliveryReturnBookISBNNumber))
    }
    var mDeliveryReturnBookGenre by remember {
        mutableStateOf(TextFieldValue(deliveryReturnBookGenre))
    }
    var mDeliveryReturnBookPublisher by remember {
        mutableStateOf(TextFieldValue(deliveryReturnBookPublisher))
    }
    var mDeliveryReturnBookSynopsis by remember {
        mutableStateOf(TextFieldValue(deliveryReturnBookSynopsis))
    }
    var mDeliveryReturnBookImageUrl by remember {
        mutableStateOf(TextFieldValue(deliveryReturnBookImageUrl))
    }
    var mDeliveryReturnBookQuantity by remember {
        mutableStateOf(TextFieldValue(deliveryReturnBookQuantity))
    }
    var mClientProfilePictureUrl by remember {
        mutableStateOf(TextFieldValue(clientProfilePictureUrl))
    }
    var mClientFullName by remember {
        mutableStateOf(TextFieldValue(clientFullName))
    }
    var mClientEmailAddress by remember {
        mutableStateOf(TextFieldValue(clientEmailAddress))
    }
    var mClientPhoneNumber by remember {
        mutableStateOf(TextFieldValue(clientPhoneNumber))
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
    var mDeliveryReturnDate by remember {
        mutableStateOf(TextFieldValue(deliveryReturnDate))
    }
    var mDeliveryReturnFine by remember {
        mutableStateOf(TextFieldValue(deliveryReturnFine))
    }
    var mDeliveryReturnLibraryFine by remember {
        mutableStateOf(TextFieldValue(deliveryReturnLibraryFine))
    }
    var mDeliveryReturnDepartureDate by remember {
        mutableStateOf(TextFieldValue(deliveryReturnDepartureDate))
    }
    var mDeliveryReturnArrivalDate by remember {
        mutableStateOf(TextFieldValue(today))
    }

    Log.d("Firebase", "Delivery Return ID: $deliveryReturnId")
    val currentDeliveryReturnDataRef = FirebaseDatabase.getInstance().getReference().child("DeliveryReturn/$deliveryPersonnelId/$deliveryReturnId")
    val deliveryReturnPath = "DeliveryReturn/$deliveryReturnId"
    Log.d("Firebase", "Database Reference Path: $deliveryReturnPath")
    Log.d("Firebase", "Fetching delivery return with ID: $deliveryReturnId")
    currentDeliveryReturnDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val deliveryReturn = snapshot.getValue(DeliveryReturn::class.java)
                if (deliveryReturn != null) {
                    mAttendantProfilePictureUrl = TextFieldValue(deliveryReturn.attendantProfilePictureUrl)
                    mAttendantFullName = TextFieldValue(deliveryReturn.attendantFullName)
                    mAttendantEmailAddress = TextFieldValue(deliveryReturn.attendantEmail)
                    mAttendantPhoneNumber = TextFieldValue(deliveryReturn.attendantPhoneNumber)
                    mDeliveryPersonnelProfilePictureUrl = TextFieldValue(deliveryReturn.deliveryPersonnelProfilePictureUrl)
                    mDeliveryPersonnelFullName = TextFieldValue(deliveryReturn.deliveryPersonnelFullName)
                    mDeliveryPersonnelEmailAddress = TextFieldValue(deliveryReturn.deliveryPersonnelEmailAddress)
                    mDeliveryPersonnelPhoneNumber = TextFieldValue(deliveryReturn.deliveryPersonnelPhoneNumber)
                    mDeliveryReturnBookTitle = TextFieldValue(deliveryReturn.deliveryBookTitle)
                    mDeliveryReturnBookAuthor = TextFieldValue(deliveryReturn.deliveryBookAuthor)
                    mDeliveryReturnBookISBNNumber = TextFieldValue(deliveryReturn.deliveryBookISBNNumber)
                    mDeliveryReturnBookGenre = TextFieldValue(deliveryReturn.deliveryBookGenre)
                    mDeliveryReturnBookPublisher = TextFieldValue(deliveryReturn.deliveryBookPublisher)
                    mDeliveryReturnBookSynopsis = TextFieldValue(deliveryReturn.deliveryBookSynopsis)
                    mDeliveryReturnBookImageUrl = TextFieldValue(deliveryReturn.deliveryBookImageUrl)
                    mDeliveryReturnBookQuantity = TextFieldValue(deliveryReturn.deliveryBookQuantity.toString())
                    mClientProfilePictureUrl = TextFieldValue(deliveryReturn.deliveryClientProfilePictureUrl)
                    mClientFullName = TextFieldValue(deliveryReturn.deliveryClientFullName)
                    mClientEmailAddress = TextFieldValue(deliveryReturn.deliveryClientEmailAddress)
                    mClientPhoneNumber = TextFieldValue(deliveryReturn.deliveryClientPhoneNumber)
                    mDeliveryCartOrderDate = TextFieldValue(deliveryReturn.deliveryCartOrderDate)
                    mDeliveryCartOrderStatus = TextFieldValue(deliveryReturn.deliveryCartOrderStatus)
                    mDeliveryLocationName = TextFieldValue(deliveryReturn.deliveryLocationName)
                    mDeliveryLocationDistanceInKm = TextFieldValue(deliveryReturn.deliveryLocationDistanceInKm)
                    mDeliveryLocationPrice = TextFieldValue(deliveryReturn.deliveryLocationPrice)
                    mDeliveryDepartureDate = TextFieldValue(deliveryReturn.deliveryDepartureDate)
                    mDeliveryArrivalDate = TextFieldValue(deliveryReturn.deliveryArrivalDate)
                    mDeliveryClientDeliveredDate = TextFieldValue(deliveryReturn.deliveryClientDeliveredDate)
                    mDeliverySetReturnDate = TextFieldValue(deliveryReturn.deliverySetReturnDate)
                    mDeliveryReturnDate = TextFieldValue(deliveryReturn.deliveryReturnDate)
                    mDeliveryReturnFine = TextFieldValue(deliveryReturn.deliveryReturnFine.toString())
                    mDeliveryReturnDepartureDate = TextFieldValue(deliveryReturn.deliveryReturnDepartureDate)
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

    Log.d("Firebase", "Staff ID: $staffId")
    val currentStaffDataRef = FirebaseDatabase.getInstance().getReference().child("Staff/$staffId")
    val staffPath = "Staff/$staffId"
    Log.d("Firebase", "Database Reference Path: $staffPath")
    Log.d("Firebase", "Fetching Staff with ID: $staffId")
    currentStaffDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val staff = snapshot.getValue(Staff::class.java)
                if (staff != null) {
                    mStaffProfilePictureUrl= TextFieldValue(staff.staffProfilePictureUrl)
                    mStaffFullName = TextFieldValue(staff.fullName)
                    mStaffEmailAddress = TextFieldValue(staff.email)
                    mStaffPhoneNumber = TextFieldValue(staff.phoneNumber)
                } else {
                    Log.e("Firebase", "Failed to parse staff data")
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
            painter = painterResource(id = R.drawable.library_delivery_return),
            contentDescription = null,
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
                    .background(color = Color.Transparent)
            ){
                Text(
                    text = "Delivery Return \uD83D\uDED2",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Blue
                )
                OutlinedTextField(
                    value = mStaffFullName,
                    onValueChange = { mStaffFullName = it },
                    label = { Text("My Name") },
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
                    value = mStaffEmailAddress,
                    onValueChange = { mStaffEmailAddress = it },
                    label = { Text("My Email Address") },
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
                    value = mStaffPhoneNumber,
                    onValueChange = { mStaffPhoneNumber = it },
                    label = { Text("My Phone Number") },
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
                    value = mAttendantFullName,
                    onValueChange = { mAttendantFullName = it },
                    label = { Text("Attendant Name") },
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
                    label = { Text("Attendant Email Address") },
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
                    label = { Text("Attendant Phone Number") },
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
                    value = mClientFullName,
                    onValueChange = { mClientFullName = it },
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
                    value = mClientEmailAddress,
                    onValueChange = { mClientEmailAddress = it },
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
                    value = mClientPhoneNumber,
                    onValueChange = { mClientPhoneNumber = it },
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
                    value = mDeliveryReturnBookTitle,
                    onValueChange = { mDeliveryReturnBookTitle = it },
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
                    value = mDeliveryReturnBookAuthor,
                    onValueChange = { mDeliveryReturnBookAuthor = it },
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
                    value = mDeliveryReturnBookISBNNumber,
                    onValueChange = { mDeliveryReturnBookISBNNumber = it },
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
                    value = mDeliveryReturnBookGenre,
                    onValueChange = { mDeliveryReturnBookGenre = it },
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
                    value = mDeliveryReturnBookPublisher,
                    onValueChange = { mDeliveryReturnBookPublisher = it },
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
                    value = mDeliveryReturnBookSynopsis,
                    onValueChange = { mDeliveryReturnBookSynopsis = it },
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
                    value = mDeliveryReturnBookQuantity,
                    onValueChange = { mDeliveryReturnBookQuantity = it },
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
                    value = mDeliveryReturnDate,
                    onValueChange = { mDeliveryReturnDate = it },
                    label = { Text("Delivery Client Return Date") },
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
                    value = mDeliveryReturnFine,
                    onValueChange = { mDeliveryReturnFine = it },
                    label = { Text("Delivery Return Fine") },
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
                    value = mDeliveryReturnLibraryFine,
                    onValueChange = { mDeliveryReturnLibraryFine = it },
                    label = { Text("Delivery Return Library Fine") },
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
                    value = mDeliveryReturnDepartureDate,
                    onValueChange = { mDeliveryReturnDepartureDate = it },
                    label = { Text("Delivery Return Departure Date") },
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
                    value = mDeliveryReturnArrivalDate,
                    onValueChange = { mDeliveryReturnArrivalDate= it },
                    label = { Text("Delivery Return Arrival Date") },
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
                    deliveryViewModel.submitDeliveryLibraryReturnDetails(
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
                        mDeliveryReturnBookTitle.text.trim(),
                        mDeliveryReturnBookAuthor.text.trim(),
                        mDeliveryReturnBookISBNNumber.text.trim(),
                        mDeliveryReturnBookGenre.text.trim(),
                        mDeliveryReturnBookPublisher.text.trim(),
                        mDeliveryReturnBookSynopsis.text.trim(),
                        mDeliveryReturnBookImageUrl.text.trim(),
                        mDeliveryReturnBookQuantity.text.toIntOrNull()?: 0,
                        clientId,
                        mClientProfilePictureUrl.text.trim(),
                        mClientFullName.text.trim(),
                        mClientEmailAddress.text.trim(),
                        mClientPhoneNumber.text.trim(),
                        mDeliveryLocationName.text.trim(),
                        mDeliveryLocationPrice.text.trim(),
                        mDeliveryLocationDistanceInKm.text.trim(),
                        mDeliveryCartOrderDate.text.trim(),
                        mDeliveryCartOrderStatus.text.trim(),
                        deliveryCartOrderId,
                        mDeliveryDepartureDate.text.trim(),
                        mDeliveryArrivalDate.text.trim(),
                        mDeliveryClientDeliveredDate.text.trim(),
                        deliveryId,
                        mDeliverySetReturnDate.text.trim(),
                        mDeliveryReturnDate.text.trim(),
                        mDeliveryReturnFine.text.toIntOrNull()?: 0,
                        mDeliveryReturnLibraryFine.text.toIntOrNull()?: 0,
                        mDeliveryReturnDepartureDate.text.trim(),
                        mDeliveryReturnArrivalDate.text.trim(),
                        deliveryReturnId
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