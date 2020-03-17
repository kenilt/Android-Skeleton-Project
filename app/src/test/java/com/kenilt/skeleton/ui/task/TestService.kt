package com.kenilt.skeleton.ui.task

import retrofit2.Call
import retrofit2.http.POST

/**
 * Created by thangnguyen on 3/17/20.
 */
interface TestService {
    @POST("XXXXX-your-hook-token")
    fun testApi(): Call<List<String>>
}
