package com.kenilt.skeleton.utils

import android.util.Log
import com.kenilt.skeleton.BuildConfig

/**
 * Created by thanh.nguyen on 11/15/16.
 */
object LXLog {
    val TAG = "LXLog"

    fun e(tag: String, msg: String, exception: Exception? = null) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, exception)
        }
    }

    fun w(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun d(msg: String) {
        d(TAG, msg)
    }
}
