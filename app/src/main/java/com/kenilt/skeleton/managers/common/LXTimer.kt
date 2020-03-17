package com.kenilt.skeleton.managers.common

import android.os.Handler

/**
 * Created by thangnguyen on 1/21/19.
 */
class LXTimer() {
    private var handler = Handler()
    var action: Runnable? = null
    private var periodMillis: Long = 1000

    constructor(periodMillis: Long): this() {
        this.periodMillis = periodMillis
    }

    private fun schedule(periodMillis: Long) {
        this.periodMillis = periodMillis
        handler.postDelayed(object : Runnable {
            override fun run() {
                action?.run()
                handler.postDelayed(this, periodMillis)
            }
        }, periodMillis)
    }

    private fun schedule() {
        schedule(periodMillis)
    }

    fun restart() {
        cancel()
        schedule()
    }

    fun cancel() {
        handler.removeCallbacksAndMessages(null)
    }

    fun invokeAndRestart() {
        action?.run()
        restart()
    }
}
