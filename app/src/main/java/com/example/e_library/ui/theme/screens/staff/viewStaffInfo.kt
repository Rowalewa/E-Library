package com.example.e_library.ui.theme.screens.staff

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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.models.Staff
import com.example.e_library.navigation.ROUTE_EDIT_STAFF_INFO
import com.example.e_library.ui.theme.screens.books.StaffAppTopBar
import com.example.e_library.ui.theme.screens.books.StaffBottomAppBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun ViewStaffInfo(navController: NavHostController, staffId: String){
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
    val email by remember {
        mutableStateOf("")
    }
    val staffStatus by remember {
        mutableStateOf("")
    }
    val staffProfilePictureUrl by remember {
        mutableStateOf("")
    }

    var staffFullName by remember {
        mutableStateOf(TextFieldValue(fullName))
    }
    var staffGender by remember {
        mutableStateOf(TextFieldValue(gender))
    }
    var staffMaritalStatus by remember {
        mutableStateOf(TextFieldValue(maritalStatus))
    }
    var staffPhoneNumber by remember {
        mutableStateOf(TextFieldValue(phoneNumber))
    }
    var staffDateOfBirth by remember {
        mutableStateOf(TextFieldValue(dateOfBirth))
    }
    var staffEmail by remember {
        mutableStateOf(TextFieldValue(email))
    }
    var mStaffStatus by remember {
        mutableStateOf(TextFieldValue(staffStatus))
    }
    var mStaffProfilePictureUrl by remember {
        mutableStateOf(staffProfilePictureUrl)
    }
    val currentDataRef = FirebaseDatabase.getInstance().getReference().child("Staff/$staffId")
    currentDataRef.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val staff = snapshot.getValue(Staff::class.java)
            staffFullName = TextFieldValue(staff!!.fullName)
            staffGender = TextFieldValue(staff.gender)
            staffMaritalStatus = TextFieldValue(staff.maritalStatus)
            staffPhoneNumber = TextFieldValue(staff.phoneNumber)
            staffDateOfBirth = TextFieldValue(staff.dateOfBirth)
            staffEmail = TextFieldValue(staff.email)
            mStaffProfilePictureUrl = staff.staffProfilePictureUrl
            mStaffStatus = TextFieldValue(staff.staffStatus)
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }
    } )

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.view_staff_info),
            contentDescription = "View Staff Wallpaper",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                StaffAppTopBar(navController, staffId)
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
                    painter = rememberAsyncImagePainter(mStaffProfilePictureUrl),
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
                        .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "Name:\n ${staffFullName.text}",
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
                        text = "Gender:\n ${staffGender.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Marital Status: \n ${staffMaritalStatus.text}",
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
                        text = "Phone Number: \n ${staffPhoneNumber.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Date of Birth: \n ${staffDateOfBirth.text}",
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
                        text = "Account Status: \n ${mStaffStatus.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Email Address: \n ${staffEmail.text}",
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
                            .background(color = Color.Yellow)
                    )
                }
                Button(
                    onClick = { navController.navigate("$ROUTE_EDIT_STAFF_INFO/$staffId") },
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
            StaffBottomAppBar(navController, staffId)
        }
    }
}