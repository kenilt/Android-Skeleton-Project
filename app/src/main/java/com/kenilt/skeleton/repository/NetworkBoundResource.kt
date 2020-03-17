package com.kenilt.skeleton.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kenilt.skeleton.extension.restCall
import com.kenilt.skeleton.model.vo.Resource
import retrofit2.Call
import java.util.concurrent.Executor

/**
 * Created by thangnguyen on 2019-06-25.
 */
abstract class NetworkBoundResource<T>(var executor: Executor) {

    private val result = MutableLiveData<Resource<T>>()

    init {
        result.value = Resource.loading()
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        executor.execute {
            getCall().restCall()
                    .onSuccess { result.postValue(Resource.success(it)) }
                    .onApiError { result.postValue(Resource.error(it)) }
                    .onException { result.postValue(Resource.exception()) }
                    .execute()
        }
    }

    abstract fun getCall(): Call<T>

    fun asLiveData() = result as LiveData<Resource<T>>
}
