package com.kenilt.skeleton.di.component

import android.app.Application
import com.kenilt.skeleton.LXApplication
import com.kenilt.skeleton.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Kenilt Nguyen on 7/13/18.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    ViewModelModule::class,
    AppModule::class,
    NetModule::class,
    ApiModule::class
])
interface AppComponent: AndroidInjector<LXApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun netModule(netModule: NetModule): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: LXApplication)
}
