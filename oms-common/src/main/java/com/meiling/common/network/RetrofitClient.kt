package com.meiling.common.network

import com.blankj.utilcode.util.SPStaticUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.MMKVUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS


        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AccessTokenInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()
        okHttpClient = client
        return client
    }


    private fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("http://test-oms.igoodsale.com")//测试环境
            .baseUrl(SPStaticUtils.getString(SPConstants.IP,"http://dev-oms-api.igoodsale.com"))//开发环境
//            .baseUrl("http://test-oms-api.igoodsale.com")//测试环境
//            .baseUrl("http://dev-oms-api.igoodsale.com")//开发环境
//            .baseUrl("https://ods-api.igoodsale.com")//生产环境
//            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(CustomGsonConverterFactory.create())
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