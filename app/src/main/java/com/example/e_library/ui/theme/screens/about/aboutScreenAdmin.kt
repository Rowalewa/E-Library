package com.example.e_library.ui.theme.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.e_library.ui.theme.ELibraryTheme
import com.example.e_library.ui.theme.screens.admin.AdminAppTopBar
import com.example.e_library.ui.theme.screens.admin.AdminBottomAppBar

@Composable
fun AboutScreenAdmin(navController: NavController, adminId: String) {
//    val scrollState = rememberScrollState()
//    val coroutineScope = rememberCoroutineScope()
//
//    DisposableEffect(Unit) {
//        coroutineScope.launch {
//            scrollState.animateScrollToItem(
//                scrollState.firstVisibleItemIndex + 1
//            )
//            delay(2000) // Adjust the delay based on your desired scroll speed
//        }
//        onDispose { }
//    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.admin_client_edit_screen),
            contentDescription = "View Clients Image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column{
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                AdminAppTopBar(navController, adminId)
            }
            AboutScreen()
        }
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
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
fun AboutScreenAdminPreview(){
    ELibraryTheme {
        AboutScreenAdmin(navController = rememberNavController(), adminId = "")
    }
}
