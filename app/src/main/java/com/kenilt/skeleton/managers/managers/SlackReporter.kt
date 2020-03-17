package com.kenilt.skeleton.managers.managers

import com.google.gson.GsonBuilder
import com.kenilt.skeleton.BuildConfig
import com.kenilt.skeleton.LXApplication
import com.kenilt.skeleton.api.service.slack.SlackService
import com.kenilt.skeleton.constant.Constant
import com.kenilt.skeleton.constant.Skeleton
import com.kenilt.skeleton.extension.format
import com.kenilt.skeleton.managers.helpers.Logout
import com.kenilt.skeleton.managers.interfaces.IOnlineReporter
import com.kenilt.skeleton.model.`object`.slack.SlackMessage
import com.kenilt.skeleton.utils.LXLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SlackReporter: IOnlineReporter {
    private val slackService: SlackService by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl(HOOK_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        retrofit.create(SlackService::class.java)
    }

    override fun sendLogMessage(message: String) {
        if (message.isEmpty()) return
        val modifiedMessage = ":round_pushpin:${BuildConfig.VERSION_NAME} [${Logout.getNearestClassNameMethodAndLineNumber()}] :sunglasses: ${getUserEmail()} | $message"
        val slackMessage = SlackMessage(modifiedMessage)
        sendMessage(slackMessage)
    }

    override fun sendTimeResponseMessage(responseTime: Long, uuid: String, path: String) {
        var timeString = (responseTime / 1000f).format(3) + "s"
        if (responseTime > Constant.VERY_SLOW_TIME_LEVEL) {
            timeString += " *[very slow]*"
        } else if (responseTime > Constant.SLOWER_TIME_LEVEL) {
            timeString += " *[slow]*"
        }
        val modifiedMessage = ":point_right: $timeString :dolphin: $uuid :sunglasses: ${getUserEmail()}:round_pushpin:$path"
        val slackMessage = SlackMessage(modifiedMessage)
        sendMessage(slackMessage)
    }

    override fun sendErrorMessage(message: String) {
        if (message.isEmpty()) return
        val modifiedMessage = ":boom: ${BuildConfig.VERSION_NAME} [${Logout.getNearestClassNameMethodAndLineNumber()}] :sunglasses: ${getUserEmail()} | $message"
        val slackMessage = SlackMessage(modifiedMessage)
        sendMessage(slackMessage, Skeleton.IS_PRODUCTION_RELEASE)
    }

    override fun sendThrowable(message: String, throwable: Throwable) {
        val modifiedMessage = " :triangular_flag_on_post: ${BuildConfig.VERSION_NAME} [${Logout.getNearestClassNameMethodAndLineNumber()}]:round_pushpin:$message $throwable"
        val slackMessage = SlackMessage(modifiedMessage)
        sendMessage(slackMessage, Skeleton.IS_PRODUCTION_RELEASE)
    }

    private fun sendMessage(slackMessage: SlackMessage, isImportant: Boolean = false) {
        if (LXApplication.instance.isInstantApp) {
            slackMessage.text = "AIA: " + slackMessage.text
        }
        if (BuildConfig.DEBUG) {
            slackMessage.text = "Debug -> " + slackMessage.text
        }
        val resultCallback = object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                LXLog.e(TAG, t.toString())
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    LXLog.e(TAG, response.toString())
                }
            }
        }
        if (isImportant) {
            slackService.sendErrorMessage(slackMessage).enqueue(resultCallback)
        } else {
            slackService.sendLogMessage(slackMessage).enqueue(resultCallback)
        }
    }

    private fun getUserEmail(): String {
        // TODO return user email
        return "no_email"
    }

    companion object {
        private const val TAG = "SlackReporter"
        private const val HOOK_URL = "https://hooks.slack.com/services/"
    }
}
