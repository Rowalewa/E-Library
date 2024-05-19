@file:Suppress("DEPRECATION")

package com.example.e_library.data

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.e_library.models.Contact
import com.google.firebase.database.FirebaseDatabase

class ContactViewModel (
    var navController: NavHostController,
    var context: Context
){
    private var progress: ProgressDialog = ProgressDialog(context)

    init {
        progress.setTitle("Submitting")
        progress.setMessage("Please wait...")
    }
    fun saveContactUs(
        guestName: String,
        guestEmail: String,
        guestPhoneNumber: String,
        guestComment: String,
        guestCallback: String
    ) {
        val guestId = System.currentTimeMillis().toString()
        val contactRef = FirebaseDatabase.getInstance().getReference().child("Contact/$guestId/$guestName")
        val contactData = Contact(
            guestName,
            guestEmail,
            guestPhoneNumber,
            guestComment,
            guestCallback,
            guestId
        )
        progress.show()
        if (guestName.isNotBlank() || guestEmail.isNotBlank() || guestComment.isNotBlank()) {
            if (guestPhoneNumber.length == 10 || guestPhoneNumber.isEmpty()) {
                contactRef.setValue(contactData).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Submission successful", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "ERROR: ${it.exception!!.message}", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                }
            }else{
                Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Fill in all the required(*) fields", Toast.LENGTH_LONG).show()
        }

    }
}