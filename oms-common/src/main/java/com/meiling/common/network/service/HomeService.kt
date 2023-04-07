package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
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
        ): ResultData<OrderDto>

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


}