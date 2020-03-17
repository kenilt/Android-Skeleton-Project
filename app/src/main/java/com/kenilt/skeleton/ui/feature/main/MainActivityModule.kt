package com.kenilt.skeleton.ui.feature.main

import com.kenilt.skeleton.ui.base.BaseActivity
import com.kenilt.skeleton.ui.base.module.BaseActivityModule
import dagger.Binds
import dagger.Module

/**
 * Created by thangnguyen on 2019-06-14.
 */
@Module(includes = [BaseActivityModule::class])
abstract class MainActivityModule {

    @Binds
    abstract fun provideBaseActivity(activity: MainActivity): BaseActivity<*>

}
