package com.safronov_original_app_online_store.core.android.network_state

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

//TODO write code to check if there is network or not before request in all methods that work with network!
class NetworkState(
    private val context: Context
) {

    fun isOnline(online: () -> Unit, offline: () -> Unit) {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                online.invoke()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                offline.invoke()
            }
        }

    }

}