package com.meiling.account.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import com.meiling.account.bean.*
import com.meiling.account.bean.PageResult
import retrofit2.http.*


val commodityService: CommodityService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(CommodityService::class.java)
}

interface CommodityService {

    /**
     *获取分类列表
     * **/
    @GET("/sort/list")
    suspend fun sorlistt(
    ): ResultData<ArrayList<GoosClassify>>


    /**
     *商品管理
     * **/
    @POST("/goods/list")
    suspend fun goodslistt(
        @Body goodsController: GoodsController,
    ): ResultData<GoodsBean>


}