package com.kenilt.skeleton.managers.notification

import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal

/**
 * Created by neal on 4/5/17.
 */

class NotificationOpenedHandler : OneSignal.NotificationOpenedHandler {

    override fun notificationOpened(osNotificationOpenResult: OSNotificationOpenResult?) {
        val dataString = osNotificationOpenResult?.notification?.payload?.additionalData?.toString()

        if (dataString != null) {
            // TODO handle notification
        }
    }
}
