package com.meiling.common.network.service


import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import retrofit2.http.GET
import retrofit2.http.Query

val loginService: LoginService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(LoginService::class.java)
}

interface LoginService {


    @GET("/api/v1/app/sms/sendCode")
    suspend fun sendCode(@Query("mobile") mobile: String): ResultData<Boolean>

    @GET("/api/v1/app/login/mobileLogin")
    suspend fun mobileLogin(
        @Query("mobile") mobile: String,
        @Query("code") code: String
    ): ResultData<String>


    @GET("/api/v1/app/login/imToken")
    suspend fun imToken(): ResultData<String>
}