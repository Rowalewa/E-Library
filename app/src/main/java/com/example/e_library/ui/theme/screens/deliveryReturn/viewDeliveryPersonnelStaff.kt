package com.example.e_library.ui.theme.screens.deliveryReturn

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.data.DeliveryViewModel
import com.example.e_library.models.Delivery
import com.example.e_library.models.DeliveryReturn
import com.example.e_library.navigation.ROUTE_COURIER_VIEW_CLIENT_DELIVERY
import com.example.e_library.navigation.ROUTE_VIEW_CART_ITEMS_FOR_CLIENT
import com.example.e_library.navigation.ROUTE_VIEW_DELIVERY_RETURN_STAFF
import com.example.e_library.ui.theme.screens.books.StaffAppTopBar
import com.example.e_library.ui.theme.screens.books.StaffBottomAppBar
import com.example.e_library.ui.theme.screens.borrowing.ClientAppTopBar

@Composable
fun ViewDeliveryPersonnelStaff(navController: NavHostController, staffId: String){
    val context = LocalContext.current
    val deliveryViewModel = remember { DeliveryViewModel(navController, context) }
    var deliveryPersonnelList by remember { mutableStateOf(emptyList<DeliveryReturn>()) }

    // Fetch cart orders on first composition
    LaunchedEffect(Unit) {
        deliveryViewModel.getDeliveryPersonnelInDelivery { delivery ->
            deliveryPersonnelList = delivery
        }
    }


    Box{
        Image(
            painter = painterResource(id = R.drawable.view_delivery_personnel_staff),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column{
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                StaffAppTopBar(navController, staffId)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
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
                Spacer(modifier = Modifier.height(5.dp))
                LazyColumn {
                    val filteredDeliveryPersonnel = deliveryPersonnelList.filter {
                        it.deliveryPersonnelFullName.contains(searchText, ignoreCase = true) ||
                        it.deliveryPersonnelEmailAddress.contains(searchText, ignoreCase = true) ||
                        it.deliveryPersonnelPhoneNumber.contains(searchText, ignoreCase = true)
                    }
                    items(filteredDeliveryPersonnel) { delivery ->
                        DeliveryPersonnelInDeliveryStaff(
                            delivery,
                            staffId,
                            navController
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
fun DeliveryPersonnelInDeliveryStaff(
    delivery: DeliveryReturn,
    staffId: String,
    navController: NavHostController
) {
    // Display the client's orders or summary here
    OutlinedCard (
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        border = BorderStroke(5.dp, Color.Black)
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(delivery.deliveryPersonnelProfilePictureUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .padding(18.dp)
            )
            Text(
                text = "Delivery Personnel Name: ${delivery.deliveryPersonnelFullName}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Delivery Personnel Email: ${delivery.deliveryPersonnelEmailAddress}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Delivery Personnel Phone Number: ${delivery.deliveryPersonnelPhoneNumber}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
            Button(onClick = { navController.navigate("$ROUTE_VIEW_DELIVERY_RETURN_STAFF/${delivery.deliveryPersonnelId}/$staffId") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
            ) {
                Text(
                    text = "View Deliveries",
                    color = Color.Black
                )
            }
        }
    }
    Spacer(
        modifier = Modifier.height(150.dp)
    )
}