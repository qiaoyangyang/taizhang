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


    //充值
    @POST("/saas/payAccountAmount/paymentApp")
    suspend fun rechargeRequest(@Body rechargeDto: RechargeRequest): ResultData<String>


    /**
     * 余额
     * **/
    @GET("/saas/payAccountAmount/getPayAccountAmountVo")
    suspend fun getPayAccountAmountVo(): ResultData<BalanceDto>

    /**
     * 充值记录
     * */
    @POST("/saas/payAccountAmount/getRecordList")
    suspend fun getRecord(@Body rechargeRecordListReq: RechargeRecordListReq): ResultData<RechargeRecordDto>

    /**
     * 财务结算明细
     * */
    @POST("/saas/financial/getRecordList")
    suspend fun getFinancialRecord(@Body rechargeRecordListReq: RechargeRecordListReq): ResultData<FinancialRecord>

    /**
     * 财务结算记录明细
     * */
    @POST("/saas/financial/getRecordListByViewId")
    suspend fun getFinancialRecordDetail(
        @Query("viewId") viewId: String,
        @Query("pageIndex") pageIndex: String,
        @Query("pageSize") pageSize: String
    ): ResultData<FinancialRecordDetail>

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



}