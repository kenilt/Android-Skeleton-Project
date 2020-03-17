package com.kenilt.skeleton.ui.base.paged

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.kenilt.skeleton.extension.restCall
import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.managers.interfaces.IPageable
import com.kenilt.skeleton.managers.interfaces.IPagedMapper
import com.kenilt.skeleton.model.vo.NetworkState
import com.kenilt.skeleton.model.vo.PagedCached
import retrofit2.Call
import java.util.concurrent.Executor

/**
 * Created by thangnguyen on 2019-08-23.
 */
abstract class BasePagedKeyDataSource<Value: IModel, Remote>(
        private val retryExecutor: Executor,
        var pagedCached: PagedCached<Value>?,
        private var mapper: IPagedMapper<List<Value>, IPageable<Remote>>? = null
): PageKeyedDataSource<Int, Value>() {

    // keep a function reference for the retry event
    @VisibleForTesting
    var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Value>) {
        if (pagedCached?.hasCachedData() == true) {
            val result = ArrayList<Value>().apply { pagedCached?.cachedItems?.let { addAll(it) } }
            callback.onResult(result, null, pagedCached?.cachedNextKey)
            return
        }
        networkState.postValue(NetworkState.LOADING)
        getApiCall(1).restCall()
                .onSuccess { data ->
                    processLoadInitialSuccessData(data, callback)
                }
                .onApiError { errorResponse ->
                    retry = {
                        loadInitial(params, callback)
                    }
                    networkState.postValue(NetworkState.error(errorResponse))
                }
                .onException {
                    retry = {
                        loadInitial(params, callback)
                    }
                    networkState.postValue(NetworkState.exception())
                }
                .onDone {
                    initialLoad.postValue(NetworkState.LOADED)
                }
                .execute()
    }

    open fun processLoadInitialSuccessData(data: IPageable<Remote>?, callback: LoadInitialCallback<Int, Value>) {
        retry = null
        val items = getDataListFromData(data) ?: emptyList()
        val nextPage = if (data?.hasNextPage() == true) 2 else null
        callback.onResult(items, null, nextPage)
        networkState.postValue(NetworkState.LOADED)
        pagedCached?.cacheInitialData(items, nextPage)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {
        // do nothing, because we only append dater after the first load
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {
        networkState.postValue(NetworkState.LOADING)
        getApiCall(params.key).restCall()
                .onSuccess { data ->
                    retry = null
                    val items = getDataListFromData(data) ?: emptyList()
                    val nextPage = if (data?.hasNextPage() == true) params.key + 1 else null
                    callback.onResult(items, nextPage)
                    networkState.postValue(NetworkState.LOADED)
                    pagedCached?.cacheData(items, nextPage)
                }
                .onApiError {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.error(it))
                }
                .onException {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.exception())
                }
                .execute()
    }

    abstract fun getApiCall(key: Int): Call<IPageable<Remote>>

    @Suppress("UNCHECKED_CAST")
    open fun getDataListFromData(data: IPageable<Remote>?): List<Value>? {
        return mapper?.convert(data) ?: data?.getDataList() as? List<Value>
    }
}
