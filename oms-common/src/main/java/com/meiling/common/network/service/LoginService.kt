package com.meiling.common.network.service


import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.ForgetDto
import com.meiling.common.network.data.LoginDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

val loginService: LoginService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(LoginService::class.java)
}

interface LoginService {


    /***
     * 验证码
     * **/
    @POST("/saas/phone/send_code")
    suspend fun sendCode(
        @Query("phone") mobile: String,
        @Query("type") type: String = "2",
    ): ResultData<String>

    /**
     * 验证码登录
     * **/
    @POST("/uc/admin/phone_login")
    suspend fun mobileLogin(
        @Query("phone") mobile: String,
        @Query("code") code: String,
        @Query("state") state: String = "",
    ): ResultData<LoginDto>

    /**
     * 友盟Token
     * */
    @POST("/uc/admin/setUmengToken")
    suspend fun imToken(@Query("deviceToken") deviceToken: String): ResultData<Any>

    /**
     * 手机号登录
     */
    @POST("/uc/admin/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): ResultData<LoginDto>

    /**
     * 忘记密码
     */
    @GET("uc/adminuser/userNameVerify")
    suspend fun userNameVerify(@Query("userName") username: String): ResultData<ForgetDto>

    /**
     * 重置密码
     */
    @POST("/uc/adminuser/reset_password")
    suspend fun resetPwd(
        @Query("userName") username: String,
        @Query("phone") phone: String,
        @Query("code") code: String,
        @Query("password") password: String,
    ): ResultData<Any>

    /**
     * 校验验证码
     */
    @GET("/saas/business/code")
    suspend fun businessCode(
        @Query("phone") phone: String,
        @Query("code") code: String,
    ): ResultData<Any>

}