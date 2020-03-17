package com.kenilt.skeleton.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.kenilt.skeleton.managers.managers.ReportManager
import com.kenilt.skeleton.model.response.ErrorResponse
import com.kenilt.skeleton.model.vo.Resource
import com.kenilt.util.InstantExecutor
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.spyk
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.IOException

class ServiceBoundResourceTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val executor = InstantExecutor()

    @Before
    fun setUp() {

    }

    @Test
    fun whenSuccess_returnSuccessData() {
        val box = String()
        val result = ServiceBoundResource<String>(executor) {
            Calls.response(Response.success(box))
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.success(box))
    }

    @Test
    fun whenFailCause400_returnError() {
        val result = ServiceBoundResource<String>(executor) {
            Calls.response(
                    Response.error(400,
                            "".toResponseBody("application/json".toMediaTypeOrNull())))
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.error(null))
    }

    @Test
    fun whenFailCause401_returnError() {
        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns spyk()
        val result = ServiceBoundResource<String>(executor) {
            Calls.response(
                    Response.error(401,
                            "{\"error\":\"Access token is invalid!\"}".toResponseBody("application/json".toMediaTypeOrNull())))
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.error(ErrorResponse(error = "Access token is invalid!")))
    }

    @Test
    fun whenFailCause404_returnError() {
        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns spyk()
        val result = ServiceBoundResource<String>(executor) {
            Calls.response(
                    Response.error(404,
                            "{\"error\":\"Not found record!\"}".toResponseBody("application/json".toMediaTypeOrNull())))
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.error(ErrorResponse(error = "Not found record!")))
    }

    @Test
    fun whenFailCause422_returnError() {
        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns spyk()
        val result = ServiceBoundResource<String>(executor) {
            Calls.response(
                    Response.error(422,
                            "{\"error\":\"test error\"}".toResponseBody("application/json".toMediaTypeOrNull())))
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.error(ErrorResponse(error = "test error")))
    }

    @Test
    fun whenFailCause500_returnError() {
        val result = ServiceBoundResource<String>(executor) {
            Calls.response(
                    Response.error(500,
                            "".toResponseBody("application/json".toMediaTypeOrNull())))
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.error(null))
    }

    @Test
    fun whenFailCause502_returnError() {
        val result = ServiceBoundResource<String>(executor) {
            Calls.response(
                    Response.error(502,
                            "<!DOCTYPE html>".toResponseBody("application/json".toMediaTypeOrNull())))
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.error(null))
    }

    @Test
    fun whenFailCause504_returnError() {
        val result = ServiceBoundResource<String>(executor) {
            Calls.response(
                    Response.error(504,
                            "<!DOCTYPE html>".toResponseBody("application/json".toMediaTypeOrNull())))
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.error(null))
    }

    @Test
    fun whenFailCauseIOException_returnException() {
        val result = ServiceBoundResource<String>(executor) {
            Calls.failure(IOException())
        }.asLiveData()
        val observer = result.test()
        observer.assertValue(Resource.exception())
    }
}
