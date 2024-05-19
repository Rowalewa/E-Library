package com.example.e_library.ui.theme.screens.clients

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_library.R
import com.example.e_library.navigation.ROUTE_CLIENT_LOGIN
import com.example.e_library.navigation.ROUTE_CLIENT_REGISTER
import com.example.e_library.navigation.ROUTE_HOME
import com.example.e_library.ui.theme.ELibraryTheme
import com.example.e_library.ui.theme.screens.dashboard.DashTopBar

@Composable
fun ClientHomeScreen(navController: NavController){
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.penta_color),
            contentDescription = null,
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.client_home_image_alpha),
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp)
                            .padding(
                                start = 0.dp,
                                end = 0.dp,
                                top = 20.dp,
                                bottom = 20.dp
                            )
                    )
                }
                Button(
                    onClick = { navController.navigate(ROUTE_CLIENT_LOGIN) },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                ) {
                    Text(
                        text = "Log In",
                        color = Color.Green,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif

                    )
                }
                Button(
                    onClick = { navController.navigate(ROUTE_CLIENT_REGISTER) },
                    colors = ButtonDefaults.buttonColors(Color.Green),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                ) {
                    Text(
                        text = "Sign Up",
                        color = Color.Blue,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }
                Button(
                    onClick = { navController.navigate(ROUTE_HOME) },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                ) {
                    Text(
                        text = "Back to Home Screen",
                        color = Color.Green,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Client Home Screen Preview"
)
@Composable
fun ClientHomeScreenPreview(){
    ELibraryTheme {
        ClientHomeScreen(navController = rememberNavController())
    }
}