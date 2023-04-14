package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import java.math.BigDecimal
import com.google.gson.annotations.SerializedName
import retrofit2.http.*


val acceptanceCheckService: AcceptanceCheckService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(AcceptanceCheckService::class.java)
}

interface AcceptanceCheckService {
    //http://test.api.igoodsale.com/saas//shop/cityshop
    // 获取该用户所有城市的店铺或工厂shop
    @GET("saas/shop/cityshop")
    suspend fun cityshop(
       // @Query("poiType") poiType: String? = "",
        @Query("channelId") channelId: String,
        @Query("hasCityAll") hasCityAll: String? = "0",
    ): ResultData<ArrayList<ShopBean>>

    @GET("/saas/poi/citypoi")
    suspend fun cityPoi(
    ): ResultData<ArrayList<ShopBean>>

    //抖音用户扫码的结果
    @GET("saas/dytg/prepare")
    suspend fun prepare(
        @Query("code") code: String? = "",//用户券码
        @Query("url") url: String,//扫码后的短链地址
        @Query("shopId") shopId: String? = "",//
    ): ResultData<ArrayList<ThrillBen>>

    //美团用户扫码的结果
    @GET("saas/mttg/prepare/{couponCode}")
    suspend fun mttgprepare(
        @Path("couponCode") couponCode: String,
        @Query("shopId") shopId: String? = "",//
    ): ResultData<String>


    //美团开始核销
    @POST("saas/mttg/consume/{couponCode}")
    suspend fun consume(
        @Path("couponCode") couponCode: String,
        @Query("count") count: String? = "",//
        @Query("shopId") shopId: String? = "",//
    ): ResultData<String>

    // 美团撤销
    @POST("saas/mttg/cancel/{couponCode}")
    suspend fun mttgcancel(
        @Path("couponCode") couponCode: String,
        @Query("shopId") shopId: String? = "",//
    ): ResultData<String>


    //抖音开始核销
    @GET("saas/dytg/verify")
    suspend fun verify(
        @Query("code") code: String? = "",//用户券码
        @Query("shopId") shopId: String? = "",//
    ): ResultData<ArrayList<ThrillItem>>

    //券码撤销
    @GET("saas/dytg/cancel")
    suspend fun cancel(
        @Query("code") code: String? = "",//用户券码
        @Query("shopId") shopId: String? = "",//
    ): ResultData<String>


    //核销列表
    @GET("saas/mttg/h5/coupon")
    suspend fun coupon(
        @Query("startDate") startDate: String = "",//开始时间
        @Query("endDate") endDate: String = "",//结束时间
        @Query("poiId") poiId: String = "",//门店id
        @Query("selectText") selectText: String = "",//搜索券码
        @Query("pageIndex") pageIndex: Int = 1,//1
        @Query("pageSize") pageSize: String = "20",//20
        @Query("status") status: String = "",//20
        @Query("type") type: String = "",//20
        @Query("isVoucher") isVoucher: String = "",//20
    ): ResultData<Writeoffhistory>


    @GET("saas/mttg/coupon/data")
    suspend fun codeNumber(
        @Query("startDate") startDate: String = "",//开始时间
        @Query("endDate") endDate: String = "",//结束时间
        @Query("poiId") poiId: String = "",//门店id
        @Query("selectText") selectText: String = "",//搜索券码
        @Query("status") status: String = "",//20
        @Query("type") type: String = "",//20
        @Query("isVoucher") isVoucher: String = "",//20

        ): ResultData<RecordCodeNumber>


}