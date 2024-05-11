package com.app.koin_mvvm_retrofit_flow_room.data


/**
 * Created on mayo.
 * year 2024 .
 */

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.app.koin_mvvm_retrofit_flow_room.domain.NetworkUtils

class AndroidNetworkUtils(private val context: Context) : NetworkUtils {

    override fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}