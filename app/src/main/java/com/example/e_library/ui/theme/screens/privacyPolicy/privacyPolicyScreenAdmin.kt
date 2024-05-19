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
            Column (
                modifier = Modifier
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true,
                        reverseScrolling = false
                    )
                    .background(color = Color.White)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Privacy Policy",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Information We Collect:\n",
                    fontSize = 25.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(20.dp)
                        .background(color = Color.Black, shape = RectangleShape)
                )
                Text(
                    text = "We collect personal information, such as name and email address, when you register for an account.\n" +
                        " We also collect usage data, such as pages visited and interactions with the app, to improve our services.\n" +
                        "\n",
                    modifier = Modifier.background(color = Color.Green, shape = RoundedCornerShape(10.dp)),
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "How We Use Your Information: \n",
                    fontSize = 25.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(20.dp)
                        .background(color = Color.Black, shape = RectangleShape)
                )
                Text(
                    text = "We use your personal information to provide the services you request and to personalize your experience. \n" +
                            "We may also use your information for marketing purposes with your consent.\n",
                    modifier = Modifier.background(color = Color.Green, shape = RoundedCornerShape(10.dp)),
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Information Sharing: \n",
                    fontSize = 25.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(20.dp)
                        .background(color = Color.Black, shape = RectangleShape)
                )
                Text(
                    text = "We do not sell or share your personal information with third parties for their marketing purposes.\n" +
                            " We may share your information with service providers who help us operate the app.\n",
                    modifier = Modifier.background(color = Color.Green, shape = RoundedCornerShape(10.dp)),
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Data Security: \n",
                    fontSize = 25.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(20.dp)
                        .background(color = Color.Black, shape = RectangleShape)
                )
                Text(
                    text = "We take reasonable measures to protect your information from unauthorized access, disclosure, or alteration. \n " +
                            "However, no method of transmission over the internet or electronic storage is 100% secure.\n",
                    modifier = Modifier.background(color = Color.Green, shape = RoundedCornerShape(10.dp)),
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Your Choices: \n",
                    fontSize = 25.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(20.dp)
                        .background(color = Color.Black, shape = RectangleShape)

                )
                Text(
                    text = "You can access and update your personal information through your account settings. \n" +
                            "You can also opt-out of marketing communications at any time.\n",
                    modifier = Modifier.background(color = Color.Green, shape = RoundedCornerShape(10.dp)),
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Changes to This Policy: \n",
                    fontSize = 25.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(20.dp)
                        .background(color = Color.Black, shape = RectangleShape)
                )
                Text(
                    text = "We may update this Privacy Policy from time to time. \n " +
                        "We will notify you of any changes by posting the new Privacy Policy on this page.\n",
                    modifier = Modifier.background(color = Color.Green, shape = RoundedCornerShape(10.dp)),
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(150.dp))
            }
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