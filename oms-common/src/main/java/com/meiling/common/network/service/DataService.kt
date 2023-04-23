package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


val dataService: DataService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(DataService::class.java)
}

interface DataService {

    /**
     * 配送
     * */
    @POST("/saas/logistics/data/list")
    suspend fun dataList(
        @Body dataDisDto: DataListDto,
    ): ResultData<DataDisDto>

    /**
     * 数据
     * */
    @POST("/saas/channel/data/list")
    suspend fun channelDataList(
        @Body dataDisDto: DataListDto,
    ): ResultData<MutableList<ChannelDataList>>

    /**
     * 门店
     * */
    @POST("/saas/orderSalesGrouping/getOrderSaleGroupingStatistics")
    suspend fun shopData(
        @Body dataShop: DataShop,
    ): ResultData<DataShopShow>

    @POST("/saas/orderSalesGrouping/getStaticsGroupByHours")
    suspend fun shopDataList(
        @Body dataDisDto: DataListDto,
    ): ResultData<DataShopList>

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
    ): ResultData<StatusCountDto>

    @GET("/api/v1/app/home/recentTab")
    suspend fun recentTab(): ResultData<HomeRecentTabVO>


    @GET("/saas/poi/citypoi")
    suspend fun cityPoi(
        @Query("hasCityAll") hasCityAll: String = "1"
    ): ResultData<ArrayList<ShopBean>>


}