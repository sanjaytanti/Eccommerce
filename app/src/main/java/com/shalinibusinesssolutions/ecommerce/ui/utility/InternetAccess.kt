package com.shalinibusinesssolutions.ecommerce.ui.utility

import android.content.Context
import android.net.ConnectivityManager

object InternetAccess {
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return if (wifiInfo != null && wifiInfo.isConnected || mobileInfo != null && mobileInfo.isConnected) {
            true
        } else {
            false
        }
    }

}