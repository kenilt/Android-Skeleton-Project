package com.kenilt.skeleton.model.vo

import com.kenilt.skeleton.model.response.ErrorResponse

/**
 * Created by thangnguyen on 2019-07-02.
 */
@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: ResourceStatus,
        val error: ErrorResponse? = null) {

    companion object {
        val LOADED = NetworkState(ResourceStatus.SUCCESS)
        val LOADING = NetworkState(ResourceStatus.LOADING)
        fun error(error: ErrorResponse? = null) = NetworkState(ResourceStatus.ERROR, error)
        fun exception(error: ErrorResponse? = null) = NetworkState(ResourceStatus.EXCEPTION, error)
    }
}
