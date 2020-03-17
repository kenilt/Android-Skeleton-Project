package com.kenilt.skeleton.managers.recycler

import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.managers.interfaces.IPageable
import com.kenilt.skeleton.managers.listeners.EnqueueListener
import com.kenilt.skeleton.model.response.ErrorResponse
import retrofit2.Call

/**
 * Created by thangnguyen on 3/7/19.
 */
abstract class RecyclerPresenter<T: IModel>: IRecyclerPresenter<T> {
    var recyclerHelper: IRecyclerHelper<T>? = null
    var page: Int = 1
    var failedCount = 0
    var calls = ArrayList<Call<*>>()

    override fun onRefreshData() {
        page = 1
        callApiDataWithType(true)
    }

    override fun callApiDataWithType(isRefresh: Boolean) {
        val call = getApiCallWithType(isRefresh) ?: return

        calls.add(call)
        call.enqueue(object: EnqueueListener<IPageable<T>> {
            override fun onBefore() {
                if (isRefresh) {
                    recyclerHelper?.setRefreshing(false)
                }
                calls.remove(call as Call<*>)
            }

            override fun onSuccess(call: Call<IPageable<T>>, data: IPageable<T>) {
                failedCount = 0
                onHandleGotResponseWithType(data, isRefresh)
            }

            override fun onApiError(call: Call<IPageable<T>>, errorResponse: ErrorResponse?) {
                handleOnApiError(errorResponse)
            }

            override fun onThrowable(call: Call<IPageable<T>>, t: Throwable) {
                handleOnFailure(isRefresh)
            }
        })
    }

    open fun onHandleGotResponseWithType(response: IPageable<T>?, isRefresh: Boolean) {
        val dataModelList = response?.getDataList()
        dataModelList?.let {
            onHandleRelatedInfo(response, isRefresh)
            recyclerHelper?.setDataListForAdapter(dataModelList, isRefresh)

            val isLoadMore = response.hasNextPage()
            recyclerHelper?.setEnded(!isLoadMore)
            page += 1
        }
    }

    open fun handleOnApiError(errorResponse: ErrorResponse?) {}

    fun handleOnFailure(isRefresh: Boolean) {
        failedCount++
        onHandleFailureRelatedInfo(isRefresh)
        if (!isRefresh && failedCount < 2) {
            callApiDataWithType(false)
        }
    }

    open fun onHandleRelatedInfo(response: IPageable<T>, refresh: Boolean) { }

    open fun onHandleFailureRelatedInfo(refresh: Boolean) { }

    fun stopAllRequest() {
        for (i in calls.size - 1 downTo 0) {
            val call = calls[i]
            call.cancel()
            calls.remove(call)
        }
    }
}
