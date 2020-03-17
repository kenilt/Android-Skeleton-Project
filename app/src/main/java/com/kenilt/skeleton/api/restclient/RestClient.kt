package com.kenilt.skeleton.api.restclient

import retrofit2.Retrofit


object RestClient {
    private lateinit var myRetrofit: Retrofit

    fun init(retrofit: Retrofit) {
        myRetrofit = retrofit
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return myRetrofit.create(serviceClass)
    }
}
