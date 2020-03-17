package com.kenilt.skeleton.managers.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.kenilt.skeleton.utils.LXLog
import java.util.concurrent.CopyOnWriteArrayList

/**
 * https://gist.github.com/steveliles/11116937
 * Usage:
 *
 * 1. Get the Foreground Singleton, passing a Context or Application object unless you
 * are sure that the Singleton has definitely already been initialised elsewhere.
 *
 * 2.a) Perform a direct, synchronous check: Foreground.isForeground() / .isBackground()
 *
 * or
 *
 * 2.b) Register to be notified (useful in Service or other non-UI components):
 *
 * Foreground.Listener myListener = new Foreground.Listener(){
 * public void onBecameForeground(){
 * // ... whatever you want to do
 * }
 * public void onBecameBackground(){
 * // ... whatever you want to do
 * }
 * }
 *
 * public void onCreate(){
 * super.onCreate();
 * Foreground.get(this).addListener(listener);
 * }
 *
 * public void onDestroy(){
 * super.onCreate();
 * Foreground.get(this).removeListener(listener);
 * }
 */
class Foreground : Application.ActivityLifecycleCallbacks {

    var isForeground = false
        private set
    private var paused = true
    private val handler = Handler()
    private val listeners = CopyOnWriteArrayList<Listener>()
    private var check: Runnable? = null
    var currentActivity: Activity? = null
        private set

    val isBackground: Boolean
        get() = !isForeground

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    override fun onActivityResumed(activity: Activity?) {
        currentActivity = activity

        paused = false
        val wasBackground = !isForeground
        isForeground = true

        if (check != null)
            handler.removeCallbacks(check)

        if (wasBackground) {
            LXLog.i(TAG, "went foreground")
            for (l in listeners) {
                try {
                    l.onBecameForeground()
                } catch (exc: Exception) {
                    LXLog.e(TAG, "Listener threw exception!", exc)
                }
            }
        } else {
            LXLog.i(TAG, "still foreground")
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        paused = true

        if (check != null)
            handler.removeCallbacks(check)

        handler.postDelayed({
            if (isForeground && paused) {
                isForeground = false
                LXLog.i(TAG, "went background")
                for (l in listeners) {
                    try {
                        l.onBecameBackground()
                    } catch (exc: Exception) {
                        LXLog.e(TAG, "Listener threw exception!", exc)
                    }
                }
            } else {
                LXLog.i(TAG, "still foreground")
            }
        }, CHECK_DELAY)

        checkClearCurrentActivity(activity)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityDestroyed(activity: Activity?) {
        checkClearCurrentActivity(activity)
    }

    private fun checkClearCurrentActivity(activity: Activity?) {
        if (currentActivity != null && currentActivity == activity) {
            currentActivity = null
        }
    }

    companion object {

        const val CHECK_DELAY: Long = 500
        const val TAG: String = "Foreground"

        @SuppressLint("StaticFieldLeak")
        private var instance: Foreground? = null

        /**
         * Its not strictly necessary to use this method - _usually_ invoking
         * get with a Context gives us a path to retrieve the Application and
         * initialise, but sometimes (e.g. in test harness) the ApplicationContext
         * is != the Application, and the docs make no guarantees.
         *
         * @param application
         * @return an initialised Foreground instance
         */
        fun init(application: Application): Foreground {
            if (instance == null) {
                instance = Foreground()
                application.registerActivityLifecycleCallbacks(instance)
            }
            return instance as Foreground
        }

        fun get(application: Application): Foreground {
            if (instance == null) {
                init(application)
            }
            return instance as Foreground
        }

        fun get(ctx: Context): Foreground {
            if (instance == null) {
                val appCtx = ctx.applicationContext
                if (appCtx is Application) {
                    init(appCtx)
                }
                throw IllegalStateException(
                        "Foreground is not initialised and " + "cannot obtain the Application object")
            }
            return instance as Foreground
        }

        fun get(): Foreground {
            if (instance == null) {
                throw IllegalStateException(
                        "Foreground is not initialised - invoke " + "at least once with parameterised init/get")
            }
            return instance as Foreground
        }
    }

    interface Listener {
        fun onBecameForeground()
        fun onBecameBackground()
    }
}
