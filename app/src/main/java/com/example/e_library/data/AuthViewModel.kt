@file:Suppress("DEPRECATION")

package com.example.e_library.data

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.example.e_library.models.Admin
import com.example.e_library.models.Clients
import com.example.e_library.models.DeliveryPersonnel
import com.example.e_library.models.Staff
import com.example.e_library.navigation.ROUTE_ADMIN_EDIT_HOME
import com.example.e_library.navigation.ROUTE_ADMIN_LOGIN
import com.example.e_library.navigation.ROUTE_ADMIN_REGISTER
import com.example.e_library.navigation.ROUTE_BOOKS_HOME
import com.example.e_library.navigation.ROUTE_BORROW_HOME
import com.example.e_library.navigation.ROUTE_CLIENT_HOME
import com.example.e_library.navigation.ROUTE_CLIENT_LOGIN
import com.example.e_library.navigation.ROUTE_CLIENT_REGISTER
import com.example.e_library.navigation.ROUTE_DASHBOARD
import com.example.e_library.navigation.ROUTE_DELIVERY_PERSONNEL_HOME
import com.example.e_library.navigation.ROUTE_DELIVERY_PERSONNEL_LOGIN
import com.example.e_library.navigation.ROUTE_DELIVERY_PERSONNEL_REGISTER
import com.example.e_library.navigation.ROUTE_HOME
import com.example.e_library.navigation.ROUTE_STAFF_HOME
import com.example.e_library.navigation.ROUTE_STAFF_LOGIN
import com.example.e_library.navigation.ROUTE_STAFF_REGISTER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

@Suppress("NAME_SHADOWING")
class AuthViewModel (
    var navController: NavController,
    private var context: Context
){
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var progress: ProgressDialog = ProgressDialog(context)
    private var progressUpdate: ProgressDialog = ProgressDialog(context)
    private var progressDelete: ProgressDialog = ProgressDialog(context)

    init {
        progress.setTitle("Loading...\uD83D\uDEE0\uFE0F ")
        progress.setMessage("Please wait for a moment")
        progressUpdate.setTitle("Updating")
        progressUpdate.setMessage("Please Wait...")
        progressDelete.setTitle("Deleting")
        progressDelete.setMessage("Please Wait...")
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun getStartDestination(): String {
        getUserId(context)
        return when (getUserType(context)) {
            "Client" -> "$ROUTE_BORROW_HOME/${getUserId(context)}"
            "Admin" -> "$ROUTE_ADMIN_EDIT_HOME/${getUserId(context)}"
            "Staff" -> "$ROUTE_BOOKS_HOME/${getUserId(context)}"
            "DeliveryPersonnel" -> "$ROUTE_DELIVERY_PERSONNEL_HOME/${getUserId(context)}"
            else -> ROUTE_DASHBOARD
        }
    }

    private fun saveLoginState(context: Context, userId: String, userType: String) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userId", userId)
        editor.putString("userType", userType)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }

    fun checkLoginState(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun getUserId(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }

    fun getUserType(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userType", null)
    }

    private fun clearLoginState(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        navController.navigate(ROUTE_HOME)
    }

    fun adminSignup(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        email: String,
        pass: String,
        confpass: String,
        adminProfilePictureUrl: Uri?,
    ) {
        progress.show()
        if (adminProfilePictureUrl != null) {
            if (fullName.isBlank() || gender.isBlank() || maritalStatus.isBlank() || phoneNumber.isBlank() || dateOfBirth.isBlank() || email.isBlank() || pass.isBlank() || confpass.isBlank()) {
                progress.dismiss()
                Toast.makeText(
                    context,
                    "Fill all the Fields Please \uD83D\uDE42",
                    Toast.LENGTH_LONG
                ).show()
                return
            } else if (pass != confpass) {
                progress.dismiss()
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_STAFF_REGISTER)
                return
            } else if (phoneNumber.length != 10) {
                progress.dismiss()
                Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_LONG).show()
                return
            } else {
                if (isStrongPassword(pass)) {
                    mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val adminId = System.currentTimeMillis().toString()
                                val storageRef = FirebaseStorage.getInstance().reference
                                val profilePicRef =
                                    storageRef.child("admin_profile_pictures/${adminId}")
                                profilePicRef.putFile(adminProfilePictureUrl)
                                    .addOnSuccessListener { _ ->
                                        profilePicRef.downloadUrl.addOnSuccessListener { uri ->
                                            // Once the image is uploaded, save the user data including the image URL
                                            val adminProfilePictureUrl = uri.toString()
                                            val adminData = Admin(
                                                fullName,
                                                gender,
                                                maritalStatus,
                                                phoneNumber,
                                                dateOfBirth,
                                                email,
                                                pass,
                                                adminProfilePictureUrl, // Save the image URL in the user data
                                                adminId

                                            )
                                            val regRef =
                                                FirebaseDatabase.getInstance().getReference()
                                                    .child("Admin").child(adminId)
                                            regRef.setValue(adminData)
                                                .addOnCompleteListener { dataTask ->
                                                    if (dataTask.isSuccessful) {
                                                        progress.dismiss()
                                                        Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_LONG).show()
                                                        navController.navigate("$ROUTE_ADMIN_EDIT_HOME/$adminId")
                                                    } else {
                                                        progress.dismiss()
                                                        Toast.makeText(context, "${dataTask.exception!!.message}", Toast.LENGTH_LONG).show()
                                                        navController.navigate(ROUTE_ADMIN_REGISTER)
                                                    }
                                                }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        progress.dismiss()
                                        Toast.makeText(context, "Failed to upload image: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                            } else {
                                progress.dismiss()
                                val errorMessage =
                                    task.exception?.message ?: "Could not Register, Retry"
                                if (errorMessage.contains("email address is already in use")) {
                                    Toast.makeText(context, "Email address is already registered", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                }
                                navController.navigate(ROUTE_ADMIN_REGISTER)
                            }
                        }
                } else {
                    Toast.makeText(context, "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            progress.dismiss()
            Toast.makeText(context, "You are required to choose an image and fill in all the fields", Toast.LENGTH_LONG).show()
            return
        }
    }

    fun adminLogin(
        email: String,
        pass: String
    ){
        progress.show()
        if (email.isBlank() || pass.isBlank()){
            progress.dismiss()
            Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                getAdminIdByEmail(email) { adminId ->
                    if (adminId != null) {
                        if (it.isSuccessful) {
                            progress.dismiss()
                            saveLoginState(context, adminId, "Admin")
                            Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG).show()
                            navController.navigate("$ROUTE_ADMIN_EDIT_HOME/$adminId")
                        } else {
                            progress.dismiss()
                            Toast.makeText(context, "${it.exception!!.message}", Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_ADMIN_LOGIN)
                        }
                    } else {
                        Toast.makeText(context, "Admin does not exist", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun getAdminIdByEmail(email: String, callback: (String?) -> Unit) {
        val ref = FirebaseDatabase.getInstance().getReference().child("Admin")
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val adminId = snap.key // Assuming the client ID is the key of the client node
                        callback(adminId)
                        return
                    }
                }
                callback(null) // Client ID not found
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Admin", "Error fetching Admin ID: ${error.message}")
                callback(null) // Handle the error by returning null
            }
        })
    }

    fun verifyStaff(staffId: String) {
        val staffRef = FirebaseDatabase.getInstance().getReference("Staff").child(staffId)
        staffRef.child("staffStatus").setValue("Verified")
    }

    fun verifyDeliveryPersonnel(deliveryPersonnelId: String) {
        val deliveryPersonnelRef = FirebaseDatabase.getInstance().getReference("DeliveryPersonnel").child(deliveryPersonnelId)
        deliveryPersonnelRef.child("accountStatus").setValue("Verified")
    }

    fun updateAdmin(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        email: String,
        pass: String,
        confpass: String,
        adminId: String,
        filePath: Uri?
    ) {
        progressUpdate.show()
        val storageReference = FirebaseStorage.getInstance().getReference().child("Admin/$adminId")
        val updateData = Admin(
            fullName,
            gender,
            maritalStatus,
            phoneNumber,
            dateOfBirth,
            email,
            pass,
            "",
            adminId
        )

        // Update book details in Firebase Realtime Database
        if (filePath != null) {
            if (pass.isNotBlank()) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.updatePassword(pass)?.addOnCompleteListener { passwordUpdateTask ->
                    if (passwordUpdateTask.isSuccessful) {
                        if (pass == confpass) {
                            if (isStrongPassword(pass)) {
                                val dbRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Admin/$adminId")
                                dbRef.setValue(updateData).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // If an image file is provided, update the image in Firebase Storage
                                        filePath.let { fileUri ->
                                            storageReference.putFile(fileUri)
                                                .addOnCompleteListener { storageTask ->
                                                    if (storageTask.isSuccessful) {
                                                        progressUpdate.dismiss()
                                                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                                                            val imageUrl = uri.toString()
                                                            updateData.adminProfilePictureUrl =
                                                                imageUrl
                                                            dbRef.setValue(updateData) // Update the book entry with the image URL
                                                        }
                                                    } else {
                                                        progressUpdate.dismiss()
                                                        Toast.makeText(context, "Upload Failure", Toast.LENGTH_LONG).show()
                                                    }
                                                }
                                        }

                                        // Show success message
                                        progressUpdate.dismiss()
                                        Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    } else {
                                        progressUpdate.dismiss()
                                        // Handle database update error
                                        Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                progressUpdate.dismiss()
                                Toast.makeText(context, "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        progressUpdate.dismiss()
                        Toast.makeText(context, "Passwords Update task failure", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                progressUpdate.dismiss()
                Toast.makeText(context, "You cannot set an empty password", Toast.LENGTH_LONG).show()
                return
            }
        } else {
            progressUpdate.show()
            if (pass.isNotBlank()) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.updatePassword(pass)?.addOnCompleteListener { passwordUpdateTask ->
                    if (passwordUpdateTask.isSuccessful) {
                        if (pass == confpass) {
                            val dbRef = FirebaseDatabase.getInstance().getReference()
                                .child("Admin/$adminId")
                            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val admin = snapshot.getValue(Admin::class.java)
                                    val existingImageUrl = admin?.adminProfilePictureUrl ?: ""

                                    val updateData = Admin(
                                        fullName,
                                        gender,
                                        maritalStatus,
                                        phoneNumber,
                                        dateOfBirth,
                                        email,
                                        pass,
                                        existingImageUrl,
                                        adminId
                                    )

                                    dbRef.setValue(updateData).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // Show success message
                                            progressUpdate.dismiss()
                                            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                                            navController.popBackStack()
                                        } else {
                                            // Handle database update error
                                            progressUpdate.dismiss()
                                            Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    // Handle database error
                                    progressUpdate.dismiss()
                                    Toast.makeText(
                                        context,
                                        "ERROR: ${error.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                            progressUpdate.dismiss()
                            Toast.makeText(
                                context,
                                "Success with Image Retained",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        progressUpdate.dismiss()
                        Toast.makeText(context, "Password Update Task Failure", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }else{
                progressUpdate.dismiss()
                Toast.makeText(context, "You cannot set an empty password", Toast.LENGTH_LONG).show()
                return
            }
        }
    }


    fun staffsignup(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        email: String,
        pass: String,
        confpass: String,
        staffProfilePictureUri: Uri?,
        staffStatus: String
    ) {
        progress.show()
        if (staffProfilePictureUri != null) {
            if (fullName.isBlank() || gender.isBlank() || maritalStatus.isBlank() || phoneNumber.isBlank() || dateOfBirth.isBlank() || email.isBlank() || pass.isBlank() || confpass.isBlank()) {
                progress.dismiss()
                Toast.makeText(
                    context,
                    "Fill all the Fields Please \uD83D\uDE42",
                    Toast.LENGTH_LONG
                ).show()
                return
            } else if (pass != confpass) {
                progress.dismiss()
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_STAFF_REGISTER)
                return
            } else if (phoneNumber.length != 10) {
                progress.dismiss()
                Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_LONG).show()
                return
            } else {
                if (isStrongPassword(pass)) {
                    mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val staffId = System.currentTimeMillis().toString()
                                val storageRef = FirebaseStorage.getInstance().reference
                                val profilePicRef =
                                    storageRef.child("staff_profile_pictures/${staffId}")
                                profilePicRef.putFile(staffProfilePictureUri)
                                    .addOnSuccessListener { _ ->
                                        profilePicRef.downloadUrl.addOnSuccessListener { uri ->
                                            // Once the image is uploaded, save the user data including the image URL
                                            val staffProfilePictureUrl = uri.toString()
                                            val staffdata = Staff(
                                                fullName,
                                                gender,
                                                maritalStatus,
                                                phoneNumber,
                                                dateOfBirth,
                                                email,
                                                pass,
                                                staffProfilePictureUrl, // Save the image URL in the user data
                                                staffStatus,
                                                staffId

                                            )
                                            val regRef =
                                                FirebaseDatabase.getInstance().getReference()
                                                    .child("Staff").child(staffId)
                                            regRef.setValue(staffdata)
                                                .addOnCompleteListener { dataTask ->
                                                    if (dataTask.isSuccessful) {
                                                        progress.dismiss()
                                                        Toast.makeText(
                                                            context,
                                                            "Registered Successfully",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                        navController.navigate(ROUTE_STAFF_LOGIN)
                                                    } else {
                                                        progress.dismiss()
                                                        Toast.makeText(
                                                            context,
                                                            "${dataTask.exception!!.message}",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                        navController.navigate(ROUTE_STAFF_LOGIN)
                                                    }
                                                }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        progress.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Failed to upload image: ${e.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            } else {
                                progress.dismiss()
                                val errorMessage =
                                    task.exception?.message ?: "Could not Register, Retry"
                                if (errorMessage.contains("email address is already in use")) {
                                    Toast.makeText(
                                        context,
                                        "Email address is already registered",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                }
                                navController.navigate(ROUTE_STAFF_REGISTER)
                            }
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }else{
            progress.dismiss()
            Toast.makeText(context, "You are required to choose an image and fill in all the fields", Toast.LENGTH_LONG).show()
            return
        }
    }

    fun clientsignup(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        email: String,
        pass: String,
        confpass: String,
        clientProfilePictureUri: Uri?,
        clientStatus: String,
        fine: Double
    ) {
        progress.show()
        if (clientProfilePictureUri != null) {
            if (fullName.isBlank() || gender.isBlank() || maritalStatus.isBlank() || phoneNumber.isBlank() || dateOfBirth.isBlank() || email.isBlank() || pass.isBlank() || confpass.isBlank()) {
                progress.dismiss()
                Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_CLIENT_REGISTER)
                return
            } else if (pass != confpass) {
                progress.dismiss()
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_CLIENT_REGISTER)
                return
            } else if (phoneNumber.length != 10) {
                Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_LONG).show()
            } else {
                if (isStrongPassword(pass)) {
                    mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val clientId = System.currentTimeMillis().toString()
                                val storageRef = FirebaseStorage.getInstance().reference
                                val profilePicRef =
                                    storageRef.child("client_profile_pictures/${clientId}")
                                profilePicRef.putFile(clientProfilePictureUri)
                                    .addOnSuccessListener { _ ->
                                        profilePicRef.downloadUrl.addOnSuccessListener { uri ->
                                            val clientProfilePictureUrl = uri.toString()
                                            val clientdata = Clients(
                                                fullName,
                                                gender,
                                                maritalStatus,
                                                phoneNumber,
                                                dateOfBirth,
                                                email,
                                                pass,
                                                clientProfilePictureUrl,
                                                clientStatus,
                                                clientId,
                                                fine


                                            )
                                            val regRef =
                                                FirebaseDatabase.getInstance().getReference()
                                                    .child("Client").child(clientId)
                                            regRef.setValue(clientdata)
                                                .addOnCompleteListener { dataTask ->
                                                    if (dataTask.isSuccessful) {
                                                        progress.dismiss()
                                                        Toast.makeText(
                                                            context,
                                                            "Registered Successfully",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                        navController.navigate("$ROUTE_BORROW_HOME/$clientId")
                                                    } else {
                                                        progress.dismiss()
                                                        Toast.makeText(
                                                            context,
                                                            "${dataTask.exception!!.message}",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                        navController.navigate(ROUTE_CLIENT_LOGIN)
                                                    }
                                                }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        progress.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Failed to upload image: ${e.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            } else {
                                progress.dismiss()
                                val errorMessage =
                                    task.exception?.message ?: "Could not Register, Retry"
                                if (errorMessage.contains("email address is already in use")) {
                                    Toast.makeText(
                                        context,
                                        "Email address is already registered",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                }
                                navController.navigate(ROUTE_CLIENT_REGISTER)
                            }
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }else{
            progress.dismiss()
            Toast.makeText(context, "You are required to choose an image and fill in all the fields", Toast.LENGTH_LONG).show()
            return
        }
    }

    private fun getClientIdByEmail(email: String, callback: (String?) -> Unit) {
        val ref = FirebaseDatabase.getInstance().getReference().child("Client")
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val clientId = snap.key // Assuming the client ID is the key of the client node
                        callback(clientId)
                        return
                    }
                }
                callback(null) // Client ID not found
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Client", "Error fetching client ID: ${error.message}")
                callback(null) // Handle the error by returning null
            }
        })
    }


    fun clientlogin(
        email: String,
        pass: String,
    ){
        progress.show()
        if (email.isBlank() || pass.isBlank()){
            progress.dismiss()
            Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                getClientIdByEmail(email) { clientId ->
                    progress.dismiss()
                    if (clientId != null) {
                        if (it.isSuccessful) {
                            saveLoginState(context, clientId, "Client")
                            Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG)
                                .show()
                            navController.navigate("$ROUTE_BORROW_HOME/$clientId")
                        } else {
                            Toast.makeText(context, "${it.exception!!.message}", Toast.LENGTH_LONG)
                                .show()
                            navController.navigate(ROUTE_CLIENT_HOME)
                        }
                    } else {
                        Toast.makeText(context, "Failed to fetch client id", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    fun updateClient(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        email: String,
        pass: String,
        confpass: String,
        clientStatus: String,
        clientId: String,
        filePath: Uri?,
        fine: Double
    ) {
        progressUpdate.show()
        val storageReference = FirebaseStorage.getInstance().getReference().child("Client/$clientId")

        val updateData = Clients(
            fullName,
            gender,
            maritalStatus,
            phoneNumber,
            dateOfBirth,
            email,
            pass,
            "",
            clientStatus,
            clientId,
            fine
        )

        // Update book details in Firebase Realtime Database
        if (filePath != null) {
            if (pass.isNotBlank()) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.updatePassword(pass)?.addOnCompleteListener { passwordUpdateTask ->
                    if (passwordUpdateTask.isSuccessful) {
                        if (pass == confpass) {
                            if (isStrongPassword(pass)) {
                                val dbRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Client/$clientId")
                                dbRef.setValue(updateData).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // If an image file is provided, update the image in Firebase Storage
                                        filePath.let { fileUri ->
                                            storageReference.putFile(fileUri)
                                                .addOnCompleteListener { storageTask ->
                                                    if (storageTask.isSuccessful) {
                                                        progressUpdate.dismiss()
                                                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                                                            val imageUrl = uri.toString()
                                                            updateData.clientProfilePictureUrl =
                                                                imageUrl
                                                            dbRef.setValue(updateData) // Update the book entry with the image URL
                                                        }
                                                    } else {
                                                        progressUpdate.dismiss()
                                                        Toast.makeText(
                                                            context,
                                                            "Upload Failure",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                }
                                        }

                                        // Show success message
                                        progressUpdate.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Update successful",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        navController.popBackStack()
                                    } else {
                                        progressUpdate.dismiss()
                                        // Handle database update error
                                        Toast.makeText(
                                            context,
                                            "ERROR: ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } else {
                                progressUpdate.dismiss()
                                Toast.makeText(
                                    context,
                                    "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        progressUpdate.dismiss()
                        Toast.makeText(context, "Passwords Update task failure", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }else{
                Toast.makeText(context, "You cannot set an empty password", Toast.LENGTH_LONG).show()
                navController.navigateUp()
            }
        } else {
            progressUpdate.show()
            if (pass.isNotBlank()) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.updatePassword(pass)?.addOnCompleteListener { passwordUpdateTask ->
                    if (passwordUpdateTask.isSuccessful) {
                        if (pass == confpass) {
                            if (isStrongPassword(pass)) {
                                val dbRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Client/$clientId")
                                dbRef.addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val client = snapshot.getValue(Clients::class.java)
                                        val existingImageUrl =
                                            client?.clientProfilePictureUrl ?: ""

                                        val updateData = Clients(
                                            fullName,
                                            gender,
                                            maritalStatus,
                                            phoneNumber,
                                            dateOfBirth,
                                            email,
                                            pass,
                                            clientStatus,
                                            existingImageUrl,
                                            clientId,
                                            fine
                                        )

                                        dbRef.setValue(updateData)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    progressUpdate.dismiss()
                                                    // Show success message
                                                    Toast.makeText(
                                                        context,
                                                        "Update successful",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    navController.popBackStack()
                                                } else {
                                                    // Handle database update error
                                                    progressUpdate.dismiss()
                                                    Toast.makeText(
                                                        context,
                                                        "ERROR: ${task.exception?.message}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        progressUpdate.dismiss()
                                        // Handle database error
                                        Toast.makeText(
                                            context,
                                            "ERROR: ${error.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                                progressUpdate.dismiss()
                                Toast.makeText(
                                    context,
                                    "Success with Image Retained",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                progressUpdate.dismiss()
                                Toast.makeText(
                                    context,
                                    "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        progressUpdate.dismiss()
                        Toast.makeText(context, "Password Update Task Failure", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }else{
                progressUpdate.dismiss()
                Toast.makeText(context, "You cannot update an empty password", Toast.LENGTH_LONG).show()
                return
            }
        }
    }


    private fun getStaffIdAndStatusByEmail(email: String, callback: (String?, String?) -> Unit) {
        val ref = FirebaseDatabase.getInstance().getReference().child("Staff")
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val staffId = snap.key // Assuming the staff ID is the key of the staff node
                        val staffStatus = snap.child("staffStatus").getValue(String::class.java)
                        callback(staffId, staffStatus)
                        return
                    }
                }
                callback(null, null) // Staff ID not found
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Staff", "Error fetching staff ID and status: ${error.message}")
                callback(null, null) // Handle the error by returning null
            }
        })
    }

    fun stafflogin(
        email: String,
        pass: String
    ) {
        progress.show()
        if (email.isBlank() || pass.isBlank()) {
            progress.dismiss()
            Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                getStaffIdAndStatusByEmail(email) { staffId, staffStatus ->
                    progress.dismiss()
                    if (staffId != null && staffStatus == "Verified") {
                        if (it.isSuccessful) {
                            saveLoginState(context, staffId, "Staff")
                            Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG)
                                .show()
                            navController.navigate("$ROUTE_BOOKS_HOME/$staffId")
                        } else {
                            Toast.makeText(context, "${it.exception!!.message}", Toast.LENGTH_LONG)
                                .show()
                            navController.navigate(ROUTE_STAFF_HOME)
                        }
                    } else {
                        if (staffStatus != "Verified") {
                            Toast.makeText(context, "Account not verified", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(context, "Staff is null", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    fun updateStaff(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        email: String,
        pass: String,
        confpass: String,
        staffId: String,
        filePath: Uri?,
        staffStatus: String
    ) {
        progressUpdate.show()
        val storageReference = FirebaseStorage.getInstance().getReference().child("Staff/$staffId")

        val updateData = Staff(
            fullName,
            gender,
            maritalStatus,
            phoneNumber,
            dateOfBirth,
            email,
            pass,
            "",
            staffStatus,
            staffId
        )

        // Update book details in Firebase Realtime Database
        if (filePath != null) {
            if (pass.isNotBlank()) {
                if (isStrongPassword(pass)) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.updatePassword(pass)?.addOnCompleteListener { passwordUpdateTask ->
                        if (passwordUpdateTask.isSuccessful) {
                            if (pass == confpass) {
                                val dbRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Staff/$staffId")
                                dbRef.setValue(updateData).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // If an image file is provided, update the image in Firebase Storage
                                        filePath.let { fileUri ->
                                            storageReference.putFile(fileUri)
                                                .addOnCompleteListener { storageTask ->
                                                    if (storageTask.isSuccessful) {
                                                        progressUpdate.dismiss()
                                                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                                                            val imageUrl = uri.toString()
                                                            updateData.staffProfilePictureUrl =
                                                                imageUrl
                                                            dbRef.setValue(updateData) // Update the book entry with the image URL
                                                        }
                                                    } else {
                                                        progressUpdate.dismiss()
                                                        Toast.makeText(
                                                            context,
                                                            "Upload Failure",
                                                            Toast.LENGTH_LONG
                                                        )
                                                            .show()
                                                    }
                                                }
                                        }

                                        // Show success message
                                        progressUpdate.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Update successful",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        navController.popBackStack()
                                    } else {
                                        // Handle database update error
                                        progressUpdate.dismiss()
                                        Toast.makeText(
                                            context,
                                            "ERROR: ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } else {
                                progressUpdate.dismiss()
                                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG)
                                    .show()
                            }
                        } else {
                            progressUpdate.dismiss()
                            Toast.makeText(
                                context,
                                "Passwords Update task failure",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                } else {
                    progressUpdate.dismiss()
                    Toast.makeText(
                        context,
                        "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }else{
                progressUpdate.dismiss()
                Toast.makeText(context, "You can not update an empty password", Toast.LENGTH_LONG).show()
                return
            }
        } else {
            progressUpdate.show()
            if (pass.isNotBlank()) {
                if (isStrongPassword(pass)) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.updatePassword(pass)?.addOnCompleteListener { passwordUpdateTask ->
                        if (passwordUpdateTask.isSuccessful) {
                            if (pass == confpass) {
                                val dbRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Staff/$staffId")
                                dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val staff = snapshot.getValue(Staff::class.java)
                                        val existingImageUrl = staff?.staffProfilePictureUrl ?: ""

                                        val updateData = Staff(
                                            fullName,
                                            gender,
                                            maritalStatus,
                                            phoneNumber,
                                            dateOfBirth,
                                            email,
                                            pass,
                                            existingImageUrl,
                                            staffStatus,
                                            staffId
                                        )

                                        dbRef.setValue(updateData).addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                progressUpdate.dismiss()
                                                // Show success message
                                                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                                                navController.popBackStack()
                                            } else {
                                                progressUpdate.dismiss()
                                                // Handle database update error
                                                Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        // Handle database error
                                        progressUpdate.dismiss()
                                        Toast.makeText(context, "ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })
                                progressUpdate.dismiss()
                                Toast.makeText(context, "Success with Image Retained", Toast.LENGTH_LONG).show()
                            } else {
                                progressUpdate.dismiss()
                                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Password Update Task Failure", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    progressUpdate.dismiss()
                    Toast.makeText(context, "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.", Toast.LENGTH_LONG).show()
                }
            }else{
                progressUpdate.dismiss()
                Toast.makeText(context, "You cannot set an empty password", Toast.LENGTH_LONG).show()
                return
            }
        }
    }

    fun stafflogout(){
        mAuth.signOut()
        FirebaseAuth.getInstance().signOut()
        clearLoginState(context)
        Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show()
    }

    fun clientlogout(){
        mAuth.signOut()
        FirebaseAuth.getInstance().signOut()
        clearLoginState(context)
        Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show()
    }

    fun adminLogout(){
        mAuth.signOut()
        FirebaseAuth.getInstance().signOut()
        clearLoginState(context)
        Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show()
    }

    fun deliveryPersonnelLogout(){
        mAuth.signOut()
        FirebaseAuth.getInstance().signOut()
        clearLoginState(context)
        Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show()
    }

//    fun isloggedin(): Boolean{
//        return mAuth.currentUser != null
//    } This one checks first if user is logged in or not for him or she to use services

    fun viewClients(
        client: MutableState<Clients>,
        clients: SnapshotStateList<Clients>
    ): SnapshotStateList<Clients> {
        progress.show()
        val ref = FirebaseDatabase.getInstance().getReference().child("Client")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progress.dismiss()
                clients.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Clients::class.java)
                    client.value = value!!
                    clients.add(value)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                progress.dismiss()
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return clients
    }

    fun viewStaff(
        staff: MutableState<Staff>,
        mStaff: SnapshotStateList<Staff>
    ): SnapshotStateList<Staff> {
        val ref = FirebaseDatabase.getInstance().getReference().child("Staff")

        progress.show()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progress.dismiss()
                mStaff.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Staff::class.java)
                    staff.value = value!!
                    mStaff.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return mStaff
    }

    fun viewDeliveryPersonnel(
        deliveryPersonnel: MutableState<DeliveryPersonnel>,
        mDeliveryPersonnel: SnapshotStateList<DeliveryPersonnel>
    ): SnapshotStateList<DeliveryPersonnel> {
        val ref = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel")

        progress.show()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progress.dismiss()
                mDeliveryPersonnel.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(DeliveryPersonnel::class.java)
                    deliveryPersonnel.value = value!!
                    mDeliveryPersonnel.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return mDeliveryPersonnel
    }

    fun deleteDeliveryPersonnel(deliveryPersonnelId: String) {
        val delRef = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel/$deliveryPersonnelId")
        progressDelete.show()
        delRef.removeValue().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                progressDelete.dismiss()
                Log.d("Delete Delivery Personnel Account", "Delivery Personnel Account deleted")
                Toast.makeText(context, "Delivery Personnel Account deleted", Toast.LENGTH_SHORT).show()
            } else {
                progressDelete.dismiss()
                Log.e("Delete Delivery Personnel Account", "Error deleting Delivery Personnel Account", task.exception)
                Toast.makeText(context, "Error deleting Delivery Personnel Account: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteClient(clientId: String) {
        val delRef = FirebaseDatabase.getInstance().getReference().child("Client/$clientId")
        progress.show()
        delRef.removeValue().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                progressDelete.dismiss()
                Log.d("Delete Client Account", "Client Account deleted")
                Toast.makeText(context, "Client Account deleted", Toast.LENGTH_SHORT).show()
            } else {
                progressDelete.dismiss()
                Log.e("Delete Client Account", "Error deleting Client Account", task.exception)
                Toast.makeText(context, "Error deleting Client Account: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteStaff(staffId: String) {
        val delRef = FirebaseDatabase.getInstance().getReference().child("Staff/$staffId")
        progressDelete.show()
        delRef.removeValue().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                progressDelete.dismiss()
                Log.d("Delete Staff Account", "Staff Account deleted")
                Toast.makeText(context, "Staff Account deleted", Toast.LENGTH_SHORT).show()
            } else {
                progressDelete.dismiss()
                Log.e("Delete Staff Account", "Error deleting Staff Account", task.exception)
                Toast.makeText(context, "Error deleting Staff Account: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isStrongPassword(pass: String): Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$".toRegex()
        return pattern.matches(pass)
    }

    fun deliveryPersonnelSignup(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        accountStatus: String,
        deliveryStatus: String,
        vehicle: String,
        email: String,
        pass: String,
        confpass: String,
        deliveryPersonnelProfilePictureUrl: Uri?,
    ) {
        progress.show()
        if (deliveryPersonnelProfilePictureUrl != null) {
            if (fullName.isBlank() || gender.isBlank() || maritalStatus.isBlank() || phoneNumber.isBlank() || dateOfBirth.isBlank() || email.isBlank() || pass.isBlank() || confpass.isBlank()) {
                progress.dismiss()
                Toast.makeText(context, "Fill all the Fields Please \uD83D\uDE42", Toast.LENGTH_LONG).show()
                return
            } else if (pass != confpass) {
                progress.dismiss()
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_STAFF_REGISTER)
                return
            } else if (phoneNumber.length != 10) {
                progress.dismiss()
                Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_LONG).show()
                return
            } else {
                if (isStrongPassword(pass)) {
                    mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val deliveryPersonnelId = System.currentTimeMillis().toString()
                                val storageRef = FirebaseStorage.getInstance().reference
                                val profilePicRef = storageRef.child("delivery_personnel_profile_pictures/${deliveryPersonnelId}")
                                profilePicRef.putFile(deliveryPersonnelProfilePictureUrl)
                                    .addOnSuccessListener { _ ->
                                        profilePicRef.downloadUrl.addOnSuccessListener { uri ->
                                            // Once the image is uploaded, save the user data including the image URL
                                            val deliveryPersonnelProfilePictureUrl = uri.toString()
                                            val deliveryPersonnelData = DeliveryPersonnel(
                                                fullName,
                                                gender,
                                                maritalStatus,
                                                phoneNumber,
                                                dateOfBirth,
                                                accountStatus,
                                                deliveryStatus,
                                                vehicle,
                                                deliveryPersonnelProfilePictureUrl,// Save the image URL in the user data
                                                email,
                                                pass,
                                                deliveryPersonnelId
                                            )
                                            val regRef = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel").child(deliveryPersonnelId)
                                            regRef.setValue(deliveryPersonnelData)
                                                .addOnCompleteListener { dataTask ->
                                                    if (dataTask.isSuccessful) {
                                                        progress.dismiss()
                                                        Toast.makeText(context, "Account Created Successfully, wait for verification", Toast.LENGTH_LONG).show()
                                                        navController.navigate(ROUTE_DELIVERY_PERSONNEL_LOGIN)
                                                    } else {
                                                        progress.dismiss()
                                                        Toast.makeText(context, "${dataTask.exception!!.message}", Toast.LENGTH_LONG).show()
                                                        navController.navigate(
                                                            ROUTE_DELIVERY_PERSONNEL_REGISTER)
                                                    }
                                                }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        progress.dismiss()
                                        Toast.makeText(context, "Failed to upload image: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                            } else {
                                progress.dismiss()
                                val errorMessage =
                                    task.exception?.message ?: "Could not Register, Retry"
                                if (errorMessage.contains("email address is already in use")) {
                                    Toast.makeText(context, "Email address is already registered", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                }
                                navController.navigate(ROUTE_DELIVERY_PERSONNEL_REGISTER)
                            }
                        }
                } else {
                    Toast.makeText(context, "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            progress.dismiss()
            Toast.makeText(context, "You are required to choose an image and fill in all the fields", Toast.LENGTH_LONG).show()
            return
        }
    }

    fun deliveryPersonnelLogin(
        email: String,
        pass: String
    ){
        progress.show()
        if (email.isBlank() || pass.isBlank()){
            progress.dismiss()
            Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                getDeliveryPersonnelIdAndStatusByEmail(email) { deliveryPersonnelId, accountStatus ->
                    if (deliveryPersonnelId != null && accountStatus == "Verified") {
                        if (it.isSuccessful) {
                            saveLoginState(context, deliveryPersonnelId, "DeliveryPersonnel")
                            progress.dismiss()
                            Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG).show()
                            navController.navigate("$ROUTE_DELIVERY_PERSONNEL_HOME/$deliveryPersonnelId")
                        } else {
                            progress.dismiss()
                            Toast.makeText(context, "${it.exception!!.message}", Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_DELIVERY_PERSONNEL_LOGIN)
                        }
                    } else {
                        if (accountStatus != "Verified") {
                            progress.dismiss()
                            Toast.makeText(context, "Account not verified", Toast.LENGTH_LONG).show()
                        } else {
                            progress.dismiss()
                            Toast.makeText(context, "Delivery Personnel is null", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun getDeliveryPersonnelIdAndStatusByEmail(email: String, callback: (String?, String?) -> Unit) {
        val ref = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel")
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val deliveryPersonnelId = snap.key // Assuming the client ID is the key of the client node
                        val accountStatus = snap.child("accountStatus").getValue(String::class.java)
                        callback(deliveryPersonnelId, accountStatus)
                        return
                    }
                }
                callback(null, null) // Client ID not found
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DeliveryPersonnel", "Error fetching Delivery Personnel ID: ${error.message}")
                callback(null, null) // Handle the error by returning null
            }
        })
    }

    fun updateDeliveryPersonnel(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        accountStatus: String,
        deliveryStatus: String,
        vehicle: String,
        email: String,
        pass: String,
        confpass: String,
        deliveryPersonnelId: String,
        filePath: Uri?
    ) {
        progressUpdate.show()
        val storageReference = FirebaseStorage.getInstance().getReference().child("DeliveryPersonnel/$deliveryPersonnelId")
        val updateData = DeliveryPersonnel(
            fullName,
            gender,
            maritalStatus,
            phoneNumber,
            dateOfBirth,
            accountStatus,
            deliveryStatus,
            vehicle,
            "",
            email,
            pass,
            deliveryPersonnelId
        )

        // Update book details in Firebase Realtime Database
        if (filePath != null) {
            if (pass.isNotBlank()) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.updatePassword(pass)?.addOnCompleteListener { passwordUpdateTask ->
                    if (passwordUpdateTask.isSuccessful) {
                        if (pass == confpass) {
                            if (isStrongPassword(pass)) {
                                val dbRef = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel/$deliveryPersonnelId")
                                dbRef.setValue(updateData).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // If an image file is provided, update the image in Firebase Storage
                                        filePath.let { fileUri ->
                                            storageReference.putFile(fileUri)
                                                .addOnCompleteListener { storageTask ->
                                                    if (storageTask.isSuccessful) {
                                                        progressUpdate.dismiss()
                                                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                                                            val imageUrl = uri.toString()
                                                            updateData.deliveryPersonnelProfilePictureUrl = imageUrl
                                                            dbRef.setValue(updateData) // Update the book entry with the image URL
                                                        }
                                                    } else {
                                                        progressUpdate.dismiss()
                                                        Toast.makeText(context, "Upload Failure", Toast.LENGTH_LONG).show()
                                                    }
                                                }
                                        }

                                        // Show success message
                                        progressUpdate.dismiss()
                                        Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    } else {
                                        progressUpdate.dismiss()
                                        // Handle database update error
                                        Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                progressUpdate.dismiss()
                                Toast.makeText(context, "Your password strength is weak, pattern requires at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one special character.", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        progressUpdate.dismiss()
                        Toast.makeText(context, "Passwords Update task failure", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                progressUpdate.dismiss()
                Toast.makeText(context, "You cannot set an empty password", Toast.LENGTH_LONG).show()
                return
            }
        } else {
            progressUpdate.show()
            if (pass.isNotBlank()) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.updatePassword(pass)?.addOnCompleteListener { passwordUpdateTask ->
                    if (passwordUpdateTask.isSuccessful) {
                        if (pass == confpass) {
                            val dbRef = FirebaseDatabase.getInstance().getReference()
                                .child("DeliveryPersonnel/$deliveryPersonnelId")
                            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val deliveryPersonnel = snapshot.getValue(DeliveryPersonnel::class.java)
                                    val existingImageUrl = deliveryPersonnel?.deliveryPersonnelProfilePictureUrl ?: ""

                                    val updateData = DeliveryPersonnel(
                                        fullName,
                                        gender,
                                        maritalStatus,
                                        phoneNumber,
                                        dateOfBirth,
                                        accountStatus,
                                        deliveryStatus,
                                        vehicle,
                                        existingImageUrl,
                                        email,
                                        pass,
                                        deliveryPersonnelId
                                    )

                                    dbRef.setValue(updateData).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // Show success message
                                            progressUpdate.dismiss()
                                            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                                            navController.popBackStack()
                                        } else {
                                            // Handle database update error
                                            progressUpdate.dismiss()
                                            Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    // Handle database error
                                    progressUpdate.dismiss()
                                    Toast.makeText(context, "ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Success with Image Retained", Toast.LENGTH_LONG).show()
                        } else {
                            progressUpdate.dismiss()
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        progressUpdate.dismiss()
                        Toast.makeText(context, "Password Update Task Failure", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                progressUpdate.dismiss()
                Toast.makeText(context, "You cannot set an empty password", Toast.LENGTH_LONG).show()
                return
            }
        }
    }


    fun deliveryStatusAvailable(deliveryPersonnelId: String) {
        val deliveryPersonnelRef = FirebaseDatabase.getInstance().getReference("DeliveryPersonnel").child(deliveryPersonnelId)
        deliveryPersonnelRef.child("deliveryStatus").setValue("Available")
    }

    fun deliveryStatusUnavailable(deliveryPersonnelId: String) {
        val deliveryPersonnelRef = FirebaseDatabase.getInstance().getReference("DeliveryPersonnel").child(deliveryPersonnelId)
        deliveryPersonnelRef.child("deliveryStatus").setValue("Unavailable")
    }
}