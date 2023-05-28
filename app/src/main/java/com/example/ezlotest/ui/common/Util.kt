package com.example.ezlotest.ui.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun hasInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
    else{
        connectivityManager.activeNetworkInfo?.run {
            return when(type){
                ConnectivityManager.TYPE_WIFI -> return true
                ConnectivityManager.TYPE_MOBILE -> return true
                ConnectivityManager.TYPE_ETHERNET -> return true
                else -> false
            }
        }
    }
    return false
}

fun MaterialAlertDialogBuilder.showAlert(message: String, pTextButton: String, nTextButton: String? = null, onClick: (() -> Unit)? = null) {
    setMessage(message)
    setPositiveButton(pTextButton) { dialog, _ ->
        onClick?.invoke()
        dialog.dismiss()
    }
    setNegativeButton(nTextButton) { dialog, _ ->
        dialog.dismiss()
    }
    show()
}