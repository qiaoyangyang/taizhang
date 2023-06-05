package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import retrofit2.http.*
import java.math.BigDecimal


val homeService: HomeService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(HomeService::class.java)
}

interface HomeService {

    @GET("/saas/order/pending")
    suspend fun orderStatus(
        @Query("logisticsStatus") logisticsStatus: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String,
        @Query("businessNumberType") businessNumberType: String,
        @Query("pageIndex") pageIndex: String,
        @Query("orderTime") orderTime: String = "1",
        @Query("pageSize") pageSize: String = "20",
        @Query("deliverySelect") deliverySelect: String = "0",
        @Query("orderStatus") orderStatus: String = "300",
        @Query("isValid") isValid: String = "",
        @Query("businessNumber") businessNumber: String = "",
        @Query("selectText") selectText: String = "",
        @Query("channelId") channelId: String = "0",
    ): ResultData<OrderDto>

    //订单列表
    @GET("/saas/order/orderList")
    suspend fun orderList(
        @Query("logisticsStatus") logisticsStatus: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String,
        @Query("businessNumberType") businessNumberType: String,
        @Query("pageIndex") pageIndex: String,
        @Query("orderTime") orderTime: String = "1",
        @Query("pageSize") pageSize: String = "20",
        @Query("deliverySelect") deliverySelect: String = "0",
        @Query("orderStatus") orderStatus: String = "300",
        @Query("isValid") isValid: String = "",
        @Query("businessNumber") businessNumber: String = "",
        @Query("selectText") selectText: String = "",
        @Query("channelId") channelId: String = "0",
        @Query("poiId") poiId: String = "",
    ): ResultData<OrderDto>

  //订单详情
    @GET("/saas/order/orderDetailVo")
    suspend fun orderDetail(
        @Query("orderViewId") orderViewId: String,
    ): ResultData<OrderDetailDto>


    //忽略订单
    @POST("saas/order/invalid/{orderViewId}/{value}")
    suspend fun invalid(
        @Path("orderViewId") orderViewId: String,
        @Path("value") value: String //0忽略订单 1 恢复忽略订单
    ): ResultData<String>

    @GET("/saas/order/statusCount")
    suspend fun statusCount(
        @Query("logisticsStatus") logisticsStatus: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String,
        @Query("businessNumberType") businessNumberType: String,
        @Query("pageIndex") pageIndex: String,
        @Query("orderTime") orderTime: String = "1",
        @Query("pageSize") pageSize: String = "20",
        @Query("deliverySelect") deliverySelect: String = "0",
        @Query("orderStatus") orderStatus: String = "300",
        @Query("isValid") isValid: String = "",
        @Query("businessNumber") businessNumber: String = "",
        @Query("channelId") channelId: String = "0",
        @Query("poiId") poiId: String = "0",
    ): ResultData<StatusCountDto>

    @GET("/saas/order/shop/updateRemark")
    suspend fun updateRemark(
        @Query("remark") remark: String,
        @Query("orderId") orderId: String
    ): ResultData<Any>


    @POST("/saas/order/shop/update/{orderId}")
    suspend fun updateAddress(
        @Path("orderId") orderId: String,
        @Query("recvName") recvName: String,
        @Query("recvPhone") recvPhone: String,
        @Query("recvAddr") recvAddr: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("arriveTime") arriveTime: String
    ): ResultData<Any>



}