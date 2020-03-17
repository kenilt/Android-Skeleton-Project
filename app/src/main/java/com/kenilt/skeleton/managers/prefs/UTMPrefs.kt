package com.kenilt.skeleton.managers.prefs

import android.content.Context
import com.kenilt.skeleton.extension.toNumberOfDays
import javax.inject.Inject

/**
 * Created by thangnguyen on 2/19/19.
 */
class UTMPrefs @Inject constructor(context: Context) : BasePreferences() {
    init {
        super.init(context, UTM_PREFS)
    }

    companion object {
        private const val UTM_PREFS = "utm_prefs"
        const val UTM_ID_KEY = "utm_id"
        const val UTM_CREATED_AT = "utm_created_at"
        const val UTM_CAMPAIGN_KEY = "utm_campaign"
        const val UTM_MEDIUM_KEY = "utm_medium"
        const val UTM_SOURCE_KEY = "utm_source"

        var utmId: String? = null
    }

    fun putUTMId(id: String?) {
        if (id == null) {
            removeKey(UTM_ID_KEY)
            removeKey(UTM_CREATED_AT)
            utmId = null
        } else {
            putString(UTM_ID_KEY, id)
            putLong(UTM_CREATED_AT, System.currentTimeMillis() / 1000)
            utmId = id
        }
    }

    fun loadUTMId() {
        val now = System.currentTimeMillis() / 1000
        val createdAt = getLong(UTM_CREATED_AT, now)
        val numberOfDays = (now - createdAt).toNumberOfDays()
        if (numberOfDays <= 30) {
            utmId = getString(UTM_ID_KEY)
        } else {
            utmId = null
            removeKey(UTM_ID_KEY)
            removeKey(UTM_CREATED_AT)
        }
    }
}
