package com.example.e_library.ui.theme.screens.endUserLicenceAgreement

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
fun AttendantEULA(navController: NavController, attendantId: String){
    Box{
        Column(
            modifier = Modifier.background(color = Color.White)
        ) {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ){
                AttendantAppTopBar(navController, attendantId)
            }
            EULA()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AttendantBottomAppBar(navController, attendantId)
        }
    }
}