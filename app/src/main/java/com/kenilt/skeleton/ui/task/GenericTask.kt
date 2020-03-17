package com.kenilt.skeleton.ui.task

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kenilt.skeleton.managers.AppExecutors
import com.kenilt.skeleton.model.vo.Resource
import com.kenilt.skeleton.model.vo.ResourceStatus
import com.kenilt.skeleton.repository.ServiceBoundResource
import retrofit2.Call
import javax.inject.Inject

abstract class GenericTask<Param, Response, Expect> {

    @Inject
    lateinit var appExecutors: AppExecutors

    private val requestParam = MutableLiveData<Param>()
    private val responseResult = Transformations.switchMap(requestParam) {
        ServiceBoundResource(appExecutors.networkIO()) { doNetworkJob(it) }.asLiveData()
    }

    val loadState = MutableLiveData<LoadState>()
    val taskResultState = MutableLiveData<TaskResultState>()
    val result = MediatorLiveData<Expect>()
    var errorMessages: List<String>? = null

    init {
        result.addSource(responseResult) {
            processAddToCartResult(it)
        }
    }

    private fun processAddToCartResult(resource: Resource<Response>) {
        when (resource.status) {
            ResourceStatus.LOADING -> loadState.value = LoadState.LOADING
            ResourceStatus.SUCCESS -> {
                loadState.value = LoadState.LOADED
                assignSuccessResult(resource)
                taskResultState.value = TaskResultState.TASK_RESULT_SUCCESS
            }
            ResourceStatus.ERROR -> {
                loadState.value = LoadState.LOADED
                errorMessages = resource.error?.errorList
                taskResultState.value = TaskResultState.TASK_RESULT_FAILED
            }
            ResourceStatus.EXCEPTION -> {
                loadState.value = LoadState.LOADED
                loadState.value = LoadState.NETWORK_ERROR
            }
        }
    }

    fun invokeTask(value: Param) {
        requestParam.postValue(value)
    }

    abstract fun doNetworkJob(value: Param): Call<Response>

    abstract fun assignSuccessResult(resource: Resource<Response>)
}
