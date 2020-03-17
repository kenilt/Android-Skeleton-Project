package com.kenilt.skeleton.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object DeviceUtil {

    @SuppressLint("HardwareIds")
    fun androidId(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

}
