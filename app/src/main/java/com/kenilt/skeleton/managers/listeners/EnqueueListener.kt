package com.kenilt.skeleton.managers.listeners

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
 * Created by thangnguyen on 11/26/18.
 */
interface EnqueueListener<T>: Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        onBefore()
        val data = response.body()
        if (response.isSuccessful && data != null) {
            onSuccess(call, data)
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                var errorResponse: ErrorResponse? = null
                val path = "${call.request().method} | ${call.request().url?.toUrl()?.file} | ${response.code()}"
                try {
                    val errorBodyString = errorBody.string()
                    errorResponse = Gson().fromJson(errorBodyString, ErrorResponse::class.java)
                    errorResponse?.let {
                        val shortErrorString = errorBodyString.substring(0, min(300, errorBodyString.length))
                        if (StatusCodeUtil.isServerError(response.code())) {
                            ReportManager.getReporter().sendErrorMessage("$path | $shortErrorString <@thang>")
                        } else {
                            if (response.code() == 422) {
                                ReportManager.getReporter().sendLogMessage("$path | $shortErrorString")
                            } else {
                                ReportManager.getReporter().sendErrorMessage("$path | $shortErrorString")
                            }
                        }
                    }
                } catch (e: Exception) {
                    ReportManager.getReporter().sendThrowable("Cannot parse error body to ErrorResponse | $path", e)
                }
                onApiError(call, errorResponse)
            }
        }
        onDone()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onBefore()
        if (t !is IOException) {
            val path = "${call.request().method} | ${call.request().url.toUrl().file}"
            ReportManager.getReporter().sendThrowable("An error happen when calling by retrofit | $path", t)
        }
        if (t !is IOException || t.message != "Canceled") {
            onThrowable(call, t)
        }
        onDone()
    }

    /**
     * When api call before, don't care is success of fail
     */
    fun onBefore() {}

    fun onSuccess(call: Call<T>, data: T)

    fun onApiError(call: Call<T>, errorResponse: ErrorResponse?) {}

    fun onThrowable(call: Call<T>, t: Throwable) {}

    /**
     * When api call done, don't care is success or fail
     */
    fun onDone() {}
}
