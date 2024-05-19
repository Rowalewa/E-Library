package com.example.e_library.ui.theme.screens.userManual

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_library.ui.theme.screens.admin.AdminAppTopBar
import com.example.e_library.ui.theme.screens.admin.AdminBottomAppBar

@Composable
fun UserManualScreenAdmin(navController: NavController, adminId: String) {
    Box {
        Column(
            modifier = Modifier.background(color = Color.Black)
        ) {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                AdminAppTopBar(navController, adminId)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "User Manual",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "1. Add Books: \n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = CutCornerShape(10.dp)),
                ) {
                    Text(
                        text = "This is an action that can only be performed by a staff member after logging in. \n" +
                                "Use the Add book button to save books to firebase as Staff. \n" +
                                "Ensure you fill all the fields \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "2. Search Books: \n ",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Use the search bar to find specific books by title, author, or category. \n " +
                                "This can be as guest or logged in as client or staff",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "3. Borrow Books:\n ",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "This is an action performed by the staff" +
                                "Choose client then you will see all available books which you can borrow after searching. \n" +
                                " The book to be borrowed should be in stock and the client must not have borrowed more than 3 books. \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "4. Return Books: \n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Choose client then you will see all available books\n" +
                                " Search for the book to be returned" +
                                "\n If the client will not have borrowed the book then there will be a message saying the client did not borrow the book" +
                                "\n Furthermore, if the client has returned the book overdue, the client will be fined and will not be able to borrow books later on unless paid.",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "5. View Books: \n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Use the View Books button to view available books as a guest, staff or client.",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "6. View Clients: \n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Use the View Clients button to view all the clients as a staff member.",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "7. View Client Information:\n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "This is a client's action \n" +
                                "Click on Account icon on client top app bar to open a dropdown menu.\n " +
                                "Choose 'My Account' to view your profile. \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "8. Edit Client Information:\n ",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Here a client can edit some details \n" +
                                "Click Edit button on View Client Profile Screen. \n" +
                                " Here you can change password or profile picture. \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "9. View Staff Information:\n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(10.dp)),
                ) {
                    Text(
                        text = "This is a staff's action \n" +
                                "Click on Account icon on staff top app bar to open a dropdown menu.\n " +
                                "Choose 'My Account' to view your profile. \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "10. Edit Staff Information:\n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Here a staff can edit some details \n" +
                                "Click Edit button on View Staff Profile Screen. \n" +
                                " Here you can change password or profile picture. \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "11. View Borrowed Books:\n ",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Click on view borrowed books button on Client home screen as a logged in  client. \n" +
                                " Here you can see all the books that you have borrowed, the return date, borrow date and the book details such as title. \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "12. Submit Feedback:\n ",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Click on mail outline icon button on bottom app bar for clients, admin and staff. \n" +
                                " This is for submitting your feedback with email optional but should you decide to fill it it should be similar to yours.\n" +
                                " We value your feedback \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "13. Staff Contacts:\n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "Click on call icon button on bottom app bar as Admin, staff or client. \n" +
                                " A screen with the staff contact details is populated here.\n" +
                                " This is in case you need to contact any member of the staff",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "14. End User Licence Agreement:\n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "For a guest user it is a Red card 'End user Licence Agreement' on Welcome screen\n" +
                                "Click on settings icon button on bottom app bar for client, admin and staff. \n" +
                                " A screen with buttons is populated here.\n" +
                                " Click 'EULA'. A screen with End User Licence Agreement is shown \n" +
                                " This is a legal contract between the software developer and the user of the software that outlines the terms and conditions for using the software. \n" +
                                "EULA primarily governs the use of the software itself. \n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "15. Privacy Policy:\n ",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "For guest users it is a yellow card 'Privacy Policy' on Welcome screen." +
                                "Click on settings icon button on bottom app bar as client, admin or staff. \n" +
                                " A dropdown menu is expanded.\n" +
                                "Click 'Privacy Policy' to open Privacy Policy Screen \n" +
                                " It outlines how this app collects, uses, stores, and protects user data.\n",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "16. User Manual:\n",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "For guest user it is a green card 'User Manual' on Welcome Screen" +
                                "This is the current screen. Guidelines to get here are below. \n" +
                                " Click on settings icon button on bottom app bar as staff, client or staff. \n" +
                                " A dropdown menu is expanded after clicking the icon button. Click 'User Manual'\n" +
                                " Guides you on using the app.",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Text(
                    text = "17. About:\n ",
                    modifier = Modifier.padding(8.dp)
                        .background(color = Color.Red, shape = CutCornerShape(10.dp)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                        .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        text = "For guest user it is a pink card 'About Us' on Welcome Screen" +
                                "For staff, admin and clients, click on info icon button on bottom app bar. \n" +
                                " A screen with the app information is populated here.\n" +
                                " This is all about the app, features, functionalities, version e.t.c.",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Spacer(modifier = Modifier.height(150.dp))
                // Add more instructions as needed
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

