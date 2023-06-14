package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import okhttp3.MultipartBody
import retrofit2.http.*


val meService: MeService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(MeService::class.java)
}

interface MeService {

    /**
     * 消息中心
     * */
    @GET("/saas/messageCenter/getMessageList")
    suspend fun getMsgCenter(
        @Query("pageIndex") pageIndex: Int,
        @Query("pageSize") pageSize: String,
        @Query("messagePlatform") umeng: String = "umeng",
        @Query("onlyUnread") onlyUnread: String = "0",
    ): ResultData<MessageDto>

    /**
     * 退出登陆
     * */
    @POST("/uc/admin/logout")
    suspend fun setUmengToken(
    ): ResultData<Any>
    /**
     * 注销账号
     * */
    @POST("/uc/adminuser/disableAccount")
    suspend fun disableAccount(
        @Query("userViewId") userViewId: String,
        @Query("accountStatus") accountStatus: String = "9",
    ): ResultData<Any>

    /**
     * 获取门店
     * */
    @GET("saas/poi/citypoi")
    suspend fun citypoi(
        @Query("poiType") poiType: String="1,2",
        @Query("hasCityAll") hasCityAll: String = "0",
    ): ResultData<ArrayList<ShopBean>>

    /**
     * 获取门店 公共接口get请求 传租户id
     * */
    @GET("saas/common/getByTenantId")
    suspend fun getByTenantId(

    ): ResultData<ByTenantId>


    /**
     * 云打印 列表
     * */
    @POST("saas/printer/printChannelList")
    suspend fun printChannelList(

    ): ResultData<ArrayList<Printing>>
    /**
     * 云打印 列表打印详情
     * */
    @POST("saas/printer/printDetail")
    suspend fun printDetail(
        @Query("deviceID") deviceID: String="",
    ): ResultData<Printing>
 /**
     * 云打印 列表删除
     * */
    @POST("saas/printer/delDev")
    suspend fun delDev(
        @Query("deviceID") deviceID: String="",
    ): ResultData<String>
/**
     * 云打印 添加打印机
     * */
    @POST("saas/printer/addDev")
    suspend fun addDev
    (@Body rechargeDto: PrinterConfigDto):
     ResultData<String>/**
     * 云打印 编辑打印机
     * */
    @POST("saas/printer/update")
    suspend fun update
    (@Body rechargeDto: PrinterConfigDto):
     ResultData<String>


}