package com.kenilt.skeleton.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import com.google.android.gms.instantapps.InstantApps
import com.kenilt.skeleton.LXApplication
import com.kenilt.skeleton.constant.Constant
import com.kenilt.skeleton.managers.helpers.ControllerHelper

/**
 * Created by thangnguyen on 4/3/19.
 */
object InstantAppUtil {
    fun openInstallPrompt(activity: Activity) {
        writeCookieApiData(activity, ControllerHelper.token())
        val postInstallIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yourdomain.com"))
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .putExtras(Bundle().apply {
                    putBoolean(Constant.KEY_SHOULD_MIGRATE_INSTANT_DATA, true)
                })
        val refererClass = activity::class.java.simpleName
        InstantApps.showInstallPrompt(activity, postInstallIntent, Constant.INSTALL_APP, refererClass)
    }

    private fun writeCookieApiData(activity: Activity, stringData: String) {
        val cookieContent = stringData.toByteArray(Charsets.UTF_8)

        with(InstantApps.getPackageManagerCompat(activity)) {
            if (cookieContent.size <= instantAppCookieMaxSize) {
                // Store the new cookie. To show cookieContent use: cookieContent.toString(Charset.defaultCharset())
                instantAppCookie = cookieContent
            } else {
//                (activity as? IGALogger)?.sendGAEvent("Tried to store too much information", "size/max = ${cookieContent.size}/$instantAppCookieMaxSize")
            }
        }
    }

    fun migrateCookieData(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // Only runs in an Installed App.
            if (!InstantApps.getPackageManagerCompat(activity).isInstantApp) {
                val token = readCookie(activity)
                if (token != null) {
                    val config = LXApplication.instance.config
                    config.putToken(token)
                    clearCookie(activity)
                }
            }
        }
    }

    private fun readCookie(activity: Activity) = InstantApps.getPackageManagerCompat(activity).instantAppCookie?.toString(Charsets.UTF_8)

    private fun clearCookie(activity: Activity) {
        InstantApps.getPackageManagerCompat(activity).instantAppCookie = null
    }
}
