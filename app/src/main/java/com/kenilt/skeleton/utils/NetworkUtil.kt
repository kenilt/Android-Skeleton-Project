package com.kenilt.skeleton.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager

object NetworkUtil {
    /**
     * Check if device has internet connection
     *
     * @param context
     * @return
     */
    fun hasConnection(context: Context): Boolean {
        val available = isAvailable(context)

        if (!available) {
            ToastUtil.showNetworkErrorToast(context)
        }

        return available
    }

    fun isAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    /**
     * check if GPS is enable or not
     *
     * @param mLocationManager
     * @return
     */
    fun isGPSProviderEnabled(mLocationManager: LocationManager): Boolean {
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
