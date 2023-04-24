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
        builder.addHeader("platform", "app")
        builder.addHeader("adminViewId", MMKVUtils.getString(SPConstants.adminViewId))
        builder.addHeader("adminToken", MMKVUtils.getString(SPConstants.TOKEN))
        builder.addHeader("tenantId", MMKVUtils.getString(SPConstants.tenantId))
//        builder.addHeader("Authorization", MMKVUtils.getString(SPConstants.TOKEN)).build()
       // Log.d("OkHttp","adminToken-----"+ MMKVUtils.getString(SPConstants.TOKEN))

        return chain.proceed(builder.build())
    }

}