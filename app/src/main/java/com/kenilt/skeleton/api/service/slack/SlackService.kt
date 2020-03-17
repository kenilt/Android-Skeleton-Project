package com.kenilt.skeleton.api.service.slack

import com.kenilt.skeleton.model.`object`.slack.SlackMessage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SlackService {
    @POST("XXXXX-your-hook-token")
    fun sendLogMessage(@Body message: SlackMessage): Call<Void>

    @POST("XXXXX-your-hook-token")
    fun sendErrorMessage(@Body message: SlackMessage): Call<Void>
}
