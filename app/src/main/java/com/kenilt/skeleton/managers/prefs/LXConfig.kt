package com.kenilt.skeleton.managers.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * Created by thanh.nguyen on 11/15/16.
 */
class LXConfig(context: Context) {

    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val gson: Gson

    init {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()

        gson = Gson()
    }

    val token: String
        get() = sharedPreferences.getString(KEY_TOKEN, "") ?: ""

    val isTutorial: Boolean
        get() = sharedPreferences.getBoolean(KEY_TUTORIAL, true)

    val isKnewBuyLater: Boolean
        get() = sharedPreferences.getBoolean(KEY_KNEW_BUY_LATER, false)

    val shouldShowFirstPopUp: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_POP_UP, true)

    fun setShowFirstPopUp(isShow: Boolean) {
        editor.putBoolean(KEY_FIRST_POP_UP, isShow)
        editor.apply()
    }

    val shouldShowFirstSignInPopUp: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_SIGN_IN_POP_UP, true)

    fun setShowFirstSignInPopUp(isShow: Boolean) {
        editor.putBoolean(KEY_FIRST_SIGN_IN_POP_UP, isShow)
        editor.apply()
    }

    val oneSignalUserId: String
        get() = sharedPreferences.getString(KEY_ONE_SIGNAL_USER_ID, "") ?: ""

    val googleRegistrationId: String
        get() = sharedPreferences.getString(KEY_GOOGLE_REGISTRATION_ID, "") ?: ""

    /**
     * Deep link
     */

    fun putToken(token: String?) {
        if (token == null) {
            editor.remove(KEY_TOKEN)
            editor.apply()
            return
        }

        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun putTutorial(isShow: Boolean) {
        editor.putBoolean(KEY_TUTORIAL, isShow)
        editor.apply()
    }

    fun putKnewBuyLater(isKnew: Boolean) {
        editor.putBoolean(KEY_KNEW_BUY_LATER, isKnew)
        editor.apply()
    }

    fun putOneSignalUserId(id: String?) {
        if (id == null) {
            editor.remove(KEY_ONE_SIGNAL_USER_ID)
            editor.apply()
        }

        editor.putString(KEY_ONE_SIGNAL_USER_ID, id)
        editor.commit()
    }

    fun putGoogleRegistrationId(id: String?) {
        if (id == null) {
            editor.remove(KEY_GOOGLE_REGISTRATION_ID)
            editor.apply()
        }

        editor.putString(KEY_GOOGLE_REGISTRATION_ID, id)
        editor.commit()
    }

    fun getInstantAppOpenCount(): Int {
        return sharedPreferences.getInt(KEY_INSTANT_APP_OPEN_COUNT, 0)
    }

    fun putInstantAppOpenCount(count: Int) {
        editor.putInt(KEY_INSTANT_APP_OPEN_COUNT, count)
        editor.apply()
    }

    fun getLastCrashedTime(): Long {
        return sharedPreferences.getLong(KEY_LAST_CRASHED_TIME, 0)
    }

    fun putLastCrashedTime(time: Long) {
        editor.putLong(KEY_LAST_CRASHED_TIME, time)
        editor.apply()
    }

    fun getLikeCount(): Int {
        return sharedPreferences.getInt(KEY_LIKE_COUNT, 0)
    }

    fun putLikeCount(count: Int) {
        editor.putInt(KEY_LIKE_COUNT, count)
        editor.apply()
    }

    fun get5StarRatingCount(): Int {
        return sharedPreferences.getInt(KEY_5_STAR_RATING_COUNT, 0)
    }

    fun put5StarRatingCount(count: Int) {
        editor.putInt(KEY_5_STAR_RATING_COUNT, count)
        editor.apply()
    }

    fun getBadRatingCount(): Int {
        return sharedPreferences.getInt(KEY_BAD_RATING_COUNT, 0)
    }

    fun putBadRatingCount(count: Int) {
        editor.putInt(KEY_BAD_RATING_COUNT, count)
        editor.apply()
    }

    fun getShouldShowRating(): Boolean {
        return sharedPreferences.getBoolean(KEY_SHOULD_SHOW_RATING, true)
    }

    fun putShouldShowRating(canShow: Boolean) {
        editor.putBoolean(KEY_SHOULD_SHOW_RATING, canShow)
        editor.apply()
    }

    fun getLastRatingTime(): Long {
        return sharedPreferences.getLong(KEY_LAST_RATING_TIME, 0)
    }

    fun putLastRatingTime(time: Long) {
        editor.putLong(KEY_LAST_RATING_TIME, time)
        editor.apply()
    }

    fun getOpenAppCount(): Int {
        return sharedPreferences.getInt(KEY_OPEN_APP_COUNT, 0)
    }

    fun putOpenAppCount(count: Int) {
        editor.putInt(KEY_OPEN_APP_COUNT, count)
        editor.apply()
    }

    companion object {

        private const val NAME = "skeleton"

        private const val KEY_TOKEN = "token"
        private const val KEY_TUTORIAL = "tutorial"
        private const val KEY_FIRST_POP_UP = "first_pop_up"
        private const val KEY_FIRST_SIGN_IN_POP_UP = "first_sign_in_pop_up"
        private const val KEY_KNEW_BUY_LATER = "knew_buy_later"
        private const val KEY_INSTANT_APP_OPEN_COUNT = "instant_app_open_count"

        private const val KEY_ONE_SIGNAL_USER_ID = "one_signal_user_id"
        private const val KEY_GOOGLE_REGISTRATION_ID = "google_registration_id"

        private const val KEY_LAST_CRASHED_TIME = "last_crashed_time"
        private const val KEY_LIKE_COUNT = "like_count"
        private const val KEY_5_STAR_RATING_COUNT = "five_star_rating_count"
        private const val KEY_BAD_RATING_COUNT = "bad_rating_count"
        private const val KEY_SHOULD_SHOW_RATING = "should_show_rating"
        private const val KEY_LAST_RATING_TIME = "last_rating_time"
        private const val KEY_OPEN_APP_COUNT = "open_app_count"
    }

}
