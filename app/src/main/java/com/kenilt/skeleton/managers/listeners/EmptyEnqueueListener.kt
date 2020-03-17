package com.kenilt.skeleton.managers.listeners

import retrofit2.Call

/**
 * Created by thangnguyen on 11/26/18.
 */
interface EmptyEnqueueListener<T>: EnqueueListener<T> {
    override fun onSuccess(call: Call<T>, data: T) {
        // do nothing
    }
}
