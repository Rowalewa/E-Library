package com.example.e_library.ui.theme.screens.staff

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_library.R
import com.example.e_library.data.AuthViewModel
import com.example.e_library.navigation.ROUTE_STAFF_REGISTER
import com.example.e_library.ui.theme.ELibraryTheme
import com.example.e_library.ui.theme.screens.dashboard.DashTopBar

@Composable
fun StaffLogInScreen(navController: NavController){
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var pass by remember { mutableStateOf(TextFieldValue("")) }
    val context= LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.staff_login),
            contentDescription = "View Clients Image",
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
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "LOG IN",
                    fontSize = 30.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .background(color = Color.Black, shape = CutCornerShape(10.dp))
                        .width(150.dp)
                        .padding(
                            start = 0.dp,
                            end = 0.dp,
                            top = 20.dp,
                            bottom = 20.dp
                        ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Enter Email") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Red,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                    ,

                    )
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = pass, onValueChange = { pass = it },
                    label = { Text(text = "Enter Password") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Blue,
                        unfocusedTextColor = Color.Red,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val myLogin = AuthViewModel(navController, context)
                        myLogin.stafflogin(
                            email.text.trim(),
                            pass.text.trim()
                        )

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                ) {
                    Text(
                        text = "Log In",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color.Magenta
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Don't have account? Click to Register",
                    color = Color.White
                )
                Button(
                    onClick = {
                        navController.navigate(ROUTE_STAFF_REGISTER)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(
                        text = "Register",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color.Magenta
                    )
                }

            }
        }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Staff LogIn Screen Preview"
)
@Composable
fun StaffLogInScreenPreview(){
    ELibraryTheme {
        StaffLogInScreen(navController = rememberNavController())
    }
}