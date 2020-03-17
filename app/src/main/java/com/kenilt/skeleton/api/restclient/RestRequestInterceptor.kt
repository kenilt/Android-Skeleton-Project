package com.kenilt.skeleton.api.restclient

import com.kenilt.skeleton.BuildConfig
import com.kenilt.skeleton.LXApplication
import com.kenilt.skeleton.constant.Constant
import com.kenilt.skeleton.managers.helpers.ControllerHelper
import com.kenilt.skeleton.managers.managers.ReportManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*

class RestRequestInterceptor : Interceptor {

    companion object {
        const val ACCESS_TOKEN_KEY = "Access-Token"
        const val PLAT_FORM_KEY = "Platform"
        const val APP_VERSION_KEY = "App-Version"
        const val BUILD_VERSION_KEY = "Build-Version"
        const val DEVICE_ID_KEY = "Device-Id"
        const val ANDROID = "android"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        // handle default headers
        // access token
        val token = ControllerHelper.token()
        if (token.isNotEmpty()) {
            builder.header(ACCESS_TOKEN_KEY, token)
        }
        // platform and version
        builder.header(PLAT_FORM_KEY, ANDROID)
        builder.header(APP_VERSION_KEY, BuildConfig.VERSION_NAME)
        builder.header(BUILD_VERSION_KEY, BuildConfig.VERSION_CODE.toString())
        builder.header(DEVICE_ID_KEY, LXApplication.instance.deviceId)
        val request = builder.build()
        return processRequest(chain, request, UUID.randomUUID().toString())
    }

    @Throws(IOException::class)
    fun processRequest(chain: Interceptor.Chain, request: Request, requestUUID: String): Response {
        val response = chain.proceed(request)

        val responseTime = response.receivedResponseAtMillis - response.sentRequestAtMillis
        if (responseTime > Constant.SLOW_TIME_LEVEL) {
            ReportManager.getReporter().sendTimeResponseMessage(responseTime, requestUUID, request.method + " " + request.url.toUrl().file)
        }

        return response
    }
}
