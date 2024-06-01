package com.example.e_library.ui.theme.screens.cart


import com.example.e_library.ui.theme.screens.borrowing.ClientAppTopBar
import com.example.e_library.ui.theme.screens.borrowing.ClientBottomAppBar
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.e_library.models.CartOrder
import com.example.e_library.navigation.ROUTE_CLIENT_VIEW_MY_DELIVERY

@Composable
fun ViewBooksAddedToCart(navController: NavHostController, clientId: String){
    val context = LocalContext.current
    val deliveryViewModel = remember { DeliveryViewModel(navController, context) }
    var cartOrdersBooks by remember { mutableStateOf<List<CartOrder>>(emptyList()) }

    LaunchedEffect(Unit) {
        deliveryViewModel.getCartBooksForClient(clientId) { books ->
            cartOrdersBooks = books
        }
    }

    Box{
        Image(
            painter = painterResource(id = R.drawable.view_cart_client),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column{
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                ClientAppTopBar(navController, clientId)
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
                    val filteredCart = cartOrdersBooks.filter {
                        it.cartOrderBookTitle.contains(searchText, ignoreCase = true) ||
                        it.cartOrderBookGenre.contains(searchText, ignoreCase = true) ||
                        it.cartOrderBookAuthor.contains(searchText, ignoreCase = true) ||
                        it.cartOrderBookPublisher.contains(searchText, ignoreCase = true) ||
                        it.cartOrderBookISBNNumber.contains(searchText, ignoreCase = true) ||
                        it.cartOrderLocationName.contains(searchText, ignoreCase = true) ||
                        it.cartOrderStatus.contains(searchText, ignoreCase = true)
                    }
                    items(filteredCart) { cart ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            BookCartItems(
                                bookId = cart.cartOrderBookId,
                                cartOrderBookTitle = cart.cartOrderBookTitle,
                                cartOrderBookAuthor = cart.cartOrderBookAuthor,
                                cartOrderBookISBNNumber = cart.cartOrderBookISBNNumber,
                                cartOrderBookGenre = cart.cartOrderBookGenre,
                                cartOrderBookPublisher = cart.cartOrderBookPublisher,
                                cartOrderBookSynopsis = cart.cartOrderBookSynopsis,
                                cartOrderBookImageUrl = cart.cartOrderBookImageUrl,
                                cartOrderBookQuantity = cart.cartOrderBookQuantity,
                                cartOrderClientProfilePictureUrl = cart.cartOrderClientProfilePictureUrl,
                                clientId = cart.clientId,
                                cartOrderClientFullName = cart.cartOrderClientFullName,
                                cartOrderClientEmailAddress = cart.cartOrderClientEmailAddress,
                                cartOrderClientPhoneNumber = cart.cartOrderClientPhoneNumber,
                                cartOrderLocationName = cart.cartOrderLocationName,
                                cartOrderLocationPrice = cart.cartOrderLocationPrice,
                                cartOrderLocationDistanceInKm = cart.cartOrderLocationDistanceInKm,
                                cartOrderDate = cart.cartOrderDate,
                                cartOrderStatus = cart.cartOrderStatus,
                                cartOrderId = cart.cartOrderId,
                                navController = navController
                            )
                        }
                    }
                }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookCartItems(
    bookId: String,
    cartOrderBookTitle: String,
    cartOrderBookAuthor: String,
    cartOrderBookISBNNumber: String,
    cartOrderBookGenre: String,
    cartOrderBookPublisher: String,
    cartOrderBookSynopsis: String,
    cartOrderBookImageUrl: String,
    cartOrderBookQuantity: Int,
    cartOrderClientProfilePictureUrl: String,
    clientId: String,
    cartOrderClientFullName: String,
    cartOrderClientEmailAddress: String,
    cartOrderClientPhoneNumber: String,
    cartOrderLocationName: String,
    cartOrderLocationPrice: String,
    cartOrderLocationDistanceInKm: String,
    cartOrderDate: String,
    cartOrderStatus: String,
    cartOrderId: String,
    navController: NavHostController
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
        .clip(shape = CutCornerShape(20.dp))
        .border(width = 5.dp, shape = CutCornerShape(20.dp), color = Color.Blue)
        ,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.Green)
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = rememberAsyncImagePainter(cartOrderBookImageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .padding(18.dp)
            )
            Text(
                text = "Book Id:\n $bookId",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Book Title: \n $cartOrderBookTitle",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Book Author: \n $cartOrderBookAuthor",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Book ISBN Number: \n $cartOrderBookISBNNumber",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Book Genre:\n $cartOrderBookGenre",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Book Publisher:\n $cartOrderBookPublisher",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Book Synopsis:\n $cartOrderBookSynopsis",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Quantity Ordered:\n $cartOrderBookQuantity",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Image(
                painter = rememberAsyncImagePainter(cartOrderClientProfilePictureUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .padding(18.dp)
            )
            Text(
                text = "Client Id:\n $clientId",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Client Full Name:\n $cartOrderClientFullName",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Client Email Address:\n $cartOrderClientEmailAddress",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Client Phone Number:\n $cartOrderClientPhoneNumber",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Order Location: \n $cartOrderLocationName",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Order Distance(Km):\n $cartOrderLocationDistanceInKm",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Order Price: \n $cartOrderLocationPrice",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Order Date: \n $cartOrderDate",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Order Status: \n $cartOrderStatus",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                textAlign = TextAlign.Center
            )
            when (cartOrderStatus) {
                "Ordered" -> {
                    Text(
                        text = "Changed your mind? \uD83D\uDC47",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
                            val deliveryViewModel = DeliveryViewModel(navController, context)
                            deliveryViewModel.removeFromCart(
                                cartOrderId,
                                bookId,
                                clientId
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(
                            text = "Remove from cart"
                        )
                    }
                }
                "Depot Delivery" -> {
                    Text(
                        text = "Delivery in progress to pickup point. Please wait for arrival \uD83D\uDE09",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                "Depot Delivered" -> {
                    Text(
                        text = "The book was delivered successfully to pickup point. Please go for it.",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                "Depot Returned" -> {
                    Text(
                        text = "The order was returned successfully to pickup point. Hopefully you enjoyed reading $cartOrderBookTitle.",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                "Library Delivery"-> {
                    Text(
                        text = "The order is in delivery to the library.",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                "Library Returned"-> {
                    Text(
                        text = "The book was delivered to the library. Thank you for your cooperation $cartOrderClientFullName \uD83E\uDD17.",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Magenta,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                else -> {
                    Text(
                        text = "Invalid Status? We are taking care of it \uD83D\uDE0A.",
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