package com.kenilt.skeleton.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kenilt.skeleton.BuildConfig
import com.kenilt.skeleton.api.parser.DateTypeDeserializer
import com.kenilt.skeleton.api.parser.NullToEmptyAdapterFactory
import com.kenilt.skeleton.api.restclient.RestRequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Kenilt Nguyen on 7/13/18.
 */
@Module
class NetModule(private val baseUrl: String) {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapterFactory(NullToEmptyAdapterFactory())
                .registerTypeAdapter(Date::class.java, DateTypeDeserializer())
                .create()
    }

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideRestRequestInterceptor(): RestRequestInterceptor {
        return RestRequestInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(restRequestInterceptor: RestRequestInterceptor,
                            logging: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .readTimeout(TIMEOUT_TIME, TimeUnit.MINUTES)
                .connectTimeout(TIMEOUT_TIME, TimeUnit.MINUTES)
                .addInterceptor(restRequestInterceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    companion object {
        private const val TIMEOUT_TIME = 1L
    }
}
