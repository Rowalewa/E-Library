package com.example.e_library.ui.theme.screens.userManual

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.e_library.ui.theme.screens.attendant.AttendantAppTopBar
import com.example.e_library.ui.theme.screens.attendant.AttendantBottomAppBar

@Composable
fun AttendantUserManual(navController: NavController, attendantId: String) {
    Box {
        Column(
            modifier = Modifier.background(color = Color.Black)
        ) {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                AttendantAppTopBar(navController, attendantId)
            }
            UserManual()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AttendantBottomAppBar(navController, attendantId)
        }
    }
}