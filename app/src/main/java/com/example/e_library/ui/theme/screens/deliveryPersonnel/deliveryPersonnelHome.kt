package com.example.e_library.ui.theme.screens.deliveryPersonnel

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.data.AuthViewModel
import com.example.e_library.navigation.ROUTE_ABOUT_SCREEN_DELIVERY_PERSONNEL
import com.example.e_library.navigation.ROUTE_DELIVERY_PERSONNEL_FEEDBACK
import com.example.e_library.navigation.ROUTE_DELIVERY_PERSONNEL_HOME
import com.example.e_library.navigation.ROUTE_EULA_DELIVERY_PERSONNEL
import com.example.e_library.navigation.ROUTE_PRIVACY_POLICY_DELIVERY_PERSONNEL
import com.example.e_library.navigation.ROUTE_STAFF_CONTACT_AS_DELIVERY_PERSONNEL
import com.example.e_library.navigation.ROUTE_USER_MANUAL_DELIVERY_PERSONNEL
import com.example.e_library.navigation.ROUTE_VIEW_CART_CLIENTS_DELIVERY_PERSONNEL
import com.example.e_library.navigation.ROUTE_VIEW_DELIVERY_PERSONNEL_ACCOUNT
import com.example.e_library.navigation.ROUTE_VIEW_DELIVERY_RETURN_DELIVERY_PERSONNEL
import com.example.e_library.ui.theme.Pink40
import com.example.e_library.ui.theme.Purple40
import com.example.e_library.ui.theme.Purple80
import com.example.e_library.ui.theme.PurpleGrey40
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DeliveryPersonnelHome(navController: NavController, deliveryPersonnelId: String){
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.delivery_home),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                DeliveryPersonnelAppTopBar(navController, deliveryPersonnelId)
            }
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true,
                        reverseScrolling = false
                    )
                    .padding(20.dp)
            ){
                Card (
                    colors = CardDefaults.cardColors(containerColor = Purple40),
                    border = BorderStroke(1.dp, Color.Yellow),
                    shape = RoundedCornerShape(20.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.cart_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Button(onClick = { navController.navigate("$ROUTE_VIEW_CART_CLIENTS_DELIVERY_PERSONNEL/$deliveryPersonnelId") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "Clients")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Card (
                    colors = CardDefaults.cardColors(containerColor = PurpleGrey40),
                    border = BorderStroke(1.dp, Color.Yellow),
                    shape = RoundedCornerShape(20.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.attendant_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Button(onClick = { navController.navigate("$ROUTE_VIEW_DELIVERY_RETURN_DELIVERY_PERSONNEL/$deliveryPersonnelId") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "My Return Details")
                    }
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryPersonnelAppTopBar(navController: NavController, deliveryPersonnelId: String){
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    val deliveryPersonnel = FirebaseAuth.getInstance().currentUser
    val deliveryPersonnelProfilePictureUrl = deliveryPersonnel?.photoUrl?.toString()
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
                navController.navigate("$ROUTE_DELIVERY_PERSONNEL_HOME/$deliveryPersonnelId")
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
            containerColor = Pink40,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = {
                navController.navigateUp()
            }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription ="Back",
                    tint = Color.Magenta
                )
            }
            IconButton(
                onClick = { expanded = true }
            ) {
                deliveryPersonnelProfilePictureUrl?.let {
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
                        navController.navigate("$ROUTE_VIEW_DELIVERY_PERSONNEL_ACCOUNT/$deliveryPersonnelId")
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Sign Out") },
                    onClick = {
                        val myLogout = AuthViewModel(navController, context)
                        myLogout.deliveryPersonnelLogout()
                    }
                )
                // Add more DropdownMenuItem for other account options
            }
        }

    )
}
@Composable
fun DeliveryPersonnelBottomAppBar(navController: NavController, deliveryPersonnelId: String){
    var expanded by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    BottomAppBar(
        actions = {
            IconButton(onClick = { navController.navigate("$ROUTE_DELIVERY_PERSONNEL_FEEDBACK/$deliveryPersonnelId") }) {
                Icon(
                    Icons.Filled.MailOutline,
                    contentDescription = "Feedback"
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            IconButton(onClick = { navController.navigate("$ROUTE_ABOUT_SCREEN_DELIVERY_PERSONNEL/$deliveryPersonnelId") }) {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "About",
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            IconButton(onClick = { navController.navigate("$ROUTE_STAFF_CONTACT_AS_DELIVERY_PERSONNEL/$deliveryPersonnelId") }) {
                Icon(
                    Icons.Filled.Phone,
                    contentDescription = "Phone Numbers",
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
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
                            navController.navigate("$ROUTE_PRIVACY_POLICY_DELIVERY_PERSONNEL/$deliveryPersonnelId")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "EULA") },
                        onClick = {
                            Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                            navController.navigate("$ROUTE_EULA_DELIVERY_PERSONNEL/$deliveryPersonnelId")

                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "User Manual") },
                        onClick = {
                            Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                            navController.navigate("$ROUTE_USER_MANUAL_DELIVERY_PERSONNEL/$deliveryPersonnelId")
                        }
                    )
                    // Add more DropdownMenuItem for other account options
                }
            }
        }
    )
}