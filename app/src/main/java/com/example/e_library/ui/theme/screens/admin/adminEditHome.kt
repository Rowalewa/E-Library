package com.example.e_library.ui.theme.screens.admin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.data.AuthViewModel
import com.example.e_library.models.Admin
import com.example.e_library.navigation.ROUTE_ABOUT_SCREEN_ADMIN
import com.example.e_library.navigation.ROUTE_ADMIN_ATTENDANT_EDIT
import com.example.e_library.navigation.ROUTE_ADMIN_CLIENT_EDIT
import com.example.e_library.navigation.ROUTE_ADMIN_DELIVERY_PERSONNEL_EDIT
import com.example.e_library.navigation.ROUTE_ADMIN_EDIT_HOME
import com.example.e_library.navigation.ROUTE_ADMIN_FEEDBACK
import com.example.e_library.navigation.ROUTE_ADMIN_STAFF_EDIT
import com.example.e_library.navigation.ROUTE_ADMIN_VIEW_ACCOUNT
import com.example.e_library.navigation.ROUTE_EULA_ADMIN
import com.example.e_library.navigation.ROUTE_PRIVACY_POLICY_ADMIN
import com.example.e_library.navigation.ROUTE_STAFF_CONTACT_AS_ADMIN
import com.example.e_library.navigation.ROUTE_USER_MANUAL_ADMIN
import com.example.e_library.ui.theme.PurpleGrey40
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun AdminEditHome(navController: NavController, adminId: String){
    val context = LocalContext.current
    val fullName by remember {
        mutableStateOf("")
    }
    var adminFullName by remember {
        mutableStateOf(TextFieldValue(fullName))
    }
    val currentDataRef = FirebaseDatabase.getInstance().getReference().child("Admin/$adminId")
    currentDataRef.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val admin = snapshot.getValue(Admin::class.java)
            adminFullName = TextFieldValue(admin!!.fullName)
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }
    } )
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.admin_edit_home_screen),
            contentDescription = "View Clients Image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                AdminAppTopBar(navController, adminId)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(state = rememberScrollState(), enabled = true, reverseScrolling = false)
            ) {
                Text(
                    text = "ACTIONS",
                    color = Color.Red,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(15.dp))
                val visible by remember { mutableStateOf(true) }

                if (visible) {
                    val timeOfDay = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                        in 0..11 -> "morning"
                        in 12..16 -> "afternoon"
                        else -> "evening"
                    }

                    Card(
                        modifier = Modifier.padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Cyan),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp,
                            focusedElevation = 10.dp,
                            pressedElevation = 20.dp,
                            hoveredElevation = 15.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Good $timeOfDay",
                                color = Color.Magenta
                            )
                            Text(
                                text = adminFullName.text,
                                fontSize = 25.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color.Red
                            )
                        }
                    }
                    LaunchedEffect(key1 = true) {
                        delay(250000) // Dismiss after 250 seconds
//                    visible = false
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    colors = CardDefaults.cardColors(Color.Blue)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.staff_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Button(
                        onClick = { navController.navigate("$ROUTE_ADMIN_STAFF_EDIT/$adminId") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Edit Staff")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Card(
                    colors = CardDefaults.cardColors(Color.Red)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.client_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Button(
                        onClick = { navController.navigate("$ROUTE_ADMIN_CLIENT_EDIT/$adminId") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Edit Client")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Card(
                    colors = CardDefaults.cardColors(Color.Yellow)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.delivery_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Button(
                        onClick = { navController.navigate("$ROUTE_ADMIN_DELIVERY_PERSONNEL_EDIT/$adminId") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Edit Delivery Personnel")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Card (
                    colors = CardDefaults.cardColors(containerColor = PurpleGrey40)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.attendant_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(11.dp)
                    )
                    Button(
                        onClick = { navController.navigate("$ROUTE_ADMIN_ATTENDANT_EDIT/$adminId") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Edit Attendants")
                    }
                }
                Spacer(modifier = Modifier.height(150.dp))
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAppTopBar(navController: NavController, adminId: String){
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    val admin = FirebaseAuth.getInstance().currentUser
    val adminProfilePictureUrl = admin?.photoUrl?.toString()
    TopAppBar(
        title = {
            Text(
                text = "E-Library",
                fontFamily = FontFamily.Serif,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            )
        },
        navigationIcon ={
            IconButton(onClick = {
                navController.navigate("$ROUTE_ADMIN_EDIT_HOME/$adminId")
                Toast.makeText(context, "You are at Home Screen", Toast.LENGTH_SHORT).show()}
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription ="Home",
                    tint = Color.Yellow
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = {
                navController.navigateUp()
            }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription ="Back",
                    tint = Color.Magenta
                )
            }
            IconButton(
                onClick = { expanded = true }
            ) {
                adminProfilePictureUrl?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                } ?: Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "menu",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "My Account") },
                    onClick = {
                        Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                        navController.navigate("$ROUTE_ADMIN_VIEW_ACCOUNT/$adminId")
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Sign Out") },
                    onClick = {
                        val myLogout = AuthViewModel(navController, context)
                        myLogout.adminLogout()
                    }
                )
                // Add more DropdownMenuItem for other account options
            }
        }

    )
}
@Composable
fun AdminBottomAppBar(navController: NavController, adminId: String){
    var expanded by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    BottomAppBar(
        actions = {
            IconButton(onClick = { navController.navigate("$ROUTE_ADMIN_FEEDBACK/$adminId") }) {
                Icon(
                    Icons.Filled.MailOutline,
                    contentDescription = "Feedback"
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            IconButton(onClick = { navController.navigate("$ROUTE_ABOUT_SCREEN_ADMIN/$adminId") }) {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "About",
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            IconButton(onClick = { navController.navigate("$ROUTE_STAFF_CONTACT_AS_ADMIN/$adminId") }) {
                Icon(
                    Icons.Filled.Phone,
                    contentDescription = "Phone Numbers",
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { expanded = true },
                containerColor = Color.Black,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Localized description",
                    tint = Color.Yellow
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Privacy Policy") },
                        onClick = {
                            Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                            navController.navigate("$ROUTE_PRIVACY_POLICY_ADMIN/$adminId")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "EULA") },
                        onClick = {
                            Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                            navController.navigate("$ROUTE_EULA_ADMIN/$adminId")

                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "User Manual") },
                        onClick = {
                            Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                            navController.navigate("$ROUTE_USER_MANUAL_ADMIN/$adminId")
                        }
                    )
                    // Add more DropdownMenuItem for other account options
                }
            }
        }
    )
}