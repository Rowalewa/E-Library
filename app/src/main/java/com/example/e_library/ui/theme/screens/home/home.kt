package com.example.e_library.ui.theme.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_library.R
import com.example.e_library.navigation.ROUTE_ADMIN_LOGIN
import com.example.e_library.navigation.ROUTE_CLIENT_HOME
import com.example.e_library.navigation.ROUTE_STAFF_HOME
import com.example.e_library.navigation.ROUTE_VIEW_BOOKS_GUEST
import com.example.e_library.ui.theme.ELibraryTheme
import com.example.e_library.ui.theme.screens.dashboard.DashTopBar

@Composable
fun HomeScreen(navController: NavController){
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)

    ){
        Image(
            painter = painterResource(id = R.drawable.gothic_image),
            contentDescription = "home background",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                DashTopBar(navController)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(
                    state = rememberScrollState(),
                    enabled = true,
                    reverseScrolling = false
                )

            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "E-Library",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif,
                        color = Color.Blue,
                        modifier = Modifier
                            .background(color = Color.Green, shape = CutCornerShape(10.dp))
                            .width(250.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(
                        modifier = Modifier
                            .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                            .padding(5.dp)
                    ) {
                        IconButton(
                            onClick = { navController.navigate(ROUTE_ADMIN_LOGIN) },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Red)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Face,
                                contentDescription = "Admin",
                                tint = Color.Blue
                            )
                        }
                        Text(
                            text = "Admin",
                            modifier = Modifier.background(color = Color.Blue),
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedCard(
//            onClick = { navController.navigate(ROUTE_STAFF_HOME)},
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red,
                        contentColor = Color.Blue
                    ),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                ) {
//            Box {
//                Image(
//                    painter = painterResource(id = R.drawable.library_image_alpha),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.staff_icon),
                                contentDescription = "Staff Icon",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 0.dp,
                                        top = 10.dp,
                                        bottom = 0.dp,
                                        end = 0.dp
                                    )
                                    .size(120.dp)
                            )
                        }
                        Button(
                            onClick = { navController.navigate(ROUTE_STAFF_HOME) },
                            colors = ButtonDefaults.buttonColors(Color.Cyan),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 10.dp,
                                    bottom = 10.dp
                                )
                        ) {
                            Text(
                                text = "Staff",
                                color = Color.Black
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedCard(
//            onClick = { navController.navigate(ROUTE_STAFF_HOME)},
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Blue,
                        contentColor = Color.Blue
                    ),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.client_icon),
                            contentDescription = "Staff Icon",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 0.dp,
                                    top = 10.dp,
                                    bottom = 0.dp,
                                    end = 0.dp
                                )
                                .size(120.dp)
                        )

                    }
                    Button(
                        onClick = { navController.navigate(ROUTE_CLIENT_HOME) },
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 10.dp,
                                bottom = 10.dp
                            )
                    ) {
                        Text(
                            text = "Client",
                            color = Color.Cyan
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.Green),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.view_book_btn),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .size(120.dp)
                    )
                    Button(
                        onClick = { navController.navigate(ROUTE_VIEW_BOOKS_GUEST) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 10.dp,
                                start = 5.dp,
                                end = 5.dp,
                                bottom = 0.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue
                        )
                    ) {
                        Text(
                            text = "View Books",
                            color = Color.Red
                        )
                    }
                    Text(
                        text = "With this option you can only view books available",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Home Screen Preview"
)
@Composable
fun HomeScreenPreview(){
    ELibraryTheme {
        HomeScreen(navController = rememberNavController())
    }
}