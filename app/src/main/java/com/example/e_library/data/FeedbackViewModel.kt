package com.example.e_library.data

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.e_library.models.Feedback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FeedbackViewModel(
    var navController: NavHostController,
    var context: Context
) {
    private var progress: ProgressDialog = ProgressDialog(context)

    init {
        progress.setTitle("Submitting")
        progress.setMessage("Please wait...")
    }

    fun saveFeedbackToFirebaseStaff(
        feedbackName: String,
        feedbackEmailAddress: String,
        feedbackText: String
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val isEmailSimilar = feedbackEmailAddress == currentUser.email
            val isEmailFilled = feedbackEmailAddress.isNotBlank()

            if (isEmailFilled && !isEmailSimilar) {
                Toast.makeText(context, "Email address does not match current user", Toast.LENGTH_LONG).show()
                return
            }else {
                val feedbackData = Feedback(
                    feedbackName,
                    feedbackEmailAddress,
                    feedbackText
                )
                val feedbackId = System.currentTimeMillis().toString()
                val feedbackRef = FirebaseDatabase.getInstance().getReference().child("FeedbackStaff/$feedbackId/$feedbackName")
                progress.show()
                if (feedbackName.isNotBlank() || feedbackText.isNotBlank()) {
                    feedbackRef.setValue(feedbackData).addOnCompleteListener {
                        progress.dismiss()
                        if (it.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Feedback Submission successful",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(
                                context,
                                "ERROR: ${it.exception!!.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
                        }
                    }
                } else {
                    Toast.makeText(context, "Fill in all the required(*) fields", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } else {
            Toast.makeText(context, "Email address does not match current user", Toast.LENGTH_LONG).show()
        }
    }

    fun saveFeedbackToFirebaseClient(
        feedbackName: String,
        feedbackEmailAddress: String,
        feedbackText: String
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val isEmailSimilar = feedbackEmailAddress == currentUser.email
            val isEmailFilled = feedbackEmailAddress.isNotBlank()

            if (isEmailFilled && !isEmailSimilar) {
                Toast.makeText(context, "Email address does not match current user", Toast.LENGTH_LONG).show()
                return
            }else {
                val feedbackData = Feedback(
                    feedbackName,
                    feedbackEmailAddress,
                    feedbackText
                )
                val feedbackId = System.currentTimeMillis().toString()
                val feedbackRef = FirebaseDatabase.getInstance().getReference().child("FeedbackClient/$feedbackId/$feedbackName")
                progress.show()
                if (feedbackName.isNotBlank() || feedbackText.isNotBlank()) {
                    feedbackRef.setValue(feedbackData).addOnCompleteListener {
                        progress.dismiss()
                        if (it.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Feedback Submission successful",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(
                                context,
                                "ERROR: ${it.exception!!.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
                        }
                    }
                } else {
                    Toast.makeText(context, "Fill in all the required(*) fields", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } else {
            Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
        }
    }

    fun saveFeedbackToFirebaseAdmin(
        feedbackName: String,
        feedbackEmailAddress: String,
        feedbackText: String
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val isEmailSimilar = feedbackEmailAddress == currentUser.email
            val isEmailFilled = feedbackEmailAddress.isNotBlank()

            if (isEmailFilled && !isEmailSimilar) {
                Toast.makeText(context, "Email address does not match current user", Toast.LENGTH_LONG).show()
                return
            }else {
                val feedbackData = Feedback(
                    feedbackName,
                    feedbackEmailAddress,
                    feedbackText
                )
                val feedbackId = System.currentTimeMillis().toString()
                val feedbackRef = FirebaseDatabase.getInstance().getReference().child("FeedbackAdmin/$feedbackId/$feedbackName")
                progress.show()
                if (feedbackName.isNotBlank() || feedbackText.isNotBlank()) {
                    feedbackRef.setValue(feedbackData).addOnCompleteListener {
                        progress.dismiss()
                        if (it.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Feedback Submission successful",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(
                                context,
                                "ERROR: ${it.exception!!.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
                        }
                    }
                } else {
                    Toast.makeText(context, "Fill in all the required(*) fields", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } else {
            Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
        }
    }

    fun saveFeedbackToFirebaseDeliveryPersonnel(
        feedbackName: String,
        feedbackEmailAddress: String,
        feedbackText: String
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val isEmailSimilar = feedbackEmailAddress == currentUser.email
            val isEmailFilled = feedbackEmailAddress.isNotBlank()

            if (isEmailFilled && !isEmailSimilar) {
                Toast.makeText(context, "Email address does not match current user", Toast.LENGTH_LONG).show()
                return
            }else {
                val feedbackData = Feedback(
                    feedbackName,
                    feedbackEmailAddress,
                    feedbackText
                )
                val feedbackId = System.currentTimeMillis().toString()
                val feedbackRef = FirebaseDatabase.getInstance().getReference().child("FeedbackDeliveryPersonnel/$feedbackId/$feedbackName")
                progress.show()
                if (feedbackName.isNotBlank() || feedbackText.isNotBlank()) {
                    feedbackRef.setValue(feedbackData).addOnCompleteListener {
                        progress.dismiss()
                        if (it.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Feedback Submission successful",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(
                                context,
                                "ERROR: ${it.exception!!.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
                        }
                    }
                } else {
                    Toast.makeText(context, "Fill in all the required(*) fields", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } else {
            Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
        }
    }

    fun saveFeedbackToFirebaseAttendant(
        feedbackName: String,
        feedbackEmailAddress: String,
        feedbackText: String
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val isEmailSimilar = feedbackEmailAddress == currentUser.email
            val isEmailFilled = feedbackEmailAddress.isNotBlank()

            if (isEmailFilled && !isEmailSimilar) {
                Toast.makeText(context, "Email address does not match current user", Toast.LENGTH_LONG).show()
                return
            }else {
                val feedbackData = Feedback(
                    feedbackName,
                    feedbackEmailAddress,
                    feedbackText
                )
                val feedbackId = System.currentTimeMillis().toString()
                val feedbackRef = FirebaseDatabase.getInstance().getReference().child("FeedbackAttendant/$feedbackId/$feedbackName")
                progress.show()
                if (feedbackName.isNotBlank() || feedbackText.isNotBlank()) {
                    feedbackRef.setValue(feedbackData).addOnCompleteListener {
                        progress.dismiss()
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Feedback Submission successful", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "ERROR: ${it.exception!!.message}", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    }
                } else {
                    Toast.makeText(context, "Fill in all the required(*) fields", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
        }
    }
}