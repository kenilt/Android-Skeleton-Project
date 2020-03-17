package com.kenilt.skeleton.managers.notification

import com.onesignal.NotificationExtenderService
import com.onesignal.OSNotificationReceivedResult

/**
 * Created by neal on 4/5/17.
 */

class NotificationExtender : NotificationExtenderService() {

    override fun onNotificationProcessing(osNotificationReceivedResult: OSNotificationReceivedResult): Boolean {
        return false
    }
}
