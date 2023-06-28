package com.meiling.common.network.service


import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import okhttp3.MultipartBody
import retrofit2.http.*

val loginService: LoginService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(LoginService::class.java)
}

interface LoginService {


    /**
     * 验证码登录
     * **/
    @POST("/uc/admin/phone_login")
    suspend fun mobileLogin(
        @Query("phone") mobile: String,
        @Query("code") code: String,
        @Query("state") state: String = "",
    ): ResultData<LoginDto>


}