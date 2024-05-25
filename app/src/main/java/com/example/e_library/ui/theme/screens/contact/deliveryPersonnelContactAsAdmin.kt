package com.example.e_library.ui.theme.screens.contact

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
import androidx.navigation.NavHostController
import com.example.e_library.R
import com.example.e_library.ui.theme.screens.admin.AdminAppTopBar
import com.example.e_library.ui.theme.screens.admin.AdminBottomAppBar

@Composable
fun ContactDeliveryPersonnelAsAdmin(navController: NavHostController, adminId: String){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.view_staff_contact),
            contentDescription = "View Delivery Personnel Image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                AdminAppTopBar(navController, adminId)
            }
            DeliveryPersonnelContact(navController)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AdminBottomAppBar(navController, adminId)
        }
    }
}