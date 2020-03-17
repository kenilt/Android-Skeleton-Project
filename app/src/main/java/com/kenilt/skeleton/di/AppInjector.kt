package com.kenilt.skeleton.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.kenilt.skeleton.LXApplication
import com.kenilt.skeleton.R
import com.kenilt.skeleton.di.component.AppComponent
import com.kenilt.skeleton.di.component.DaggerAppComponent
import com.kenilt.skeleton.di.module.NetModule
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * Created by thangnguyen on 2019-06-16.
 */
object AppInjector {
    fun init(application: LXApplication): AppComponent {
        val appComponent = DaggerAppComponent.builder()
                .application(application)
                .netModule(NetModule(application.getString(R.string.api_url)))
                .build()
        val lifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        }
        application.registerActivityLifecycleCallbacks(lifecycleCallbacks)
        return appComponent
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            val lifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentCreated(fm: FragmentManager, fragment: Fragment,
                                               savedInstanceState: Bundle?) {
                    if (fragment is Injectable) {
                        AndroidSupportInjection.inject(fragment)
                    }
                }
            }
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(lifecycleCallbacks, true)
        }
    }
}
