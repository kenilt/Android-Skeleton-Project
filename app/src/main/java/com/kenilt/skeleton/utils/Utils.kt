package com.kenilt.skeleton.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import androidx.core.content.ContextCompat

/**
 * Created by thanh.nguyen on 11/26/2016.
 */

object Utils {

    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                model.capitalize()
            } else {
                manufacturer.capitalize() + " " + model
            }
        }

    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

    val density: Float
        get() = Resources.getSystem().displayMetrics.density

    val widthInDp: Int by lazy {
        (screenWidth / density).toInt()
    }

    fun convertDpToPx(context: Context, dp: Int): Int {
        return convertDpToPx(context, dp.toFloat())
    }

    fun convertDpToPx(context: Context, dp: Float): Int {
        if (dp == 0f) return 0
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.resources.displayMetrics).toInt()
    }

    fun getColor(context: Context, id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

}
