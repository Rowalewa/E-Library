package com.example.e_library.ui.theme.screens.deliveryReturn

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.data.DeliveryViewModel
import com.example.e_library.models.DeliveryReturn
import com.example.e_library.ui.theme.Purple40
import com.example.e_library.ui.theme.PurpleGrey80
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelAppTopBar
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelBottomAppBar

@Composable
fun ViewDeliveryReturnDeliveryPersonnel(navController: NavHostController, deliveryPersonnelId: String){
    val context = LocalContext.current
    val deliveryViewModel = remember { DeliveryViewModel(navController, context) }
    var deliveryDetails by remember { mutableStateOf<List<DeliveryReturn>>(emptyList()) }

    LaunchedEffect(Unit) {
        deliveryViewModel.getDeliveryReturnDetails(deliveryPersonnelId) { delivery ->
            deliveryDetails = delivery
        }
    }

    Box{
        Image(
            painter = painterResource(id = R.drawable.view_delivery_return_item_delivery_personnel),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column{
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                DeliveryPersonnelAppTopBar(navController, deliveryPersonnelId)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
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
                    val filteredDelivery = deliveryDetails.filter {
                        it.deliveryBookTitle.contains(searchText, ignoreCase = true) ||
                        it.deliveryBookGenre.contains(searchText, ignoreCase = true) ||
                        it.deliveryBookAuthor.contains(searchText, ignoreCase = true) ||
                        it.deliveryBookPublisher.contains(searchText, ignoreCase = true) ||
                        it.deliveryBookISBNNumber.contains(searchText, ignoreCase = true) ||
                        it.deliveryLocationName.contains(searchText, ignoreCase = true) ||
                        it.deliveryPersonnelFullName.contains(searchText, ignoreCase = true) ||
                        it.deliveryPersonnelEmailAddress.contains(searchText, ignoreCase = true) ||
                        it.deliveryPersonnelPhoneNumber.contains(searchText, ignoreCase = true)
                        it.deliveryCartOrderStatus.contains(searchText, ignoreCase = true)
                    }
                    items(filteredDelivery) { deliveryIt ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            DeliveryReturnItemsClient(
                                deliveryId = deliveryIt.deliveryId,
                                deliveryPersonnelId = deliveryIt.deliveryPersonnelId,
                                deliveryPersonnelProfilePictureUrl = deliveryIt.deliveryPersonnelProfilePictureUrl,
                                deliveryPersonnelFullName = deliveryIt.deliveryPersonnelFullName,
                                deliveryPersonnelEmailAddress = deliveryIt.deliveryPersonnelEmailAddress,
                                deliveryPersonnelPhoneNumber = deliveryIt.deliveryPersonnelPhoneNumber,
                                deliveryBookId = deliveryIt.deliveryBookId,
                                deliveryBookTitle = deliveryIt.deliveryBookTitle,
                                deliveryBookAuthor = deliveryIt.deliveryBookAuthor,
                                deliveryBookISBNNumber = deliveryIt.deliveryBookISBNNumber,
                                deliveryBookGenre = deliveryIt.deliveryBookGenre,
                                deliveryBookPublisher = deliveryIt.deliveryBookPublisher,
                                deliveryBookSynopsis = deliveryIt.deliveryBookSynopsis,
                                deliveryBookImageUrl = deliveryIt.deliveryBookImageUrl,
                                deliveryBookQuantity = deliveryIt.deliveryBookQuantity,
                                deliveryClientId = deliveryIt.deliveryClientId,
                                deliveryClientProfilePictureUrl = deliveryIt.deliveryClientProfilePictureUrl,
                                deliveryClientFullName = deliveryIt.deliveryClientFullName,
                                deliveryClientEmailAddress = deliveryIt.deliveryClientEmailAddress,
                                deliveryClientPhoneNumber = deliveryIt.deliveryClientPhoneNumber,
                                deliveryLocationName = deliveryIt.deliveryLocationName,
                                deliveryLocationPrice = deliveryIt.deliveryLocationPrice,
                                deliveryLocationDistanceInKm = deliveryIt.deliveryLocationDistanceInKm,
                                deliveryCartOrderDate = deliveryIt.deliveryCartOrderDate,
                                deliveryCartOrderStatus = deliveryIt.deliveryCartOrderStatus,
                                deliveryCartOrderId = deliveryIt.deliveryCartOrderId,
                                deliveryDepartureDate = deliveryIt.deliveryDepartureDate,
                                deliveryArrivalDate = deliveryIt.deliveryArrivalDate,
                                deliveryClientDeliveredDate = deliveryIt.deliveryClientDeliveredDate,
                                deliverySetReturnDate = deliveryIt.deliverySetReturnDate,
                                deliveryReturnDate = deliveryIt.deliveryReturnDate,
                                deliveryReturnFine = deliveryIt.deliveryReturnFine,
                                deliveryReturnLibraryFine = deliveryIt.deliveryReturnLibraryFine,
                                deliveryReturnDepartureDate = deliveryIt.deliveryReturnDepartureDate,
                                deliveryReturnArrivalDate = deliveryIt.deliveryReturnArrivalDate,
                                navController
                            )
//
                        }
                    }
                }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeliveryReturnItemsDeliveryPersonnel(
    deliveryId: String,
    deliveryPersonnelId: String,
    deliveryPersonnelProfilePictureUrl: String,
    deliveryPersonnelFullName: String,
    deliveryPersonnelEmailAddress: String,
    deliveryPersonnelPhoneNumber: String,
    deliveryBookId: String,
    deliveryBookTitle: String,
    deliveryBookAuthor: String,
    deliveryBookISBNNumber: String,
    deliveryBookGenre: String,
    deliveryBookPublisher: String,
    deliveryBookSynopsis: String,
    deliveryBookImageUrl: String,
    deliveryBookQuantity: Int,
    deliveryClientId: String,
    deliveryClientProfilePictureUrl: String,
    deliveryClientFullName: String,
    deliveryClientEmailAddress: String,
    deliveryClientPhoneNumber: String,
    deliveryLocationName: String,
    deliveryLocationPrice: String,
    deliveryLocationDistanceInKm: String,
    deliveryCartOrderDate: String,
    deliveryCartOrderStatus: String,
    deliveryCartOrderId: String,
    deliveryDepartureDate: String,
    deliveryArrivalDate: String,
    deliveryClientDeliveredDate: String,
    deliverySetReturnDate: String,
    deliveryReturnDate: String,
    deliveryReturnFine: Int,
    deliveryReturnLibraryFine: Int,
    deliveryReturnDepartureDate: String,
    deliveryReturnArrivalDate: String,
    navController: NavHostController
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                top = 0.dp,
                end = 20.dp,
                bottom = 0.dp
            )
            .clip(shape = CutCornerShape(20.dp))
            .border(width = 5.dp, shape = CutCornerShape(20.dp), color = Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.Green)
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Book Details",
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 20.sp
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = PurpleGrey80)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(deliveryBookImageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .padding(18.dp)
                )
                Text(
                    text = "Book Id:\n $deliveryBookId",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Book Title: \n $deliveryBookTitle",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Book Author: \n $deliveryBookAuthor",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Book ISBN Number: \n $deliveryBookISBNNumber",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Book Genre:\n $deliveryBookGenre",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Book Publisher:\n $deliveryBookPublisher",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Book Synopsis:\n $deliveryBookSynopsis",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Quantity Ordered:\n $deliveryBookQuantity",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "My Details",
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 20.sp
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = PurpleGrey80)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(deliveryPersonnelProfilePictureUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .padding(18.dp)
                )
                Text(
                    text = "My Full Name:\n $deliveryPersonnelFullName",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "My Email Address:\n $deliveryPersonnelEmailAddress",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "My Phone Number:\n $deliveryPersonnelPhoneNumber",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Client Details",
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 20.sp
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = PurpleGrey80)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(deliveryClientProfilePictureUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .padding(18.dp)
                )
                Text(
                    text = "Client Id:\n $deliveryClientId",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Client Full Name:\n $deliveryClientFullName",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Client Email Address:\n $deliveryClientEmailAddress",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Client Phone Number:\n $deliveryClientPhoneNumber",
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$deliveryClientPhoneNumber")
                            }
                            context.startActivity(intent)
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Blue)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Order Details",
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 20.sp
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = Purple40)
            ) {
                Text(
                    text = "Order Location: \n $deliveryLocationName",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Order Distance(Km):\n $deliveryLocationDistanceInKm",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Order Price: \n $deliveryLocationPrice",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Order Date: \n $deliveryCartOrderDate",
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Order Status: \n $deliveryCartOrderStatus",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Departure Date: \n $deliveryDepartureDate",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                if (deliveryArrivalDate.isBlank()) {
                    Text(
                        text = "Arrival Date: \n Soon",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Arrival Date: \n $deliveryArrivalDate",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                }
                if (deliveryClientDeliveredDate.isBlank()) {
                    Text(
                        text = "Client Delivered Date: \n Not picked up by client",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Client Delivered Date: \n $deliveryClientDeliveredDate",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                }
                if (deliverySetReturnDate.isBlank()) {
                    Text(
                        text = "Delivery Set Return Date: \n Not Set",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Delivery Set Return Date: \n $deliverySetReturnDate",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                }
                if (deliveryReturnDate.isBlank()) {
                    Text(
                        text = "Delivery Return Date: \n Not returned",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Delivery Return Date: \n $deliveryReturnDate",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = "Delivery Return Fine: \n $deliveryReturnFine",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Delivery Return Library Fine: \n $deliveryReturnLibraryFine",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                if (deliveryReturnDepartureDate.isNotBlank()) {
                    Text(
                        text = "Delivery Return Departure Date: \n $deliveryReturnDepartureDate",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }else{
                    Text(
                        text = "Delivery Return Departure Date: \n Book is still at pickup point.",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                if (deliveryReturnArrivalDate.isNotBlank()) {
                    Text(
                        text = "Delivery Return Arrival Date: \n $deliveryReturnArrivalDate",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }else{
                    Text(
                        text = "Delivery Return Arrival Date: \n Book not returned to library.",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
            when (deliveryCartOrderStatus) {
                "Depot Returned" -> {
                    Text(
                        text = "The order was returned to depot successfully. Please go for it $deliveryPersonnelFullName",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                "Library Delivery" -> {
                    Text(
                        text = "The order is under delivery to library. Travel Safely",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                "Library Returned" -> {
                    Text(
                        text = "The order was returned to library successfully. Thank you for your service $deliveryPersonnelFullName.",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                else ->{
                    Text(
                        text = "Invalid status? We are taking care of it.",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(150.dp))
}