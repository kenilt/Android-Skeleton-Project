package com.kenilt.skeleton.managers.listeners

import android.os.Handler
import com.kenilt.skeleton.managers.helpers.SimpleGeometricCounter
import com.kenilt.skeleton.model.response.ErrorResponse
import retrofit2.Call

/**
 * Created by thangnguyen on 12/7/18.
 */
abstract class RetryableEnqueueListener<T>(private var maxTryCount: Int): EnqueueListener<T> {
    private var tryCount: Int = 0

    constructor(): this(3)

    override fun onApiError(call: Call<T>, errorResponse: ErrorResponse?) {
        retryCall(call)
    }

    override fun onThrowable(call: Call<T>, t: Throwable) {
        retryCall(call)
    }

    protected fun retryCall(call: Call<T>) {
        if (tryCount >= maxTryCount) {
            onRetriedFailed()
            return
        }

        val waitingTime = SimpleGeometricCounter().getValueAt(tryCount) * 1000
        Handler().postDelayed({
            tryCount ++
            call.clone().enqueue(this)
        }, waitingTime)
    }

    open fun onRetriedFailed() {}
}
