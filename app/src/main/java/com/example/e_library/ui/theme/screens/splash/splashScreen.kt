package com.example.e_library.ui.theme.screens.splash

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_library.R
import com.example.e_library.data.AuthViewModel
import com.example.e_library.navigation.ROUTE_ADMIN_EDIT_HOME
import com.example.e_library.navigation.ROUTE_ATTENDANT_HOME
import com.example.e_library.navigation.ROUTE_BOOKS_HOME
import com.example.e_library.navigation.ROUTE_BORROW_HOME
import com.example.e_library.navigation.ROUTE_DASHBOARD
import com.example.e_library.navigation.ROUTE_DELIVERY_PERSONNEL_HOME
import com.example.e_library.navigation.ROUTE_HOME
import com.example.e_library.navigation.ROUTE_SPLASH
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    var progress by remember { mutableFloatStateOf(0f) }
    var count by remember {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current
    val authViewModel = AuthViewModel(navController, context)

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000),
        label = "Animation"
    )
    var startDestination by remember {
        mutableStateOf(ROUTE_SPLASH)
    }

    LaunchedEffect(key1 = true) {
        while (progress < 1f && count < 100) {
            delay(100)
            progress += 0.01f
            count += 1
            startAnimation = true
        }
        delay(3000) // Display splash screen for 3 seconds
        if (authViewModel.checkLoginState(context)) {
            val userType = authViewModel.getUserType(context)
            val userId = authViewModel.getUserId(context)
            if (authViewModel.isInternetAvailable(context)) {
                startDestination = when (userType) {
                    "Client" -> "$ROUTE_BORROW_HOME/$userId"
                    "Admin" -> "$ROUTE_ADMIN_EDIT_HOME/$userId"
                    "Staff" -> "$ROUTE_BOOKS_HOME/$userId"
                    "DeliveryPersonnel" -> "$ROUTE_DELIVERY_PERSONNEL_HOME/$userId"
                    "Attendant" -> "$ROUTE_ATTENDANT_HOME/$userId"
                    else -> ROUTE_DASHBOARD
                }
                navController.navigate(startDestination)
                Log.d("E-Library", "Navigating to $startDestination")
                Toast.makeText(context, "Welcome back $userType \uD83E\uDD17", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "No internet connection. Please check your connection.", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Welcome", Toast.LENGTH_LONG).show()
            navController.navigate(ROUTE_DASHBOARD)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_background_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your logo resource
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .alpha(alphaAnim.value)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "E-Library",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.alpha(alphaAnim.value)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = Color.Blue,
                trackColor = Color.Yellow,
            )
            Spacer(modifier = Modifier.height(35.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .clip(RoundedCornerShape(3.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(10.dp),
                color = Color.White,
                trackColor = Color.Red
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "LOADING $count %",
                fontFamily = FontFamily.Cursive,
                color = Color.Green,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}
