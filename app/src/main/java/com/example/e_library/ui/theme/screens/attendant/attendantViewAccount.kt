package com.example.e_library.ui.theme.screens.attendant

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.models.Admin
import com.example.e_library.models.Attendant
import com.example.e_library.navigation.ROUTE_ATTENDANT_EDIT_ACCOUNT
import com.example.e_library.navigation.ROUTE_ATTENDANT_REGISTER
import com.example.e_library.ui.theme.screens.admin.AdminAppTopBar
import com.example.e_library.ui.theme.screens.admin.AdminBottomAppBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun AttendantViewAccount(navController: NavController, attendantId: String){
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
    val email by remember {
        mutableStateOf("")
    }
    val attendantProfilePictureUrl by remember {
        mutableStateOf("")
    }

    var attendantFullName by remember {
        mutableStateOf(TextFieldValue(fullName))
    }
    var attendantGender by remember {
        mutableStateOf(TextFieldValue(gender))
    }
    var attendantMaritalStatus by remember {
        mutableStateOf(TextFieldValue(maritalStatus))
    }
    var attendantPhoneNumber by remember {
        mutableStateOf(TextFieldValue(phoneNumber))
    }
    var attendantDateOfBirth by remember {
        mutableStateOf(TextFieldValue(dateOfBirth))
    }
    var attendantAccountStatus by remember {
        mutableStateOf(TextFieldValue(accountStatus))
    }
    var attendantEmail by remember {
        mutableStateOf(TextFieldValue(email))
    }
    var mAttendantProfilePictureUrl by remember {
        mutableStateOf(attendantProfilePictureUrl)
    }
    val currentDataRef = FirebaseDatabase.getInstance().getReference().child("Attendant/$attendantId")
    currentDataRef.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val attendant = snapshot.getValue(Attendant::class.java)
            attendantFullName = TextFieldValue(attendant!!.fullName)
            attendantGender = TextFieldValue(attendant.gender)
            attendantMaritalStatus = TextFieldValue(attendant.maritalStatus)
            attendantPhoneNumber = TextFieldValue(attendant.phoneNumber)
            attendantDateOfBirth = TextFieldValue(attendant.dateOfBirth)
            attendantAccountStatus =  TextFieldValue(attendant.accountStatus)
            attendantEmail = TextFieldValue(attendant.email)
            mAttendantProfilePictureUrl = attendant.attendantProfilePictureUrl
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }
    } )

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.attendant_view_account),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                AttendantAppTopBar(navController, attendantId)
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
                    painter = rememberAsyncImagePainter(mAttendantProfilePictureUrl),
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
                        text = "Name:\n ${attendantFullName.text}",
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
                        text = "Gender:\n ${attendantGender.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Marital Status: \n ${attendantMaritalStatus.text}",
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
                        text = "Phone Number: \n ${attendantPhoneNumber.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Date of Birth: \n ${attendantDateOfBirth.text}",
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
                        text = "Email Address: \n ${attendantEmail.text}",
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
                Button(
                    onClick = { navController.navigate("$ROUTE_ATTENDANT_EDIT_ACCOUNT/$attendantId") },
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
            AttendantBottomAppBar(navController, attendantId)
        }
    }
}