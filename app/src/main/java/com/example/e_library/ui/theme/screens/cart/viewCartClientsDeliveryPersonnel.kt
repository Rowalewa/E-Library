package com.example.e_library.ui.theme.screens.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.e_library.R
import com.example.e_library.data.DeliveryViewModel
import com.example.e_library.models.CartOrder
import com.example.e_library.navigation.ROUTE_VIEW_CART_CLIENT
import com.example.e_library.navigation.ROUTE_VIEW_CART_ITEMS_FOR_CLIENT
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelAppTopBar
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelBottomAppBar

@Composable
fun ViewCartClientsDeliveryPersonnel(navController: NavHostController, deliveryPersonnelId: String){
    val context = LocalContext.current
    val deliveryViewModel = remember { DeliveryViewModel(navController, context) }
    var clientList by remember { mutableStateOf(emptyList<CartOrder>()) }

    // Fetch cart orders on first composition
    LaunchedEffect(Unit) {
        deliveryViewModel.getCartClientsForDeliveryPersonnel { clients ->
            clientList = clients
        }
    }


    Box{
        Image(
            painter = painterResource(id = R.drawable.view_my_borrowed_books),
            contentDescription = "View Client Wallpaper",
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                LazyColumn {
                    items(clientList) { cartOrder ->
                        ClientCartItemsDelivery(
                            cartOrder,
                            deliveryPersonnelId,
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
            DeliveryPersonnelBottomAppBar(navController, deliveryPersonnelId)
        }
    }
}

@Composable
fun ClientCartItemsDelivery(
    cartOrder: CartOrder, deliveryPersonnelId: String, navController: NavHostController
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
                painter = rememberAsyncImagePainter(cartOrder.cartOrderClientProfilePictureUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .padding(18.dp)
            )
            Text(
                text = "Client ID: ${cartOrder.clientId}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Client Name: ${cartOrder.cartOrderClientFullName}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Client Email: ${cartOrder.cartOrderClientEmailAddress}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Client Phone number: ${cartOrder.cartOrderClientPhoneNumber}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
            Button(
                onClick =
                { navController.navigate("$ROUTE_VIEW_CART_ITEMS_FOR_CLIENT/$deliveryPersonnelId/${cartOrder.clientId}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "View Cart Items",
                    color = Color.White
                )
            }
        }
    }
    Spacer(
        modifier = Modifier.height(20.dp)
    )
}
