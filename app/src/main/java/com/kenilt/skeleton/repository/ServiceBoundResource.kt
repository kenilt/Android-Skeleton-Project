package com.kenilt.skeleton.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kenilt.skeleton.extension.restCall
import com.kenilt.skeleton.model.vo.Resource
import retrofit2.Call
import java.util.concurrent.Executor

class ServiceBoundResource<T>(var executor: Executor, var getCall: () -> Call<T>) {

    private val result = MutableLiveData<Resource<T>>()

    init {
        result.value = Resource.loading()
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        executor.execute {
            getCall.invoke().restCall()
                    .onSuccess { result.postValue(Resource.success(it)) }
                    .onApiError { result.postValue(Resource.error(it)) }
                    .onException { result.postValue(Resource.exception()) }
                    .execute()
        }
    }

    fun asLiveData() = result as LiveData<Resource<T>>
}
