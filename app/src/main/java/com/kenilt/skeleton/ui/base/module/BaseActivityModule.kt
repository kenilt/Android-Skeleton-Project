package com.kenilt.skeleton.ui.base.module

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.kenilt.skeleton.ui.base.BaseActivity
import dagger.Module
import dagger.Provides

/**
 * Created by thangnguyen on 2019-06-14.
 */
@Module
class BaseActivityModule {
    @Provides
    fun provideContext(activity: BaseActivity<*>): Context {
        return activity
    }

    @Provides
    fun provideFragmentActivity(activity: BaseActivity<*>): FragmentActivity {
        return activity
    }

    @Provides
    fun provideFragmentManager(activity: BaseActivity<*>): FragmentManager {
        return activity.supportFragmentManager
    }
}
