package com.example.e_library.ui.theme.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.e_library.navigation.ROUTE_ABOUT_SCREEN_GUEST
import com.example.e_library.navigation.ROUTE_CONTACT_US
import com.example.e_library.navigation.ROUTE_DASHBOARD
import com.example.e_library.navigation.ROUTE_EULA_GUEST
import com.example.e_library.navigation.ROUTE_HOME
import com.example.e_library.navigation.ROUTE_PRIVACY_POLICY_GUEST
import com.example.e_library.navigation.ROUTE_USER_MANUAL_GUEST
import com.example.e_library.ui.theme.ELibraryTheme
import com.example.e_library.ui.theme.Pink40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController){
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.dashboard_screen),
            contentDescription = "Dashboard",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                DashTopBar(navController)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Welcome",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = Color.Red,
                modifier = Modifier
                    .background(color = Color.Cyan, shape = CutCornerShape(10.dp))
                    .padding(5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center

            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.dashboard_image),
                contentDescription = null,
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Crop
            )
            Button(
                onClick = { navController.navigate(ROUTE_HOME) },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Pink40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row {
                    Text(
                        text = "Get Started",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = Color.Yellow
                    )
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(
                    bottom = 10.dp,
                    start = 20.dp,
                    end = 20.dp
                )
            ) {
                Card(
                    onClick = { navController.navigate(ROUTE_ABOUT_SCREEN_GUEST) },
                    colors = CardDefaults.cardColors(containerColor = Color.Magenta)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.about_icon_guest),
                        contentDescription = "About us icon",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp)
                    )
                    Text(
                        text = "About Us",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(
                                start = 0.dp,
                                end = 0.dp,
                                top = 0.dp,
                                bottom = 5.dp
                            )
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Card(
                    onClick = { navController.navigate(ROUTE_CONTACT_US) },
                    colors = CardDefaults.cardColors(containerColor = Color.Blue)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.contact_us_guest),
                        contentDescription = "Contact Us",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp)
                    )
                    Text(
                        text = "Contact Us",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(
                                start = 0.dp,
                                end = 0.dp,
                                top = 0.dp,
                                bottom = 5.dp
                            )
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Card(
                    onClick = { navController.navigate(ROUTE_USER_MANUAL_GUEST) },
                    colors = CardDefaults.cardColors(containerColor = Color.Green)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_manual_guest),
                        contentDescription = "User Manual",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp)
                    )
                    Text(
                        text = "User Manual",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(
                                start = 0.dp,
                                end = 0.dp,
                                top = 0.dp,
                                bottom = 5.dp
                            )
                    )

                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                )
            ) {
                Card(
                    onClick = { navController.navigate(ROUTE_PRIVACY_POLICY_GUEST) },
                    colors = CardDefaults.cardColors(containerColor = Color.Yellow)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.privacy_policy_guest),
                        contentDescription = "Privacy policy_guest",
                        modifier = Modifier
                            .size(160.dp)
                            .padding(10.dp)
                    )
                    Text(
                        text = "Privacy Policy",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(
                                start = 0.dp,
                                end = 0.dp,
                                top = 0.dp,
                                bottom = 5.dp
                            )
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Card(
                    onClick = { navController.navigate(ROUTE_EULA_GUEST) },
                    colors = CardDefaults.cardColors(containerColor = Color.Red)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.eula_image_guest),
                        contentDescription = "Eula",
                        modifier = Modifier
                            .size(135.dp)
                            .padding(10.dp)
                    )
                    Text(
                        text = "End User Licence Agreement",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(
                                start = 0.dp,
                                end = 0.dp,
                                top = 0.dp,
                                bottom = 5.dp
                            ),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashTopBar(navController: NavController) {
    val context = LocalContext.current
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
                navController.navigate(ROUTE_DASHBOARD)
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
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Green
                )
            }
            IconButton(
                onClick = { navController.navigate(ROUTE_HOME) }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "account",
                    tint = Color.White
                )
            }
        }
    )
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DashboardScreenPreview(){
    ELibraryTheme {
        DashboardScreen(navController = rememberNavController())
    }
}