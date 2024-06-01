package com.example.e_library.ui.theme.screens.deliveryPersonnel

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.data.AuthViewModel
import com.example.e_library.models.DeliveryPersonnel
import com.example.e_library.navigation.ROUTE_EDIT_DELIVERY_PERSONNEL_ACCOUNT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun DeliveryPersonnelViewAccount(navController: NavHostController, deliveryPersonnelId: String){
    val context = LocalContext.current
    val fullName by remember {
        mutableStateOf("")
    }
    val gender by remember {
        mutableStateOf("")
    }
    val maritalStatus by remember {
        mutableStateOf("")
    }
    val phoneNumber by remember {
        mutableStateOf("")
    }
    val dateOfBirth by remember {
        mutableStateOf("")
    }
    val accountStatus by remember {
        mutableStateOf("")
    }
    val deliveryStatus by remember {
        mutableStateOf("")
    }
    val vehicle by remember {
        mutableStateOf("")
    }
    val email by remember {
        mutableStateOf("")
    }
    val deliveryPersonnelProfilePictureUrl by remember {
        mutableStateOf("")
    }

    var deliveryPersonnelFullName by remember {
        mutableStateOf(TextFieldValue(fullName))
    }
    var deliveryPersonnelGender by remember {
        mutableStateOf(TextFieldValue(gender))
    }
    var deliveryPersonnelMaritalStatus by remember {
        mutableStateOf(TextFieldValue(maritalStatus))
    }
    var deliveryPersonnelPhoneNumber by remember {
        mutableStateOf(TextFieldValue(phoneNumber))
    }
    var deliveryPersonnelDateOfBirth by remember {
        mutableStateOf(TextFieldValue(dateOfBirth))
    }
    var deliveryPersonnelAccountStatus by remember {
        mutableStateOf(TextFieldValue(accountStatus))
    }
    var deliveryPersonnelDeliveryStatus by remember {
        mutableStateOf(TextFieldValue(deliveryStatus))
    }
    var deliveryPersonnelVehicle by remember {
        mutableStateOf(TextFieldValue(vehicle))
    }
    var deliveryPersonnelEmail by remember {
        mutableStateOf(TextFieldValue(email))
    }
    var mDeliveryPersonnelProfilePictureUrl by remember {
        mutableStateOf(deliveryPersonnelProfilePictureUrl)
    }
    val currentDataRef = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel/$deliveryPersonnelId")
    currentDataRef.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val deliveryPersonnel = snapshot.getValue(DeliveryPersonnel::class.java)
            deliveryPersonnelFullName = TextFieldValue(deliveryPersonnel!!.fullName)
            deliveryPersonnelGender = TextFieldValue(deliveryPersonnel.gender)
            deliveryPersonnelMaritalStatus = TextFieldValue(deliveryPersonnel.maritalStatus)
            deliveryPersonnelPhoneNumber = TextFieldValue(deliveryPersonnel.phoneNumber)
            deliveryPersonnelDateOfBirth = TextFieldValue(deliveryPersonnel.dateOfBirth)
            deliveryPersonnelAccountStatus = TextFieldValue(deliveryPersonnel.accountStatus)
            deliveryPersonnelDeliveryStatus = TextFieldValue(deliveryPersonnel.deliveryStatus)
            deliveryPersonnelVehicle = TextFieldValue(deliveryPersonnel.vehicle)
            deliveryPersonnelEmail = TextFieldValue(deliveryPersonnel.email)
            mDeliveryPersonnelProfilePictureUrl = deliveryPersonnel.deliveryPersonnelProfilePictureUrl
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }
    } )

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.delivery_personnel_view_account),
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(
                    state = rememberScrollState(),
                    enabled = true,
                    reverseScrolling = true
                )
            ) {
                Image(
                    painter = rememberAsyncImagePainter(mDeliveryPersonnelProfilePictureUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(200.dp)
                        .clip(shape = CircleShape)
                )
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .border(
                            width = 5.dp,
                            color = Color.Magenta,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Text(
                        text = "Name:\n ${deliveryPersonnelFullName.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 15.dp,
                                bottom = 0.dp,
                                end = 0.dp,
                                start = 0.dp
                            )
                            .background(color = Color.Yellow)
                    )
                    Text(
                        text = "Gender:\n ${deliveryPersonnelGender.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Marital Status: \n ${deliveryPersonnelMaritalStatus.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Yellow)
                    )
                    Text(
                        text = "Phone Number: \n ${deliveryPersonnelPhoneNumber.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Date of Birth: \n ${deliveryPersonnelDateOfBirth.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Yellow)
                    )
                    Text(
                        text = "Account Status: \n ${deliveryPersonnelAccountStatus.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Delivery Status: \n ${deliveryPersonnelDeliveryStatus.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Vehicle: \n ${deliveryPersonnelVehicle.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Yellow)
                    )
                    Text(
                        text = "Email Address: \n ${deliveryPersonnelEmail.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 0.dp,
                                bottom = 15.dp,
                                end = 0.dp,
                                start = 0.dp
                            )
                    )
                }
                Row {
                    Button(onClick = {
                        val myDeliveryStatus = AuthViewModel(navController, context)
                        myDeliveryStatus.deliveryStatusAvailable(deliveryPersonnelId)
                    }) {
                        Text(
                            text = "Available"
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(onClick = {
                        val myDeliveryStatus = AuthViewModel(navController, context)
                        myDeliveryStatus.deliveryStatusUnavailable(deliveryPersonnelId)
                    }) {
                        Text(
                            text = "Unavailable"
                        )
                    }
                }
                Button(
                    onClick = { navController.navigate("$ROUTE_EDIT_DELIVERY_PERSONNEL_ACCOUNT/$deliveryPersonnelId") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                ) {
                    Text(text = "Edit")
                }
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                ) {
                    Text(text = "Back")
                }
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            DeliveryPersonnelBottomAppBar(navController, deliveryPersonnelId)
        }
    }
}