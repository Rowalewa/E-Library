package com.example.e_library.ui.theme.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.e_library.R
import com.example.e_library.ui.theme.screens.attendant.AttendantAppTopBar
import com.example.e_library.ui.theme.screens.attendant.AttendantBottomAppBar

@Composable
fun AboutScreenAttendant(navController: NavController, attendantId: String){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.admin_client_edit_screen),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column{
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                AttendantAppTopBar(navController, attendantId)
            }
            AboutScreen()
        }
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            AttendantBottomAppBar(navController, attendantId)
        }
    }
}