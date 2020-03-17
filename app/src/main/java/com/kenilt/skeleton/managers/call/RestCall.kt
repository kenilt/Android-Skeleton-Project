package com.kenilt.skeleton.managers.call

import com.google.gson.Gson
import com.kenilt.skeleton.managers.managers.ReportManager
import com.kenilt.skeleton.model.response.ErrorResponse
import com.kenilt.skeleton.utils.StatusCodeUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.min

/**
 * Created by thangnguyen on 2019-07-03.
 */
open class RestCall<T>(private val call: Call<T>) {
    private var onSuccess: ((data: T?) -> Unit)? = null
    private var onApiError: ((errorResponse: ErrorResponse?) -> Unit)? = null
    private var onException: ((throwable: Throwable) -> Unit)? = null
    private var onBefore: (() -> Unit)? = null
    private var onDone: (() -> Unit)? = null

    fun onSuccess(onSuccess: ((data: T?) -> Unit)) = apply { this.onSuccess = onSuccess }

    fun onApiError(onApiError: ((errorResponse: ErrorResponse?) -> Unit)) = apply { this.onApiError = onApiError }

    fun onException(onException: ((throwable: Throwable) -> Unit)) = apply { this.onException = onException }

    /**
     * This function was called after receive response or error,
     * but it was called before other stuff
     */
    fun onBefore(onBefore: (() -> Unit)) = apply { this.onBefore = onBefore }

    /**
     * This function was called after all other stuffs
     */
    fun onDone(onDone: (() -> Unit)) = apply { this.onDone = onDone }

    open fun execute() {
        try {
            val response = call.execute()
            onBefore?.invoke()
            val data = response.body()
            if (response.isSuccessful) {
                onSuccess?.invoke(data)
            } else {
                handleApiError(call, response)
            }
        } catch (exception: Exception) {
            onBefore?.invoke()
            if (exception !is IOException) {
                handleNotIOException(exception)
                onApiError?.invoke(ErrorResponse(error = exception.message))
            } else {
                onException?.invoke(exception)
            }
        }
        onDone?.invoke()
    }

    fun enqueue() {
        call.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                onBefore?.invoke()
                val data = response.body()
                if (response.isSuccessful && data != null) {
                    onSuccess?.invoke(data)
                } else {
                    handleApiError(call, response)
                }
                onDone?.invoke()
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onBefore?.invoke()
                if (t !is IOException) {
                    handleNotIOException(t)
                    onApiError?.invoke(ErrorResponse(error = t.message))
                } else if (t.message != "Canceled") {
                    onException?.invoke(t)
                }
                onDone?.invoke()
            }
        })
    }

    private fun <T> handleApiError(call: Call<T>, response: Response<T>) {
        val errorBody = response.errorBody()
        if (errorBody != null) {
            var errorResponse: ErrorResponse? = null
            val responseCode = response.code()
            val path = "${call.request().method} | ${call.request().url.toUrl().file} | $responseCode"
            try {
                val errorBodyString = errorBody.string()
                errorResponse = Gson().fromJson(errorBodyString, ErrorResponse::class.java)
                errorResponse?.let {
                    val shortErrorString = errorBodyString.substring(0, min(300, errorBodyString.length))
                    if (StatusCodeUtil.isServerError(responseCode)) {
                        ReportManager.getReporter().sendErrorMessage("$path | $shortErrorString <@thang>")
                    } else {
                        if (responseCode == 422 || (responseCode == 404 && path.contains("/popups/"))) {
                            ReportManager.getReporter().sendLogMessage("$path | $shortErrorString")
                        } else {
                            ReportManager.getReporter().sendErrorMessage("$path | $shortErrorString")
                        }
                    }
                }
            } catch (e: Exception) {
                ReportManager.getReporter().sendThrowable("Cannot parse error body to ErrorResponse | $path", e)
            }
            onApiError?.invoke(errorResponse)
        }
    }

    private fun handleNotIOException(throwable: Throwable) {
        val path = "${call.request().method} | ${call.request().url.toUrl().file}"
        ReportManager.getReporter().sendThrowable("An error happen when calling by retrofit | $path", throwable)
    }
}
