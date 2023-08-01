package com.meiling.common.network

import android.util.Log
import com.google.gson.Gson
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.LogUtils
import com.meiling.common.utils.MMKVUtils
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.header("userViewId", MMKVUtils.getString(SPConstants.userViewId))
        builder.header("token", MMKVUtils.getString(SPConstants.TOKEN))
        builder.header("tenantId", MMKVUtils.getString(SPConstants.tenantId))
        builder.header("platform", "ipad")

        return chain.proceed(builder.build())
    }

}