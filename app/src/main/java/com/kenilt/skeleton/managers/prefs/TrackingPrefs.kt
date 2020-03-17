package com.kenilt.skeleton.managers.prefs

import android.content.Context

/**
 * Created by thangnguyen on 12/21/18.
 */
class TrackingPrefs(context: Context) : BasePreferences() {
    init {
        super.init(context, TRACKING_PREFS)
    }

    companion object {
        private const val TRACKING_PREFS = "tracking_prefs"
    }
}
