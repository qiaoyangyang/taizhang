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

    /**
     *商品入库
     * **/
    @POST("/storageGoods/save")
    suspend fun storageGoodssave(
        @Body storageGoods: StorageGoods,
    ): ResultData<String>

  /**
     *获取时间分段列表
     * **/
    @POST("/storageGoods/dateSplit")
    suspend fun dateSplit(
        @Body dateSplit: DateSplit,
    ): ResultData<ArrayList<DateSplitList>>

    /**
     *
    时间段内入库商品列表
     * **/
    @POST("/storageGoods/goodsSplit")
    suspend fun goodsSplit(
        @Body dateSplit: DateSplit,
    ): ResultData<ArrayList<DateSplitList>>


}