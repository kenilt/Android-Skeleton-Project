package com.kenilt.skeleton.managers.helpers

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * Created by thangnguyen on 3/30/18.
 */
class ThreadFinishedCounting(var totalThread: Int, private var waitingTime: Long) {
    companion object {
        const val WAITING_TIME = 20000L  // 20 seconds
    }

    private var successCount: Int = 0
    private var failedCount: Int = 0
    private var callback: ThreadCountingCallback? = null
    private var expiredFutureTask: ScheduledFuture<*>? = null

    constructor(totalThread: Int) : this(totalThread, WAITING_TIME)

    fun start(callback: ThreadCountingCallback) {
        this.callback = callback
        expiredFutureTask = Executors.newSingleThreadScheduledExecutor().schedule({
            callback.onFinishCounting(successCount, failedCount)
            this.callback = null
        }, waitingTime, TimeUnit.MILLISECONDS)
    }

    fun countSuccess() {
        successCount++

        checkProgress()
    }

    fun countFailed() {
        failedCount++

        checkProgress()
    }

    fun cancel() {
        expiredFutureTask?.cancel(true)
        callback = null
    }

    private fun checkProgress() {
        if (successCount + failedCount >= totalThread) {
            expiredFutureTask?.cancel(true)
            callback?.onFinishCounting(successCount, failedCount)
        }
    }

    interface ThreadCountingCallback {
        fun onFinishCounting(successCount: Int, failedCount: Int)
    }
}
