package com.example.e_library.ui.theme.screens.feedback

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.e_library.R
import com.example.e_library.data.FeedbackViewModel
import com.example.e_library.ui.theme.screens.borrowing.ClientAppTopBar
import com.example.e_library.ui.theme.screens.borrowing.ClientBottomAppBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun FeedbackScreenClient(navController: NavHostController, clientId: String) {
    val context = LocalContext.current
    var feedbackName by remember {
        mutableStateOf(TextFieldValue())
    }
    var feedbackEmailAddress by remember {
        mutableStateOf(TextFieldValue())
    }
    var feedbackText by remember {
        mutableStateOf(TextFieldValue())
    }
    val clientFullNameRef = FirebaseDatabase.getInstance().getReference("Client").child(clientId).child("fullName")
    clientFullNameRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val clientFullName = snapshot.getValue(String::class.java)
            if (clientFullName != null) {
                feedbackName = TextFieldValue(clientFullName)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle database error
            Toast.makeText(context, "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    })
    Box{
        Image(
            painter = painterResource(id = R.drawable.feedback_screen_client),
            contentDescription = "View Client Wallpaper",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                ClientAppTopBar(navController, clientId)
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "We'd love to hear your feedback!" +
                            "\n" +
                            "\"* Required Question\"",
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(color = Color.Yellow, shape = RoundedCornerShape(10.dp)),
                    color = Color.Black,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = feedbackName,
                    onValueChange = { feedbackName = it },
                    label = { Text("Name *") },
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
                    value = feedbackEmailAddress,
                    onValueChange = { feedbackEmailAddress = it },
                    label = { Text("Enter your email here (Optional)") },
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
                    value = feedbackText,
                    onValueChange = { feedbackText = it },
                    label = { Text("Enter your feedback here *") },
                    modifier = Modifier.fillMaxWidth()
                        .height(100.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Blue,
                        unfocusedContainerColor = Color.Black,
                        focusedTextColor = Color.Red,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val feedbackRepository = FeedbackViewModel(navController, context)
                        feedbackRepository.saveFeedbackToFirebaseClient(
                            feedbackName.text.trim(),
                            feedbackEmailAddress.text.trim(),
                            feedbackText.text.trim()
                        )
//                feedbackText = TextFieldValue()
//                feedbackEmailAddress = TextFieldValue()
//                feedbackName = TextFieldValue()
                    },
                    modifier = Modifier.align(Alignment.End)
                        .width(350.dp)
                        .padding(10.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Submit")
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ClientBottomAppBar(navController, clientId)
        }
    }

}
