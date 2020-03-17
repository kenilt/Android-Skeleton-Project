package com.kenilt.skeleton.model.vo

import com.kenilt.skeleton.model.response.ErrorResponse
import com.kenilt.skeleton.model.vo.ResourceStatus.*

/**
 * Created by thangnguyen on 2019-06-21.
 */
data class Resource<out T>(val status: ResourceStatus, val data: T?, val error: ErrorResponse?) {
    companion object {
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(error: ErrorResponse? = null): Resource<T> {
            return Resource(ERROR, null, error)
        }

        fun <T> exception(): Resource<T> {
            return Resource(EXCEPTION, null, null)
        }
    }

    fun isSuccess(): Boolean {
        return status == SUCCESS
    }

    fun isLoading(): Boolean {
        return status == LOADING
    }

    fun extractNetworkState(): NetworkState {
        return when (status) {
            LOADING -> NetworkState.LOADING
            SUCCESS -> NetworkState.LOADED
            ERROR -> NetworkState.error(error)
            EXCEPTION -> NetworkState.exception(error)
        }
    }
}
