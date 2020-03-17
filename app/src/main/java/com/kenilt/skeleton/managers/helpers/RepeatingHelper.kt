package com.kenilt.skeleton.managers.helpers

/**
 * Created by thangnguyen on 10/13/17.
 */

abstract class RepeatingHelper(repeatingTime: Int) {
    private val handler = android.os.Handler()
    private var runnable: Runnable? = null
    private var repeatingTime = 5000

    init {
        this.repeatingTime = repeatingTime
        runnable = Runnable {
            onTick()

            handler.postDelayed(runnable, this@RepeatingHelper.repeatingTime.toLong())
        }
    }

    fun startDelayedRepeatingTask() {
        handler.postDelayed(runnable, repeatingTime.toLong())
    }

    fun startRepeatingTask() {
        runnable?.run()
    }

    fun stopRepeatingTask() {
        handler.removeCallbacks(runnable)
    }

    abstract fun onTick()
}
