package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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
    @POST("saas/logistics/cancelOrder")
    suspend fun cancelOrderSend(@Body cancelCouponSum: CancelOrderSend): ResultData<String>

    //完成自提
    @POST("saas/order/shop/shipped/{orderId}")
    suspend fun orderFinish(@Path("orderId") orderId: String): ResultData<Any>
//  //自提订单确认
//    @POST("saas/order/orderOutStorage")
//    suspend fun orderFinish(@Query("orderId") orderId: String): ResultData<Any>


    //发起配送
    @POST("/saas/logistics/insertOrderApp")
    suspend fun insertOrderSend(@Body logisticsConfirmDtoList: LogisticsConfirmDtoList): ResultData<Any>

    //配送详情
    @GET("/saas/logistics/getLogisticsOrderList")
    suspend fun getLogisticsOrderDetail(@Query("orderId") orderId: String): ResultData<ArrayList<OrderSendDetail>>

    //配送加小费显示
    @GET("/saas/logistics/getLogisticsOrder")
    suspend fun getLogisticsOrder(
        @Query("orderId") orderId: String
    ): ResultData<ArrayList<OrderSendAddTips>>

    //配送加小费
    @POST("/saas/logistics/addTips")
    suspend fun setAddTips(@Body orderSendAddTipRequest: OrderSendAddTipRequest): ResultData<Any>

    //配送物流选择
    @GET("/saas/logistics/logisticsMenu")
    suspend fun logisticsMenu(): ResultData<ArrayList<SelectLabel>>

    //配送渠道/平台
    @GET("/saas/channel")
    suspend fun orderChannelPlatForm(): ResultData<ArrayList<OrderSelectPlatform>>

    //    入参 sourceId 订单号
//    shopId 店铺id
//    printTemplateType 收银小票:1 退款小票:3
    //打印
    @POST("/saas/printer/printTicket")
    suspend fun print(
        @Query("sourceId") sourceId: String,
        @Query("shopId") shopId: String,
        @Query("printTemplateType") orderId: String//收银小票:1 退款小票:3
    ): ResultData<Any>

}