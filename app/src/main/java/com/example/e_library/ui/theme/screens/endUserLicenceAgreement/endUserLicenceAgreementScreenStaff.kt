package com.example.e_library.ui.theme.screens.endUserLicenceAgreement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_library.ui.theme.screens.books.StaffAppTopBar
import com.example.e_library.ui.theme.screens.books.StaffBottomAppBar

@Composable
fun EndUserLicenceAgreementScreenStaff(navController: NavController, staffId: String){
    Box{
        Column (
            modifier = Modifier.background(color = Color.White)
        ){
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                StaffAppTopBar(navController, staffId)
            }
            Column (
                modifier = Modifier.verticalScroll(state = rememberScrollState(), enabled = true, reverseScrolling = false)
                    .padding(10.dp)
                    .background(color = Color.Gray)
            ){
                Text(
                    text = "End User Licence Agreement",
                    fontSize = 30.sp,
                    color = Color.Blue,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "License Grant: \n" +
                            "\n" +
                            "We grant you a limited, non-exclusive, non-transferable license to use the app for personal, non-commercial purposes." +
                            "\n"
                )
                Text(
                    text = "Restrictions: \n" +
                            "\n" +
                            "You may not reverse engineer, modify, or distribute the app. You may not use the app for any illegal or unauthorized purpose." +
                            "\n"
                )
                Text(
                    text = "Ownership: \n" +
                            "\n" +
                            "The app is owned by us and is protected by copyright and other intellectual property laws." +
                            "\n"
                )
                Text(
                    text = "Termination: \n" +
                            "\n" +
                            "We may terminate your license to use the app if you violate the terms of this agreement.\n" +
                            "\n"
                )
                Text(
                    text = "Warranty Disclaimer: \n" +
                            "\n" +
                            "The app is provided \"as is\" without any warranties, express or implied. We do not warrant that the app will be error-free or that any defects will be corrected." +
                            "\n"
                )
                Text(
                    text = "Limitation of Liability: \n" +
                            "\n" +
                            "We shall not be liable for any indirect, incidental, special, or consequential damages arising out of or in connection with your use of the app.\n" +
                            "\n"
                )
                Text(
                    text = "Governing Law: \n" +
                            "\n" +
                            "This agreement shall be governed by and construed in accordance with the laws of Kenya.\n" +
                            "\n"
                )
                Text(
                    text = "Contact Us: \n" +
                            "\n" +
                            "If you have any questions about this EULA, please contact us at [lesliewanjala06@gmail.com]." +
                            "\n"
                )
                Spacer(modifier = Modifier.height(150.dp))
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