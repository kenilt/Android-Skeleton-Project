package com.kenilt.skeleton.ui.task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.kenilt.skeleton.constant.TestConstant
import com.kenilt.skeleton.extension.assertListContent
import com.kenilt.skeleton.extension.restCall
import com.kenilt.skeleton.managers.call.RestCall
import com.kenilt.skeleton.managers.helpers.ControllerHelper
import com.kenilt.skeleton.managers.managers.ReportManager
import com.kenilt.skeleton.managers.prefs.LXConfig
import com.kenilt.skeleton.model.vo.Resource
import com.kenilt.util.InstantAppExecutors
import io.mockk.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.IOException

class GenericTaskTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var targetTask: GenericTask<Int, List<String>, List<String>>
    private val testService = mockk<TestService>()

    @Before
    fun setUp() {
        targetTask = object : GenericTask<Int, List<String>, List<String>>() {
            override fun doNetworkJob(value: Int): Call<List<String>> {
                return testService.testApi()
            }

            override fun assignSuccessResult(resource: Resource<List<String>>) {
                resource.data?.let {
                    result.value = it
                }
            }
        }
        targetTask.appExecutors = InstantAppExecutors()

        val config = mockk<LXConfig>()
        ControllerHelper.init(config)
        every { ControllerHelper.token() } returns TestConstant.VALID_TOKEN
    }

    @Test
    fun invokeTask_loading() {
        mockkStatic("com.kenilt.skeleton.extension.CallKt")
        val fakeCall = mockk<Call<List<String>>>()
        val restCall = object : RestCall<List<String>>(fakeCall) {
            override fun execute() {
                // do nothing
            }
        }
        every { testService.testApi() } returns fakeCall
        every { fakeCall.restCall() } returns restCall
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADING)
        stateObserver.assertNoValue()
        targetObserver.assertNoValue()
    }

    @Test
    fun invokeTask_whenSuccess() {
        val targetResponse = listOf("")
        val response = targetResponse
        every { testService.testApi() } returns Calls.response(Response.success(response))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_SUCCESS)
        targetObserver.assertValue(targetResponse)
    }

    @Test
    fun invokeTask_whenSuccessButNoData() {
        val response = listOf("")
        every { testService.testApi() } returns Calls.response(Response.success(response))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_SUCCESS)
        targetObserver.assertValue(response)
    }

    @Test
    fun invokeTask_whenFail_with400() {
        every { testService.testApi() } returns Calls.response(
                Response.error(400,
                        "".toResponseBody("application/json".toMediaTypeOrNull())))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_FAILED)
        targetObserver.assertNoValue()
    }

    @Test
    fun invokeTask_whenFail_with401() {
        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns spyk()
        every { testService.testApi() } returns Calls.response(
                Response.error(401,
                        "{\"error\":\"Access token is invalid!\"}".toResponseBody("application/json".toMediaTypeOrNull())))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_FAILED)
        assertListContent("Access token is invalid!", list = targetTask.errorMessages)
        targetObserver.assertNoValue()
    }

    @Test
    fun invokeTask_whenFail_with404() {
        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns spyk()
        every { testService.testApi() } returns Calls.response(
                Response.error(404,
                        "{\"error\":\"Not found record!\"}".toResponseBody("application/json".toMediaTypeOrNull())))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_FAILED)
        assertListContent("Not found record!", list = targetTask.errorMessages)
        targetObserver.assertNoValue()
    }

    @Test
    fun invokeTask_whenFail_with422() {
        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns spyk()
        every { testService.testApi() } returns Calls.response(
                Response.error(422,
                        "{\"error\":\"test error\"}".toResponseBody("application/json".toMediaTypeOrNull())))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_FAILED)
        assertListContent("test error", list = targetTask.errorMessages)
        targetObserver.assertNoValue()
    }

    @Test
    fun invokeTask_whenFail_with500() {
        every { testService.testApi() } returns Calls.response(
                Response.error(500,
                        "".toResponseBody("application/json".toMediaTypeOrNull())))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_FAILED)
        assertTrue(targetTask.errorMessages.isNullOrEmpty())
        targetObserver.assertNoValue()
    }

    @Test
    fun invokeTask_whenFail_with502() {
        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns spyk()
        every { testService.testApi() } returns Calls.response(
                Response.error(502,
                        "<!DOCTYPE html>".toResponseBody("application/json".toMediaTypeOrNull())))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_FAILED)
        assertTrue(targetTask.errorMessages.isNullOrEmpty())
        targetObserver.assertNoValue()
    }

    @Test
    fun invokeTask_whenFail_with504() {
        mockkObject(ReportManager)
        every { ReportManager.getReporter() } returns spyk()
        every { testService.testApi() } returns Calls.response(
                Response.error(504,
                        "<!DOCTYPE html>".toResponseBody("application/json".toMediaTypeOrNull())))
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.LOADED)
        stateObserver.assertValue(TaskResultState.TASK_RESULT_FAILED)
        assertTrue(targetTask.errorMessages.isNullOrEmpty())
        targetObserver.assertNoValue()
    }

    @Test
    fun invokeTask_whenException() {
        every { testService.testApi() } returns Calls.failure(IOException())
        val loadObserver = targetTask.loadState.test()
        val stateObserver = targetTask.taskResultState.test()
        val targetObserver = targetTask.result.test()
        targetTask.invokeTask(1)
        loadObserver.assertValue(LoadState.NETWORK_ERROR)
        stateObserver.assertNoValue()
        targetObserver.assertNoValue()
    }
}
