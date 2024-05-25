package com.example.e_library.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.e_library.models.NetworkUtils

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    private val networkUtils = NetworkUtils(application)
    val isConnected: LiveData<Boolean> = networkUtils.isConnected

    override fun onCleared() {
        super.onCleared()
        networkUtils.unregisterCallback()
    }
}