package com.kenilt.skeleton.managers.helpers

import android.os.Handler

class Throttler {
    private var isRunning = false
    private val handler: Handler = Handler()

    fun reset() {
        // Remove any previous runnable callbacks
        handler.removeCallbacksAndMessages(null)
        isRunning = false
    }

    fun throttle(callback: Runnable, duration: Int) {
        if (!isRunning) {
            isRunning = true
            callback.run()
            handler.postDelayed({
                isRunning = false
            }, duration.toLong())
        }
    }
}
