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


    /**
     * 校验验证码
     */
    @GET("/saas/Version/CheckUpdate")
    suspend fun update(
        @Query("phone") phone: String,
        @Query("code") code: String,
    ): ResultData<Any>


    /**
     * 上传logo图片
     */
    @Multipart
    @POST("/saas/system/file/upload")
    suspend fun upload(@Part parts: List<MultipartBody.Part> ):ResultData<String>

    /**
     * 所属行业
     */
    @GET("/saas/business/category")
    suspend fun getCategory():ResultData<List<Children>>

    /**
     * 校验品牌名称
     */
    @GET("/saas/business/thanBrand")
    suspend fun checkThanBrand(@Query("name") name: String):ResultData<Boolean>

    /**
     * 注册
     */
    @POST("/saas/business/save")
    suspend fun save(@Body businessDto: BusinessDto):ResultData<String>

    /**
     *  所属渠道  租户默认所有渠道
     */
    @GET("/saas/business/channel")
    suspend fun getChannel():ResultData<ArrayList<Channel>>

    /**
     * 校验账号名称
     */
    @GET("/saas/business/username/check")
    suspend fun checkUserName(@Query("username") username:String):ResultData<Any>

    /**
     * 校验品牌名称
     */
    @GET("/saas/business/thanBrand")
    suspend fun thanBrand(@Query("name") name:String):ResultData<Any>

    /**
     *  所属城市  租户默认所有城市
     */
    @GET("/saas/business/city")
    suspend fun getCity():ResultData<ArrayList<City>>

    /**
     *  获取物流列表
     */
    @GET("/saas/express/merchant/list")
    suspend fun getMerChantList(@Header("tenantId") tenantId:String):ResultData<ArrayList<Merchant>>

    /**
     * 绑定物流
     */
    @POST("/saas/express/merchant/save")
    suspend fun merChantSave(@Body putMerChant:PutMerChant):ResultData<Any>

}