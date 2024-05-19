package com.example.e_library.ui.theme.screens.clients

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
import com.example.e_library.models.Clients
import com.example.e_library.navigation.ROUTE_EDIT_CLIENT_INFO
import com.example.e_library.ui.theme.screens.borrowing.ClientAppTopBar
import com.example.e_library.ui.theme.screens.borrowing.ClientBottomAppBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun ViewClientInfo(navController: NavHostController, clientId: String) {
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
    val clientProfilePictureUrl by remember {
        mutableStateOf("")
    }
    val clientStatus by remember {
        mutableStateOf("")
    }
    val fine by remember {
        mutableStateOf("")
    }

    var clientFullName by remember {
        mutableStateOf(TextFieldValue(fullName))
    }
    var clientGender by remember {
        mutableStateOf(TextFieldValue(gender))
    }
    var clientMaritalStatus by remember {
        mutableStateOf(TextFieldValue(maritalStatus))
    }
    var clientPhoneNumber by remember {
        mutableStateOf(TextFieldValue(phoneNumber))
    }
    var clientDateOfBirth by remember {
        mutableStateOf(TextFieldValue(dateOfBirth))
    }
    var clientEmail by remember {
        mutableStateOf(TextFieldValue(email))
    }
    var mClientProfilePictureUrl by remember {
        mutableStateOf(clientProfilePictureUrl)
    }
    var mClientStatus by remember {
        mutableStateOf(clientStatus)
    }
    var clientFine by remember {
        mutableStateOf(TextFieldValue(fine))
    }
    val currentDataRef = FirebaseDatabase.getInstance().getReference().child("Client/$clientId")
    currentDataRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val client = snapshot.getValue(Clients::class.java)
            clientFullName = TextFieldValue(client!!.fullName)
            clientGender = TextFieldValue(client.gender)
            clientMaritalStatus = TextFieldValue(client.maritalStatus)
            clientPhoneNumber = TextFieldValue(client.phoneNumber)
            clientDateOfBirth = TextFieldValue(client.dateOfBirth)
            clientEmail = TextFieldValue(client.email)
            mClientStatus = TextFieldValue(client.clientStatus).text
            mClientProfilePictureUrl = client.clientProfilePictureUrl
            clientFine = TextFieldValue(client.fine.toString())
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.view_client_info),
            contentDescription = "View Client Wallpaper",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                ClientAppTopBar(navController, clientId)
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
                    painter = rememberAsyncImagePainter(mClientProfilePictureUrl),
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
                        text = "Name:\n ${clientFullName.text}",
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
                    )
                    Text(
                        text = "Gender:\n ${clientGender.text}",
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
                        text = "Marital Status: \n ${clientMaritalStatus.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Phone Number: \n ${clientPhoneNumber.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Yellow)
                    )
                    Text(
                        text = "Date of Birth: \n ${clientDateOfBirth.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Status: \n $mClientStatus",
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
                        text = "Email Address: \n ${clientEmail.text}",
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
                            .background(color = Color.Magenta)
                    )
                    Text(
                        text = "Fine: \n ${clientFine.text}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Button(
                    onClick = { navController.navigate("$ROUTE_EDIT_CLIENT_INFO/$clientId") },
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
            ClientBottomAppBar(navController, clientId)
        }
    }
}