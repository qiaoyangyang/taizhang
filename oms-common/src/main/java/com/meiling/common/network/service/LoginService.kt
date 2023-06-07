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
    suspend fun upload(@Header("platform") platform:String?,@Part parts: MultipartBody.Part ):ResultData<String>

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
    suspend fun save(@Body businessDto: BusinessDto):ResultData<SaveSuccess>

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
    suspend fun getMerChantList(@Query("poiId") poiId:String):ResultData<ArrayList<Merchant>>

    /**
     * 绑定物流
     */
    @POST("/saas/express/merchant/save")
    suspend fun merChantSave(@Body putMerChant:PutMerChant):ResultData<Any>


    /**
     * 物流注册
     */
    @POST("/saas/express/merchant/add_merchant")
    suspend fun addMerChant(@Body dadaMerchantAddReq:DadaMerchantAddReq):ResultData<Any>

    /**
     * 获取类目
     */
    @GET("/saas/express/merchant/category")
    suspend fun getCategory(@Query("channelType")channelType:String,@Query("poiId")poiId:String):ResultData<ArrayList<OtherCategory>>

    /**
     * 获取余额列表
     * channelType 物流类型(uu,ss,sf_tc,dada)
     * poiId 门店编号
     */
    @GET("/saas/express/merchant/balance")
    suspend fun getMerchantBalanceList(@Query("channelType")channelType:String,@Query("poiId")poiId:String):ResultData<ArrayList<BalanceItem>>

    /**
     * 物流充值
     */
    @POST("/saas/express/merchant/recharge")
    suspend fun merchantRecharge(@Body merchantRecharge:MerchantRecharge):ResultData<String>

    /**
     * 手动绑定门店
     * poiId门店编号
     * thirdShopId三方门店编号
     * thirdShopName三方门店名称
     * type物流类型(uu,ss,sf_tc,dada)
     */
    @GET("/saas/logistics/auth/bind_shop")
    suspend fun bindShop(@Query("poiId") poiId:String,
                         @Query("thirdShopId") thirdShopId:String,
                         @Query("thirdShopName") thirdShopName:String,
                         @Query("type") type:String):ResultData<Any>
    /**
     * 获取短信验证码
     */
    @GET("/saas/logistics/auth/get_code")
    suspend fun getCode(@Query("phone")phone:String,
                        @Query("type")type:String):ResultData<Any>

    /**
     * 验证码静默授权
     */
    @GET("/saas/logistics/auth/get_openId")
    suspend fun getOpenId(@Query("code")code:String,
                          @Query("phone")phone:String,
                          @Query("poiId")poiId:String,
                          @Query("type")type:String):ResultData<Any>
    /**
     * 三方跳转链接授权
     */
    @GET("/saas/logistics/auth/get_url")
    suspend fun getUrl(@Query("originId")originId:String?,
                       @Query("poiId")poiId: String,
                       @Query("type")type:String):ResultData<String>

    /**
     *获取三方门店列表
     */
    @GET("/saas/logistics/auth/shop_list")
    suspend fun getShopList(@Query("pageNum")pageNum:String,
                            @Query("pageSize")pageSize:String,
                            @Query("poiId")poiId:String,
                            @Query("type")type:String):ResultData<ArrayList<OtherShop>>


}