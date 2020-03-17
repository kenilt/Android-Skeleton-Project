package com.kenilt.skeleton.di.module

import com.kenilt.skeleton.ui.feature.main.MainActivity
import com.kenilt.skeleton.ui.feature.main.MainActivityModule
import com.kenilt.skeleton.ui.feature.welcome.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by thangnguyen on 2019-06-13.
 */
@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeWelcomeActivity(): WelcomeActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

}
