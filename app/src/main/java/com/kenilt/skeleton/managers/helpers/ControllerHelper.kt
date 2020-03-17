package com.kenilt.skeleton.managers.helpers

import com.kenilt.skeleton.managers.prefs.LXConfig
import org.json.JSONObject

/**
 * Created by thanh.nguyen on 1/9/2017.
 */

object ControllerHelper {

    private lateinit var lxConfig: LXConfig
    var branchJsonObject: JSONObject? = null
    var invitedReferralCode: String? = null
    var isIsNewRegisteredUser = false
    var isJustCreatedLixiSelfie = false

    fun init(config: LXConfig) {
        lxConfig = config
    }

    fun token(): String {
        return lxConfig.token
    }

    fun hasToken(): Boolean {
        return token().isNotEmpty()
    }

    fun isKnewBuyLater(): Boolean {
        return lxConfig.isKnewBuyLater
    }

    fun setKnewBuyLater(isKnew: Boolean) {
        lxConfig.putKnewBuyLater(isKnew)
    }

    fun shouldShowFirstPopUp(): Boolean {
        return lxConfig.shouldShowFirstPopUp
    }

    fun setShowFirstPopUp(isShow: Boolean) {
        lxConfig.setShowFirstPopUp(isShow)
    }

    fun shouldShowFirstSignInPopUp(): Boolean {
        return lxConfig.shouldShowFirstSignInPopUp
    }

    fun setShowFirstSignInPopUp(isShow: Boolean) {
        lxConfig.setShowFirstSignInPopUp(isShow)
    }

    fun oneSignalUserIdController(id: String) {
        lxConfig.putOneSignalUserId(id)
    }

    fun oneSignalUserId(): String {
        return lxConfig.oneSignalUserId
    }

    fun googleRegistrationId(): String {
        return lxConfig.googleRegistrationId
    }

    fun googleRegistrationIdController(id: String) {
        lxConfig.putGoogleRegistrationId(id)
    }

    fun getLastCrashedTime(): Long {
        return lxConfig.getLastCrashedTime()
    }

    fun getLikeCount(): Int {
        return lxConfig.getLikeCount()
    }

    fun increaseLikeCount() {
        lxConfig.putLikeCount(getLikeCount() + 1)
    }

    fun get5StarRatingCount(): Int {
        return lxConfig.get5StarRatingCount()
    }

    fun increase5StarRatingCount() {
        lxConfig.put5StarRatingCount(get5StarRatingCount() + 1)
    }

    fun getBadRatingCount(): Int {
        return lxConfig.getBadRatingCount()
    }

    fun increaseBadRatingCount() {
        lxConfig.putBadRatingCount(getBadRatingCount() + 1)
    }

    fun getShouldShowRating(): Boolean {
        return lxConfig.getShouldShowRating()
    }

    fun setShouldShowRating(canShow: Boolean) {
        lxConfig.putShouldShowRating(canShow)
    }

    fun getLastRatingTime(): Long {
        return lxConfig.getLastRatingTime()
    }

    fun putLastRatingTime(time: Long) {
        lxConfig.putLastRatingTime(time)
    }

    fun getOpenAppCount(): Int {
        return lxConfig.getOpenAppCount()
    }

    fun increaseOpenAppCount() {
        lxConfig.putOpenAppCount(getOpenAppCount() + 1)
    }
}
