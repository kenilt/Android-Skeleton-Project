package com.kenilt.skeleton.managers.call

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kenilt.skeleton.managers.interfaces.IOnlineReporter
import com.kenilt.skeleton.managers.managers.ReportManager
import com.kenilt.skeleton.model.response.ErrorResponse
import io.mockk.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeoutException

/**
 * Created by thangnguyen on 2019-09-25.
 */
class RestCallTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var restCall: RestCall<String>
    private val callBox: Call<String> = mockk(relaxed = true)
    private val onSuccess = spyk<(data: String?) -> Unit>()
    private val onApiError = spyk<(data: ErrorResponse?) -> Unit>()
    private val onException = spyk<(data: Throwable?) -> Unit>()
    private val onBefore = spyk<() -> Unit>()
    private val onDone = spyk<() -> Unit>()
    private val mockReporter: IOnlineReporter = mockk(relaxed = true)
    private val pathPrefix = " |  |"

    @Before
    fun setUp() {
        restCall = RestCall(callBox)
        restCall.onSuccess(onSuccess)
        restCall.onApiError(onApiError)
        restCall.onException(onException)
        restCall.onBefore(onBefore)
        restCall.onDone(onDone)

        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns mockReporter
    }

    @Test
    fun execute_whenSuccess() {
        val responseBox = String()
        every { callBox.execute() } returns Response.success(responseBox)
        restCall.execute()
        verify { onBefore.invoke() }
        verify { onSuccess.invoke(responseBox) }
        verify { onDone.invoke() }
    }

    @Test
    fun execute_whenApiError_withEmptyMessage() {
        every { callBox.execute() } returns Response.error(400,
                "".toResponseBody("application/json".toMediaTypeOrNull()))
        restCall.execute()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(null) }
        verify { onDone.invoke() }
    }

    @Test
    fun execute_whenApiError_withWrongFormatMessage() {
        every { callBox.execute() } returns Response.error(400,
                "<html/>".toResponseBody("application/json".toMediaTypeOrNull()))
        restCall.execute()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(null) }
        verify { onDone.invoke() }
        verify { mockReporter.sendThrowable("Cannot parse error body to ErrorResponse | $pathPrefix 400", any()) }
    }

    @Test
    fun execute_whenApiError_withResponse_code500() {
        val errorContent = "{ error = \"test\" }"
        every { callBox.execute() } returns Response.error(500,
                errorContent.toResponseBody("application/json".toMediaTypeOrNull()))
        restCall.execute()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(ErrorResponse(error = "test")) }
        verify { onDone.invoke() }
        verify { mockReporter.sendErrorMessage("$pathPrefix 500 | $errorContent <@thang>") }
    }

    @Test
    fun execute_whenApiError_withResponse_code422() {
        val errorContent = "{ error = \"test\" }"
        every { callBox.execute() } returns Response.error(422,
                errorContent.toResponseBody("application/json".toMediaTypeOrNull()))
        restCall.execute()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(ErrorResponse(error = "test")) }
        verify { onDone.invoke() }
        verify { mockReporter.sendLogMessage("$pathPrefix 422 | $errorContent") }
    }

    @Test
    fun execute_whenApiError_withResponse_code404() {
        val errorContent = "{ error = \"test\" }"
        every { callBox.execute() } returns Response.error(404,
                errorContent.toResponseBody("application/json".toMediaTypeOrNull()))
        restCall.execute()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(ErrorResponse(error = "test")) }
        verify { onDone.invoke() }
        verify { mockReporter.sendErrorMessage("$pathPrefix 404 | $errorContent") }
    }

    @Test
    fun execute_whenThrowTimeOutException() {
        val errorContent = "{ error = \"test\" }"
        every { callBox.execute() } throws TimeoutException(errorContent)
        restCall.execute()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(ErrorResponse(error = errorContent)) }
        verify { onDone.invoke() }
        verify { mockReporter.sendThrowable("An error happen when calling by retrofit |  | ", any()) }
    }

    @Test
    fun execute_whenThrowIOException() {
        val errorContent = "{ error = \"test\" }"
        val ioException = IOException(errorContent)
        every { callBox.execute() } throws ioException
        restCall.execute()
        verify { onBefore.invoke() }
        verify { onException.invoke(ioException) }
        verify { onDone.invoke() }
    }

    @Test
    fun enqueue_whenSuccess() {
        val responseBox = String()
        every { callBox.enqueue(any()) } answers {
            val callback = firstArg() as Callback<String>
            callback.onResponse(callBox, Response.success(responseBox))
        }
        restCall.enqueue()
        verify { onBefore.invoke() }
        verify { onSuccess.invoke(responseBox) }
        verify { onDone.invoke() }
    }

    @Test
    fun enqueue_whenApiError_withEmptyMessage() {
        every { callBox.enqueue(any()) } answers {
            val callback = firstArg() as Callback<String>
            callback.onResponse(callBox, Response.error(400,
                    "".toResponseBody("application/json".toMediaTypeOrNull())))
        }
        restCall.enqueue()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(null) }
        verify { onDone.invoke() }
    }

    @Test
    fun enqueue_whenApiError_withWrongFormatMessage() {
        every { callBox.enqueue(any()) } answers {
            val callback = firstArg() as Callback<String>
            callback.onResponse(callBox, Response.error(400,
                    "<html/>".toResponseBody("application/json".toMediaTypeOrNull())))
        }
        restCall.enqueue()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(null) }
        verify { onDone.invoke() }
        verify { mockReporter.sendThrowable("Cannot parse error body to ErrorResponse | $pathPrefix 400", any()) }
    }

    @Test
    fun enqueue_whenApiError_withResponse_code500() {
        val errorContent = "{ error = \"test\" }"
        every { callBox.enqueue(any()) } answers {
            val callback = firstArg() as Callback<String>
            callback.onResponse(callBox, Response.error(500,
                    errorContent.toResponseBody("application/json".toMediaTypeOrNull())))
        }
        restCall.enqueue()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(ErrorResponse(error = "test")) }
        verify { onDone.invoke() }
        verify { mockReporter.sendErrorMessage("$pathPrefix 500 | $errorContent <@thang>") }
    }

    @Test
    fun enqueue_whenApiError_withResponse_code422() {
        val errorContent = "{ error = \"test\" }"
        every { callBox.enqueue(any()) } answers {
            val callback = firstArg() as Callback<String>
            callback.onResponse(callBox, Response.error(422,
                    errorContent.toResponseBody("application/json".toMediaTypeOrNull())))
        }
        restCall.enqueue()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(ErrorResponse(error = "test")) }
        verify { onDone.invoke() }
        verify { mockReporter.sendLogMessage("$pathPrefix 422 | $errorContent") }
    }

    @Test
    fun enqueue_whenApiError_withResponse_code404() {
        val errorContent = "{ error = \"test\" }"
        every { callBox.enqueue(any()) } answers {
            val callback = firstArg() as Callback<String>
            callback.onResponse(callBox, Response.error(404,
                    errorContent.toResponseBody("application/json".toMediaTypeOrNull())))
        }
        restCall.enqueue()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(ErrorResponse(error = "test")) }
        verify { onDone.invoke() }
        verify { mockReporter.sendErrorMessage("$pathPrefix 404 | $errorContent") }
    }

    @Test
    fun enqueue_whenThrowTimeOutException() {
        val errorContent = "{ error = \"test\" }"
        every { callBox.enqueue(any()) } answers {
            val callback = firstArg() as Callback<String>
            callback.onFailure(callBox, TimeoutException(errorContent))
        }
        restCall.enqueue()
        verify { onBefore.invoke() }
        verify { onApiError.invoke(ErrorResponse(error = errorContent)) }
        verify { onDone.invoke() }
        verify { mockReporter.sendThrowable("An error happen when calling by retrofit |  | ", any()) }
    }

    @Test
    fun enqueue_whenThrowIOException() {
        val errorContent = "{ error = \"test\" }"
        val ioException = IOException(errorContent)
        every { callBox.enqueue(any()) } answers {
            val callback = firstArg() as Callback<String>
            callback.onFailure(callBox, ioException)
        }
        restCall.enqueue()
        verify { onBefore.invoke() }
        verify { onException.invoke(ioException) }
        verify { onDone.invoke() }
    }
}
