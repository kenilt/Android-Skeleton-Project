package com.kenilt.mock

import com.kenilt.helper.AssetsHelper
import com.kenilt.skeleton.extension.isNotNullNorEmpty
import com.kenilt.skeleton.extension.lxFilePath
import com.kenilt.skeleton.model.CustomInfo
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import java.util.concurrent.TimeUnit

/**
 * Created by thangnguyen on 12/13/18.
 */
open class MockDispatcher: Dispatcher() {
    protected var delayTime: Long = DEFAULT_DELAY_TIME
    protected var customPaths = HashMap<String, CustomInfo>()

    override fun dispatch(request: RecordedRequest): MockResponse {
//        Log.d("Kenilt", "query " + request?.requestUrl?.query())
//        Log.d("Kenilt", "param " +  request?.requestUrl?.queryParameter("address_last_updated_at"))
        val mockResponse = MockResponse()
                .throttleBody(1024*1024, 1, TimeUnit.SECONDS)
                .setHeadersDelay(delayTime, TimeUnit.MILLISECONDS)
                .setBodyDelay(delayTime, TimeUnit.MILLISECONDS)
                .setResponseCode(200)

        processMockResponse(request, mockResponse)

        return mockResponse
    }

    private fun processMockResponse(request: RecordedRequest?, mockResponse: MockResponse) {
        val path = request?.requestUrl?.lxFilePath(request.method) ?: ""
        when {
            customPaths.contains(path) -> processCustomCase(mockResponse, path, customPaths[path])
            else -> processNormalCase(mockResponse, path)
        }
    }

    open fun processNormalCase(mockResponse: MockResponse, path: String?) {
        val jsonString = AssetsHelper.readJsonFile("$path.json")
        if (jsonString.isNotEmpty()) {
            mockResponse.setBody(jsonString)
        } else {
            mockResponse.setResponseCode(404)
            mockResponse.setBody(AssetsHelper.readJsonFile("errors/error.json"))
        }
    }

    open fun processCustomCase(mockResponse: MockResponse, path: String, customInfo: CustomInfo?) {
        if (customInfo == null) return

        if (customInfo.code > 0) {
            mockResponse.setResponseCode(customInfo.code)

            // json response
            val jsonString = customInfo.json ?: if (customInfo.jsonPath.isNotNullNorEmpty()) {
                AssetsHelper.readJsonFile(customInfo.jsonPath)
            } else {
                AssetsHelper.readJsonFile("$path.json")
            }
            mockResponse.setBody(jsonString)

            // slow response
            customInfo.slowInfo?.let { slowInfo ->
                mockResponse.throttleBody(slowInfo.bytesPerSecond, 1, TimeUnit.SECONDS)
                        .setHeadersDelay(slowInfo.delayTime, TimeUnit.MILLISECONDS)
                        .setBodyDelay(delayTime, TimeUnit.MILLISECONDS)
            }
        } else {
            mockResponse.socketPolicy = SocketPolicy.FAIL_HANDSHAKE
        }

        if (customInfo.executeTime == 1) {
            customPaths.remove(path)
        } else {
            customInfo.executeTime--
        }
    }

    fun addSlowPath(path: String) {
        customPaths[path] = CustomInfo().slow()
    }

    fun addErrorPath(path: String) {
        customPaths[path] = CustomInfo().error()
    }

    fun addExceptionPath(path: String) {
        customPaths[path] = CustomInfo().exception()
    }

    fun addCustomPath(path: String, customInfo: CustomInfo) {
        customPaths[path] = customInfo
    }

    fun clearCustomPath(path: String) {
        customPaths.remove(path)
    }

    fun clearAllCustomPaths() {
        customPaths.clear()
    }

    companion object {
        const val DEFAULT_DELAY_TIME = 100L
    }
}
