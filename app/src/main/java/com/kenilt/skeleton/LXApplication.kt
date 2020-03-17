package com.kenilt.skeleton

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import androidx.fragment.app.FragmentActivity
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.android.gms.common.wrappers.InstantApps
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.kenilt.skeleton.api.restclient.RestClient
import com.kenilt.skeleton.constant.Constant
import com.kenilt.skeleton.di.AppInjector
import com.kenilt.skeleton.extension.isNotNullNorEmpty
import com.kenilt.skeleton.managers.common.Foreground
import com.kenilt.skeleton.managers.helpers.ControllerHelper
import com.kenilt.skeleton.managers.helpers.LXPicasso
import com.kenilt.skeleton.managers.notification.NotificationOpenedHandler
import com.kenilt.skeleton.managers.notification.NotificationReceivedHandler
import com.kenilt.skeleton.managers.prefs.LXConfig
import com.kenilt.skeleton.managers.prefs.UTMPrefs
import com.kenilt.skeleton.ui.custom.dialog.InstallRecommendationDialog
import com.kenilt.skeleton.utils.DeviceUtil
import com.kenilt.skeleton.utils.Installation
import com.kenilt.skeleton.utils.InstantClock
import com.kenilt.skeleton.utils.LXLog
import com.onesignal.OneSignal
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.branch.referral.Branch
import io.fabric.sdk.android.Fabric
import io.fabric.sdk.android.InitializationCallback
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * Created by thanh.nguyen on 11/15/16.
 */

open class LXApplication : DaggerApplication() {

    @Inject
    lateinit var config: LXConfig
    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics
    @Inject
    lateinit var retrofit: Retrofit
    val deviceId: String by lazy {
        var deviceId = DeviceUtil.androidId(this)
        if (deviceId == null || deviceId.isEmpty()) {
            deviceId = Installation.id(this)
        }
        deviceId ?: ""
    }
    val isInstantApp: Boolean by lazy {
        InstantApps.isInstantApp(this)
    }
    private lateinit var foreground: Foreground
    var isAppUsed = false
    var isOpenFromNotification = false
    private var mDefaultUEH: Thread.UncaughtExceptionHandler? = null
    private val mCaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread, ex ->
        // Custom logic goes here
        config.putLastCrashedTime(InstantClock.currentTimeMillis())

        // This will make Crashlytics do its job
        mDefaultUEH?.uncaughtException(thread, ex)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return AppInjector.init(this)
    }

    override fun onCreate() {
        if (!isInstantApp && MissingSplitsManagerFactory.create(this).disableAppIfMissingRequiredSplits()) {
            // Skip app initialization.
            return
        }

        super.onCreate()
        instance = this

        // inject config for ControllerHelper
        ControllerHelper.init(config)

        // Create retrofit api
        RestClient.init(retrofit)

        // Fabric
        initCrashlytics()

        // Foreground
        initForeground()

        // OneSignal
        registerOneSignal()

        // Branch
        Branch.getAutoInstance(this)

        LXPicasso.init(this)
        UTMPrefs(this).loadUTMId()

        checkInstantAppOpenCount()

        initFirebaseAnalyticForAppType()

        ControllerHelper.increaseOpenAppCount()

        LXLog.d("LXApplication", "versionName: ${BuildConfig.VERSION_NAME}, versionCode: ${BuildConfig.VERSION_CODE}")
    }

    // Tutorial from here: https://stackoverflow.com/a/49266303/2347872
    private fun initCrashlytics() {
        val core = CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build()
        Fabric.with(Fabric.Builder(this).kits(Crashlytics.Builder()
                .core(core)
                .build())
                .initializationCallback(object : InitializationCallback<Fabric?> {
                    override fun success(fabric: Fabric?) {
                        mDefaultUEH = Thread.getDefaultUncaughtExceptionHandler()
                        Thread.setDefaultUncaughtExceptionHandler(mCaughtExceptionHandler)
                    }

                    override fun failure(e: Exception) {}
                })
                .build())
    }

    private fun initForeground() {
        foreground = Foreground.get(this)
        foreground.addListener(object : Foreground.Listener {
            override fun onBecameForeground() {
//                sendGAEvent("App became foreground", "${foreground.currentActivity?.javaClass?.simpleName}")
            }

            override fun onBecameBackground() {
//                sendGAEvent("App became background")
            }
        })
    }

    private fun registerOneSignal() {
        if (isInstantApp) { return }

        OneSignal.startInit(this)
                .setNotificationOpenedHandler(NotificationOpenedHandler())
                .setNotificationReceivedHandler(NotificationReceivedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

        OneSignal.idsAvailable { userId, registrationId ->
            if (userId.isNotNullNorEmpty()) {
                ControllerHelper.oneSignalUserIdController(userId)
            }
            if (registrationId.isNotNullNorEmpty()) {
                ControllerHelper.googleRegistrationIdController(registrationId)
            }
        }
    }

    private fun initFirebaseAnalyticForAppType() {
        val appTypeStatus = if (isInstantApp) Constant.STATUS_INSTANT else Constant.STATUS_INSTALLED
        firebaseAnalytics.setUserProperty(Constant.ANALYTICS_APP_PROP, appTypeStatus)
    }

    fun showRecommendInstallDialog() {
        val activity = currentActivity
        if (activity is FragmentActivity) {
            if (isInstantApp && !activity.isFinishing) {
                InstallRecommendationDialog.show(activity)
            }
        }
    }

    private fun checkInstantAppOpenCount() {
        if (!isInstantApp) { return }
        val count = config.getInstantAppOpenCount()
        val numberBeforeAsk = Constant.OPEN_INSTANT_APP_COUNT_BEFORE_ASK
        if (count == numberBeforeAsk) {
            Handler().postDelayed({
                showRecommendInstallDialog()
            }, 1000)
        }
        if (count <= numberBeforeAsk) {
            config.putInstantAppOpenCount(count + 1)
        }
    }

    val currentActivity: Activity?
        get() = foreground.currentActivity

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: LXApplication
            private set
    }
}
