package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import retrofit2.http.*


val orderCreateService: OrderCreateService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(OrderCreateService::class.java)
}

interface OrderCreateService {

    /**
     * 获取订单和门店配送时间
     * */
    @GET("/saas/poi/cityPoiOffline")
    suspend fun getCityPoiOffline(
        @Query("poiType") poiType: String = "1",
        @Query("hasCityAll") hasCityAll: String = "0",
    ): ResultData<ArrayList<ShopBean>>

    /**
     * 保存
     * */
    @POST("/saas/order/shop/selforder")
    suspend fun saveOrder(
        @Body selfGoodsDtoList:OrderCreateSaveDto): ResultData<Any>

//    /**
//     * 获取订单和门店配送时间
//     * */
//    @POST("/saas/poi/cityPoiOffline")
//    suspend fun saveOrder(
//        @QueryMap map: @JvmSuppressWildcards Map<String, Any>
//    ): ResultData<Any>
//

    //物流询价接口
    @POST("/saas/logistics/orderConfirm")
    suspend fun orderSendConfirm(@Body orderSendRequest: OrderSendRequest): ResultData<ArrayList<OrderSendChannel>>

}