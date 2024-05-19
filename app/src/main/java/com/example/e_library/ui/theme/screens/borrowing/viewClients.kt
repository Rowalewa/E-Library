package com.example.e_library.ui.theme.screens.borrowing

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
import com.example.e_library.data.BooksViewModel
import com.example.e_library.models.Clients
import com.example.e_library.navigation.ROUTE_VIEW_BOOKS
import com.example.e_library.navigation.ROUTE_VIEW_CLIENT_BORROWS
import com.example.e_library.ui.theme.screens.books.StaffAppTopBar
import com.example.e_library.ui.theme.screens.books.StaffBottomAppBar

@Composable
fun ViewClientsScreen(navController: NavHostController, staffId: String){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.view_clients),
            contentDescription = "View Clients Image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                StaffAppTopBar(navController, staffId)
            }
            val context = LocalContext.current
            val clientsRepository = AuthViewModel(navController, context)
            val emptyClientState = remember { mutableStateOf(Clients("", "", "", "", "", "", "", "", "", "", 0.0)) }
            val emptyClientListState = remember { mutableStateListOf<Clients>() }

            val clients = clientsRepository.viewClients(emptyClientState, emptyClientListState)


            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CLIENTS",
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
                    val filteredClients = clients.filter {
                        it.fullName.contains(searchText, ignoreCase = true) ||
                                it.email.contains(searchText, ignoreCase = true) ||
                                it.clientStatus.contains(searchText, ignoreCase = true) ||
                                it.gender.contains(searchText, ignoreCase = true) ||
                                it.maritalStatus.contains(searchText, ignoreCase = true)
                    }
                    items(filteredClients) {
                        ClientInstance(
                            fullName = it.fullName,
                            gender = it.gender,
                            maritalStatus = it.maritalStatus,
                            phoneNumber = it.phoneNumber,
                            dateOfBirth = it.dateOfBirth,
                            email = it.email,
                            clientProfilePictureUrl = it.clientProfilePictureUrl,
                            clientStatus = it.clientStatus,
                            clientId = it.clientId,
                            clientFine = it.fine,
                            staffId = staffId,
                            navController = navController,
                            clientRepository = clientsRepository
                        )
                    }
                }
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
fun ClientInstance(
    fullName: String,
    gender: String,
    maritalStatus: String,
    phoneNumber: String,
    dateOfBirth: String,
    email: String,
    clientProfilePictureUrl: String,
    clientStatus: String,
    clientId: String,
    clientFine: Double,
    staffId: String,
    navController: NavHostController,
    clientRepository: AuthViewModel
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
                painter = rememberAsyncImagePainter(clientProfilePictureUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .padding(18.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = "Client Name: $fullName",
                color = Color.Black
            )
            Text(
                text = "Client Gender: $gender",
                color = Color.Black
            )
            Text(
                text = "Client Marital Status: $maritalStatus",
                color = Color.Black
            )
            Text(
                text = "Client Phone Number: $phoneNumber",
                color = Color.Black
            )
            Text(
                text = "Client Date of Birth: $dateOfBirth",
                color = Color.Black
            )
            Text(
                text = "Client Email: $email",
                color = Color.Black
            )
            Text(
                text = "Client Status: $clientStatus",
                color = Color.Black
            )
            Text(
                text = "Client Id: $clientId",
                color = Color.Black
            )
            Text(
                text = "Client Fine: $clientFine",
                color = Color.Black
            )
            Row(
                modifier = Modifier.background(color = Color.Yellow)
            ) {
                Button(
                    onClick = {
                        clientRepository.deleteClient(clientId)
                    },
                    modifier = Modifier
                        .width(150.dp)
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
                Spacer(modifier = Modifier.width(30.dp))
                Button(
                    onClick = {
                        navController.navigate("$ROUTE_VIEW_BOOKS/$clientId/$staffId")
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(
                            start = 0.dp,
                            end = 20.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(text = "Choose Book")
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = {
                    navController.navigate("$ROUTE_VIEW_CLIENT_BORROWS/$clientId/$staffId")
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
                Text(text = "Borrowed Books")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = {
                    val viewModel = BooksViewModel(navController, context )
                    viewModel.payFine(clientId)
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
                Text(text = "Fine Paid")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = Modifier.background(color = Color.Black)
            ){
                Button(
                    onClick = {
                        val viewModel = BooksViewModel(navController, context )
                        viewModel.suspendClient(clientId)
                    },
                    modifier = Modifier
                        .width(160.dp)
                        .padding(
                            start = 10.dp,
                            end = 0.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "Suspend")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        val viewModel = BooksViewModel(navController, context )
                        viewModel.activateClient(clientId)
                    },
                    modifier = Modifier
                        .width(160.dp)
                        .padding(
                            start = 0.dp,
                            end = 10.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(text = "Activate")
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(150.dp))
}