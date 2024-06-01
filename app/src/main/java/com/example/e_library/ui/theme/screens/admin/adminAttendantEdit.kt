package com.example.e_library.ui.theme.screens.admin

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
import androidx.compose.foundation.shape.CircleShape
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
import com.example.e_library.data.AuthViewModel
import com.example.e_library.models.Attendant

@Composable
fun AdminAttendantEdit(navController: NavHostController, adminId: String){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.admin_attendant_edit),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val context = LocalContext.current
            val attendantRepository = AuthViewModel(navController, context)
            val attendantState = remember { mutableStateOf(Attendant("", "", "", "", "", "", "", "", "", "")) }
            val emptyAttendantListState = remember { mutableStateListOf<Attendant>() }

            val attendant = attendantRepository.viewAttendant(attendantState, emptyAttendantListState)


            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AdminAppTopBar(navController, adminId)
                Text(
                    text = "DELIVERY PERSONNEL",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Red
                )

                Spacer(modifier = Modifier.height(10.dp))
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
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        start = 0.dp,
                        end = 0.dp,
                        bottom = 20.dp,
                        top = 0.dp
                    )
                ) {
                    val filteredAttendants = attendant.filter {
                        it.fullName.contains(searchText, ignoreCase = true) ||
                                it.email.contains(searchText, ignoreCase = true) ||
                                it.gender.contains(searchText, ignoreCase = true) ||
                                it.accountStatus.contains(searchText, ignoreCase = true) ||
                                it.maritalStatus.contains(searchText, ignoreCase = true)
                    }
                    items(filteredAttendants) {
                        AttendantInstance(
                            fullName = it.fullName,
                            gender = it.gender,
                            maritalStatus = it.maritalStatus,
                            phoneNumber = it.phoneNumber,
                            dateOfBirth = it.dateOfBirth,
                            accountStatus = it.accountStatus,
                            email = it.email,
                            attendantPersonnelProfilePictureUrl = it.attendantProfilePictureUrl,
                            attendantId = it.attendantId,
                            navController = navController,
                            attendantRepository = attendantRepository
                        )
                    }
                }
            }
        }
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            AdminBottomAppBar(navController, adminId)
        }
    }
}

@Composable
fun AttendantInstance(
    fullName: String,
    gender: String,
    maritalStatus: String,
    phoneNumber: String,
    dateOfBirth: String,
    accountStatus: String,
    email: String,
    attendantPersonnelProfilePictureUrl: String,
    attendantId: String,
    navController: NavHostController,
    attendantRepository: AuthViewModel
) {
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(
            start = 20.dp,
            top = 0.dp,
            end = 20.dp,
            bottom = 0.dp
        )
        .clip(shape = CutCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.Green)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(attendantPersonnelProfilePictureUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .padding(18.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = "Attendant Name: $fullName",
                color = Color.Black
            )
            Text(
                text = "Attendant Gender: $gender",
                color = Color.Black
            )
            Text(
                text = "Attendant Marital Status: $maritalStatus",
                color = Color.Black
            )
            Text(
                text = "Attendant Phone Number: $phoneNumber",
                color = Color.Black
            )
            Text(
                text = "Attendant Date of Birth: $dateOfBirth",
                color = Color.Black
            )
            Text(
                text = "Attendant Account Status: $accountStatus",
                color = Color.Black
            )
            Text(
                text = "Attendant Email: $email",
                color = Color.Black
            )
            Text(
                text = "Attendant Id: $attendantId",
                color = Color.Black
            )
            Row(
                modifier = Modifier.background(color = Color.Yellow)
            ) {
                Button(
                    onClick = {
                        attendantRepository.deleteAttendantRecord(attendantId)
                    },
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
                    Text(text = "Delete")
                }
            }
            Button(
                onClick = {
                    val viewModel = AuthViewModel(navController, context )
                    viewModel.verifyAttendant(attendantId)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    ),
                colors = ButtonDefaults.buttonColors(Color.Magenta)
            ) {
                Text(text = "Verify Account")
            }
        }
    }
    Spacer(modifier = Modifier.height(150.dp))
}