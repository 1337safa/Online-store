package com.safronov_original_app_online_store.core.android.network_state

import android.content.Context
import android.net.ConnectivityManager
import java.net.InetAddress

//TODO write code to check if there is network or not before request in all methods that work with network!
class NetworkState(
    private val context: Context
) {

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

    private fun isInternetAvailable(): Boolean {
        try {
            val address: InetAddress = InetAddress.getByName("www.google.com")
            return !address.equals("")
        } catch (e: Exception) {
            return false
        }
    }

}