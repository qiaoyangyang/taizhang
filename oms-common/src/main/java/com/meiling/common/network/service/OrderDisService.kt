package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


val orderDisService: OrderDisService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(OrderDisService::class.java)
}

interface OrderDisService {

    /**
     * 获取订单和门店配送时间
     * */
    @GET("/saas/logistics/getOrderAndPoiDeliveryDate")
    suspend fun getOrderAndPoiDeliveryDate(
        @Query("poiId") poiId: String,
        @Query("orderId") orderId: String,
        @Query("logisticsType") logisticsType: String, //配送商类型:同城=TC，物流=EXPRESS，自配送=SELF
    ): ResultData<OrderSendAddress>


    //物流询价接口
    @POST("/saas/logistics/orderConfirm")
    suspend fun orderSendConfirm(@Body orderSendRequest: OrderSendRequest): ResultData<ArrayList<OrderSendChannel>>

    //取消配送订单
    @POST("logistics/cancelOrder")
    suspend fun cancelOrderSend(@Body cancelCouponSum: CancelOrderSend): ResultData<Any>


    //发起配送
    @POST("/saas/logistics/insertOrder")
    suspend fun insertOrderSend(@Body logisticsConfirmDtoList: LogisticsConfirmDtoList): ResultData<Any>

    //配送详情
    @GET("/saas/logistics/getLogisticsOrderList")
    suspend fun getLogisticsOrderDetail(@Query("orderId") orderId: String): ResultData<ArrayList<OrderSendDetail>>

    //配送加小费显示
    @GET("/saas/logistics/getLogisticsOrder")
    suspend fun getLogisticsOrder(
        @Query("orderId") orderId: String, @Query("poiId") poiId: String,
        @Query("logisticsType") logisticsType: String
    ): ResultData<ArrayList<OrderSendAddTips>>

    //配送加小费
    @POST("/saas/logistics/addTips")
    suspend fun setAddTips(@Body orderSendAddTipRequest: OrderSendAddTipRequest): ResultData<Any>

    //配送物流选择
    @GET("/saas/logistics/logisticsMenu")
    suspend fun logisticsMenu(): ResultData<ArrayList<SelectLabel>>


}