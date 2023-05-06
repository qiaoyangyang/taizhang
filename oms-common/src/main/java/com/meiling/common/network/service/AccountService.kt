package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import retrofit2.http.*


val accountService: AccountService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(AccountService::class.java)
}

interface AccountService {

    @POST("/uc/adminuser/list")
    suspend fun orderStatus(
        @Body requestDto: RequestAccount,
    ): ResultData<AccountListDto>

    /**
     * 注销账号/禁用账号 1启用
     * */
    @POST("/uc/adminuser/disableAccount")
    suspend fun disableAccount(
        @Query("userViewId") userViewId: String,
        @Query("accountStatus") accountStatus: String,
    ): ResultData<Any>

    //获取授权门店列表
    @GET("/saas/poi")
    suspend fun getPoiList(
        @Query("pageIndex") pageIndex: Int,
        @Query("pageSize") pageSize: String = "10",
    ): ResultData<CreateSelectPoiDto>

    /**
     * 获取门店
     * */
    @GET("saas/poi/citypoi")
    suspend fun getCityPoiList(
        @Query("poiType") poiType: String = "1,2",
        @Query("hasCityAll") hasCityAll: String = "0",
    ): ResultData<ArrayList<CreateShopBean>>

    /**
     * 检查账号
     * */
    @GET("uc/adminuser/username/check")
    suspend fun checkAccount(
        @Query("username") username: String,
    ): ResultData<ArrayList<CreateShopBean>>
    /**
     * 检查手机号
     * */
    @POST("uc/adminuser/phoneIsRepeat")
    suspend fun checkAccountPhone(
        @Query("phone") phone: String,
    ): ResultData<ArrayList<CreateShopBean>>

    /**
     * 获取门店
     * */
    @POST("uc/adminuser/saveAndUpdate")
    suspend fun saveAndUpdate(
        @Body requestDto: ReqCreateAccount,
    ): ResultData<Any>
}