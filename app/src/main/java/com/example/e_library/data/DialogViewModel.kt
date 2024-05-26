package com.example.e_library.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {
    private val showDialog: MutableState<Boolean> = mutableStateOf(false)

    fun showDialog() {
        showDialog.value = true
    }

    fun hideDialog() {
        showDialog.value = false
    }
}