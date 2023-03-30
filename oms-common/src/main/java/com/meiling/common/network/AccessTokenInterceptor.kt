package com.meiling.common.network

import com.blankj.utilcode.util.SPStaticUtils
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Authorization", SPStaticUtils.getString(SPConstants.TOKEN)).build()
        LogUtils.e("Token", SPStaticUtils.getString(SPConstants.TOKEN))
        return chain.proceed(builder.build())
    }

}