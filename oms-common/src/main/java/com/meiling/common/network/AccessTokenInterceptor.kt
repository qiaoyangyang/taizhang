package com.meiling.common.network

import com.blankj.utilcode.util.SPStaticUtils
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.LogUtils
import com.meiling.common.utils.MMKVUtils
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("platform", "android")
//        builder.addHeader("Authorization", MMKVUtils.getString(SPConstants.TOKEN)).build()
//        LogUtils.e("Token", MMKVUtils.getString(SPConstants.TOKEN))
        return chain.proceed(builder.build())
    }

}