package com.kenilt.skeleton.di.module

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.kenilt.skeleton.managers.prefs.LXConfig
import com.kenilt.skeleton.managers.prefs.TrackingPrefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(application: Application): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(application)
    }

    @Provides
    @Singleton
    fun provideLXConfig(application: Application): LXConfig {
        return LXConfig(application)
    }

    @Provides
    @Singleton
    fun provideTrackingPrefs(application: Application): TrackingPrefs {
        return TrackingPrefs(application)
    }
}
