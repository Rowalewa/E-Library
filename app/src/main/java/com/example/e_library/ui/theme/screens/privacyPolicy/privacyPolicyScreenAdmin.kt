package com.example.e_library.ui.theme.screens.privacyPolicy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_library.ui.theme.ELibraryTheme
import com.example.e_library.ui.theme.screens.admin.AdminAppTopBar
import com.example.e_library.ui.theme.screens.admin.AdminBottomAppBar

@Composable
fun PrivacyPolicyScreenAdmin(navController: NavController, adminId: String){
    Box{
        Column (
            modifier = Modifier.background(color = Color.Black)
        ){
            Box (
                modifier = Modifier.fillMaxWidth()
            ){
                AdminAppTopBar(navController, adminId)
            }
            PrivacyPolicy()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AdminBottomAppBar(navController, adminId)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PrivacyPolicyScreenAdminPreview(){
    ELibraryTheme {
        PrivacyPolicyScreenAdmin(navController = rememberNavController(), adminId = "")
    }
}