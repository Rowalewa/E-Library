@file:Suppress("DEPRECATION")

package com.example.e_library.ui.theme.screens.admin

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_library.R
import com.example.e_library.data.AuthViewModel
import com.example.e_library.ui.theme.ELibraryTheme
import com.example.e_library.ui.theme.screens.dashboard.DashTopBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRegisterScreen(navController: NavController){
    var fullName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val genderOptions = listOf("Male", "Female", "Prefer Not to Say")
    var isGenderExpanded by remember {
        mutableStateOf(false)
    }
    var gender by remember {
        mutableStateOf(genderOptions[0])
    }
    val maritalStatusOptions = listOf("Single", "Married", "Divorced", "Widowed")
    var isMaritalStatusExpanded by remember {
        mutableStateOf(false)
    }
    var maritalStatus by remember {
        mutableStateOf(maritalStatusOptions[0])
    }
    var phoneNumber by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var dateOfBirth by remember { mutableStateOf(TextFieldValue("")) }
    var isDateOfBirthExpanded by remember { mutableStateOf(false) }
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var pass by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var confpass by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.admin_register_screen),
            contentDescription = "View Clients Image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ){
                DashTopBar(navController)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState(), enabled = true, reverseScrolling = false)
            ) {
                Text(
                    text = "REGISTER ",
                    fontFamily = FontFamily.Serif,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(color = Color.Red, shape = CutCornerShape(10.dp))
                        .width(200.dp)
                )
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = {
                        Text(
                            text = "Enter Your Full Name"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                        .border(width = Dp.Hairline, color = Color.White)
                ) {
                    Text(
                        text = "Gender:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = Color.White
                    )
                    ExposedDropdownMenuBox(
                        expanded = isGenderExpanded,
                        onExpandedChange = { isGenderExpanded = !isGenderExpanded }
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
                            value = gender,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderExpanded) },
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
                            expanded = isGenderExpanded,
                            onDismissRequest = { isGenderExpanded = false }) {
                            genderOptions.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = { Text(text = text) },
                                    onClick = { gender = genderOptions[index] },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                }
                Text(text = "Currently Selected: $gender")
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                        .border(width = Dp.Hairline, color = Color.White)
                ) {
                    Text(
                        text = "Marriage Status:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = Color.White
                    )
                    ExposedDropdownMenuBox(
                        expanded = isMaritalStatusExpanded,
                        onExpandedChange = { isMaritalStatusExpanded = !isMaritalStatusExpanded }
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
                            value = maritalStatus,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMaritalStatusExpanded) },
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
                            expanded = isMaritalStatusExpanded,
                            onDismissRequest = { isMaritalStatusExpanded = false }) {
                            maritalStatusOptions.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = { Text(text = text) },
                                    onClick = { maritalStatus = maritalStatusOptions[index] },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                }
                Text(text = "Currently Selected: $maritalStatus")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = {
                        Text(
                            text = "Enter Your Phone Number"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = { dateOfBirth = it },
                    label = { Text("Date of Birth") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { isDateOfBirthExpanded = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                        }
                    }
                )

                if (isDateOfBirthExpanded) {
                    val today = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val selectedDate = Calendar.getInstance()
                            selectedDate.set(year, month, day)
                            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            dateOfBirth = TextFieldValue(sdf.format(selectedDate.time))
                            isDateOfBirthExpanded = false
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = "Enter Email Address"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = pass,
                    onValueChange = { pass = it },
                    label = {
                        Text(
                            text = "Enter Password"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = confpass,
                    onValueChange = { confpass = it },
                    label = {
                        Text(
                            text = "Confirm Password"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                ImagePicker(
                    context,
                    navController,
                    fullName.text.trim(),
                    gender.trim(),
                    maritalStatus.trim(),
                    phoneNumber.text.trim(),
                    dateOfBirth.text.trim(),
                    email.text.trim(),
                    pass.text.trim(),
                    confpass.text.trim(),

                    )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}
@Composable
fun ImagePicker(
    context: Context,
    navController: NavController,
    fullName: String,
    gender: String,
    maritalStatus: String,
    phoneNumber: String,
    dateOfBirth: String,
    email: String,
    pass: String,
    confpass: String
) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    Column(modifier = Modifier) {
        if (hasImage && imageUri != null) {
            val bitmap = MediaStore.Images.Media.
            getBitmap(context.contentResolver,imageUri)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Selected image",
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 0.dp,
                    bottom = 0.dp
                ))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    imagePicker.launch("image/*")
                }
            ) {
                Text(
                    text = "Select Profile Picture Image"
                )
            }
            Button(onClick = {
                //-----------WRITE THE UPLOAD LOGIC HERE---------------//
                val productRepository = AuthViewModel(navController,context)
                productRepository.adminSignup(
                    fullName,
                    gender,
                    maritalStatus,
                    phoneNumber,
                    dateOfBirth,
                    email,
                    pass,
                    confpass,
                    imageUri
                )

            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    ),
                colors = ButtonDefaults.buttonColors(Color.Cyan)
            ) {
                Text(
                    text = "Register",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Client Register Screen Preview"
)
@Composable
fun ClientRegisterScreenPreview(){
    ELibraryTheme {
        AdminRegisterScreen(navController = rememberNavController())
    }
}
