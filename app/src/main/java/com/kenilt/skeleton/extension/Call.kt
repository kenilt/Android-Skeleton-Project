package com.kenilt.skeleton.extension

import com.kenilt.skeleton.managers.call.RestCall
import retrofit2.Call

/**
 * Created by thangnguyen on 2019-07-03.
 */
fun <T> Call<T>.restCall(): RestCall<T> {
    return RestCall(this)
}
