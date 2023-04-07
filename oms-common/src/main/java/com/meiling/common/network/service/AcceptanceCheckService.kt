package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.math.BigDecimal


val acceptanceCheckService: AcceptanceCheckService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(AcceptanceCheckService::class.java)
}

interface AcceptanceCheckService {
    //http://test.api.igoodsale.com/saas//shop/cityshop
    // 获取该用户所有城市的店铺或工厂shop
    @GET("saas/shop/cityshop")
    suspend fun cityshop(
        @Query("poiType") poiType: String? = "1,2",
        @Query("channelId") channelId: String,
        @Query("hasCityAll") hasCityAll: String? = "1",
    ): ResultData<ArrayList<ShopBean>>
  @GET("saas/poi/citypoi")
    suspend fun cityPoi(
    ): ResultData<ArrayList<ShopBean>>

    @GET("saas/dytg/prepare")
    suspend fun prepare(
        @Query("code") code: String? = "1,2",//用户券码
        @Query("url") url: String,//扫码后的短链地址
        @Query("shopId") shopId: String? = "",//
    ): ResultData<ArrayList<String>>


}