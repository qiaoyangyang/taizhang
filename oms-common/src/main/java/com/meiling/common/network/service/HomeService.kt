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


    @GET("/api/v1/app/goods/exploreList")
    suspend fun exploreList(
        @Query("sortType") sortType: Int,
        @Query("current") current: Long,
        @Query("size") size: Long,
    ): ResultData<MutableList<ExploreGoodsVO>>


    @GET("/api/v1/app/home/recentTab")
    suspend fun recentTab(): ResultData<HomeRecentTabVO>


    @GET("/api/v1/app/market/list")
    suspend fun marketList(
        @Query("sortType") sortType: Int,
        @Query("current") current: Long,
        @Query("size") size: Long,
    ): ResultData<MutableList<MarketGoodsVO>>


    @GET("/api/v1/app/goods/userList")
    suspend fun goodsUserList(
        @Query("sortType") sortType: Int,
        @Query("current") current: Long,
        @Query("size") size: Long,
    ): ResultData<MutableList<UserGoodsVO>>


    @GET("/api/v1/app/recommended/interestedList")
    suspend fun interestedList(): ResultData<MutableList<FollowUserVO>>


    @POST("/api/v1/app/production/create")
    suspend fun create(@Body map: MutableMap<String, Any>): ResultData<Boolean>


    @GET("/api/v1/app/goods/collectionList")
    suspend fun getCollectionList(
        @Query("current") current: Long,
        @Query("size") size: Long,
    ): ResultData<MutableList<CollectionGoodsVO>>


    @GET("/api/v1/app/follow/followList")
    suspend fun getFollowList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<FollowVO>>


    @GET("/api/v1/app/follow/fansList")
    suspend fun getFansList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<FansVO>>


    @GET("/api/v1/app/profit/getProfitList")
    suspend fun getProfitList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<ProfitVO>>

    @GET("/api/v1/app/profit/getProfitInfo")
    suspend fun getProfitInfo(): ResultData<ProfitInfoVO>


    @GET("/api/v1/app/production/releaseRecordList")
    suspend fun getReleaseRecordList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<ReleaseRecordVO>>


    @GET("/api/v1/app/market/getRecordList")
    suspend fun getMarketRecordList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<MarketRecordVO>>


    @GET("/api/v1/app/market/cancel")
    suspend fun marketCancel(@Query("id") id: Long): ResultData<Boolean>

    @GET("/api/v1/app/transaction/getRecordList")
    suspend fun getTransactionRecordList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<TransactionRecordVO>>


    @GET("/api/v1/app/assets/getBalance")
    suspend fun getBalance(): ResultData<BigDecimal>


    @GET("/api/v1/app/assets/recordList")
    suspend fun getAssetsRecordList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<AssetsRecordVO>>


    @GET("/api/v1/app/home/searchList")
    suspend fun searchList(@Query("keyWord") keyWord: String): ResultData<SearchVO>

    @GET("/api/v1/app/goodsLike/getLikeList")
    suspend fun getLikeList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<GoodsLikeVO>>


    @GET("/api/v1/app/follow/userList")
    suspend fun getFollowUserList(
        @Query("current") current: Long,
        @Query("size") size: Long
    ): ResultData<MutableList<FollowCollectionVO>>

    @POST("/api/v1/app/user/realName")
    suspend fun realName(@Body map: MutableMap<String, String>): ResultData<Boolean>
}