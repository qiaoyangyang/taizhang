package com.meiling.common.network

import com.friendwing.universe.common.network.LoggerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private val DEFAULT_TIME_OUT = 60
    private lateinit var INSTANCE: RetrofitClient
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var mRetrofit: Retrofit

    fun getHttpClient(): OkHttpClient {
        return okHttpClient
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggerInterceptor())
            .addInterceptor(AccessTokenInterceptor())
            .connectTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()
        okHttpClient = client
        return client
    }


    private fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://47.97.158.192:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(client)
            .build()
    }

    private fun RetrofitClient(): RetrofitClient {
        mRetrofit = provideRetrofit(provideOkHttpClient())
        return this
    }


    fun getInstance(): RetrofitClient {
        INSTANCE = RetrofitClient()
        return INSTANCE
    }


    fun <T> createApiClient(apiClientClass: Class<T>): T {
        return mRetrofit.create(apiClientClass)
    }
}