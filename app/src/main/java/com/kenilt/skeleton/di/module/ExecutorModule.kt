package com.kenilt.skeleton.di.module

import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Kenilt Nguyen on 9/10/18.
 */
@Module
internal object ExecutorModule {
    @Provides
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }
}
