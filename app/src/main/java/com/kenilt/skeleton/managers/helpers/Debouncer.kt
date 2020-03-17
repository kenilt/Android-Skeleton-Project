package com.kenilt.skeleton.managers.helpers

import android.os.Handler
import javax.inject.Inject

/**
 * Created by thangnguyen on 10/17/17.
 */

class Debouncer @Inject constructor() {
    private val handler: Handler = Handler()

    fun reset() {
        // Remove any previous runnable callbacks
        handler.removeCallbacksAndMessages(null)
    }

    fun debounce(callback: Runnable, delay: Int) {
        reset()
        handler.postDelayed(callback, delay.toLong())
    }
}
