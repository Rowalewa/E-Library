package com.example.e_library.ui.theme.screens.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.e_library.R
import com.example.e_library.data.ContactViewModel
import com.example.e_library.ui.theme.screens.dashboard.DashTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(navController: NavHostController) {
    val context = LocalContext.current
    var guestName by remember {
        mutableStateOf(TextFieldValue())
    }
    var guestEmail by remember {
        mutableStateOf(TextFieldValue())
    }
    var guestPhoneNumber by remember {
        mutableStateOf(TextFieldValue())
    }
    var guestComment by remember {
        mutableStateOf(TextFieldValue())
    }
    val guestCallbackOptions = listOf("Yes", "No")
    var isGuestCallbackExpanded by remember {
        mutableStateOf(false)
    }
    var guestCallback by remember {
        mutableStateOf(guestCallbackOptions[0])
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.contact_us_screen),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DashTopBar(navController)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "We'd love to hear your feedback! \n * Indicates required field.",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Yellow, shape = RoundedCornerShape(10.dp)),
                color = Color.Black,
                fontFamily = FontFamily.Serif
            )
            Column(
                modifier = Modifier.padding(10.dp)
            ){
                TextField(
                    value = guestName,
                    onValueChange = { guestName = it },
                    label = { Text("Enter your name here *") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Blue,
                        unfocusedContainerColor = Color.Black,
                        focusedTextColor = Color.Red,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = guestEmail,
                    onValueChange = { guestEmail = it },
                    label = { Text("Enter your email here *") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Blue,
                        unfocusedContainerColor = Color.Black,
                        focusedTextColor = Color.Red,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = guestPhoneNumber,
                    onValueChange = { guestPhoneNumber = it },
                    label = { Text("Enter your phone number here") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Blue,
                        unfocusedContainerColor = Color.Black,
                        focusedTextColor = Color.Red,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = guestComment,
                    onValueChange = { guestComment = it },
                    label = { Text("Enter your comment here *") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Blue,
                        unfocusedContainerColor = Color.Black,
                        focusedTextColor = Color.Red,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .border(width = Dp.Hairline, color = Color.White)
                        .background(color = Color.Magenta)
                ) {
                    Text(
                        text = "Callback?:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = Color.White
                    )
                    ExposedDropdownMenuBox(
                        expanded = isGuestCallbackExpanded,
                        onExpandedChange = { isGuestCallbackExpanded = !isGuestCallbackExpanded }
                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                ),
                            value = guestCallback,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGuestCallbackExpanded) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Magenta,
                                unfocusedTextColor = Color.Red,
                                focusedContainerColor = Color.Cyan,
                                unfocusedContainerColor = Color.Green,
                                disabledContainerColor = Color.White,
                                focusedLabelColor = Color.Green,
                                unfocusedLabelColor = Color.Magenta
                            ),
                        )
                        ExposedDropdownMenu(
                            expanded = isGuestCallbackExpanded,
                            onDismissRequest = { isGuestCallbackExpanded = false }) {
                            guestCallbackOptions.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = { Text(text = text) },
                                    onClick = { guestCallback = guestCallbackOptions[index] },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                }
                Text(text = "Currently Selected: $guestCallback",
                    modifier = Modifier.background(color = Color.DarkGray))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val contactRepository = ContactViewModel(navController, context)
                    contactRepository.saveContactUs(
                        guestName.text.trim(),
                        guestEmail.text.trim(),
                        guestPhoneNumber.text.trim(),
                        guestComment.text.trim(),
                        guestCallback.trim()
                    )
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        10.dp
                    )
                    .width(250.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(
                    text = "Submit",
                    color = Color.Magenta,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}
