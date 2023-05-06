package com.meiling.oms.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import com.meiling.oms.bean.*
import com.meiling.oms.bean.PageResult
import retrofit2.http.*


val branchInformationService: BranchInformationService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(BranchInformationService::class.java)
}

interface BranchInformationService {


    @GET("saas/poi/list")
    suspend fun poilis(
        @Query("pageIndex") pageIndex: Int = 1,//1
        @Query("pageSize") pageSize: String = "200",//20
        @Query("city") city: String = "0",//20
        @Query("types") types: String = "0",//20
        @Query("adminUserRole") adminUserRole: String = "19",//20
    ): ResultData<BranchInformation>

    @GET("saas/poi/{id}")
    suspend fun poi(
        @Path("id") id: String,
    ): ResultData<PoiVoBean>

    @POST("saas/poi")
    suspend fun poiadd(

        @Query("id") id: String = "",//门店id
        @Query("poiName") poiName: String = "",//门店名称
        @Query("sinceCode") sinceCode: String = "",//门店编号
        @Query("poiPhone") poiPhone: String = "",//门店电话
        @Query("poiAddress") poiAddress: String = "",//门店地址
        @Query("lat") lat: String = "",//
        @Query("lon") lon: String = "",//
        @Query("contactPerson") contactPerson: String = "",//联系人姓名
        @Query("mobilePhone") mobilePhone: String = "",//联系人手机号
        @Query("provinceCode") provinceCode: String = "",//省
        @Query("cityCode") cityCode: String = "",//省
        @Query("districtCode") districtCode: String = "",//省
        @Query("cityName") cityName: String = "",//省
    ): ResultData<String>


    @GET("saas/channel")
    suspend fun getShopAndChannelVO(
    ): ResultData<ArrayList<ChannelX>>
//http://test-oms-api.igoodsale.com/saas/poi/shop_list?poiId=156207217&channelId= get请求 传poiId 和 channelId 对应门店的渠道的店铺

    @GET("saas/poi/shop_list")
    suspend fun shop_list(
        @Query("channelId") channelId: String = "",//所选渠道的id
        @Query("poiId") poiId: String = "",//所选渠道的id
    ): ResultData<ChannShopBean>

    @GET("/saas/admin/unification/auth")
    suspend fun urlauth(
        @Query("channelId") channelId: String = "",//所选渠道的id
        @Query("poiId") poiId: String = "",//所选渠道的id
        @Query("businessId") businessId: String = "",//美团专用 1：美团点评 2：美团外卖 3：美团闪惠
    ): ResultData<Unification>

    @GET("/saas/admin/unification/auth")
    suspend fun douurlauth(
        @Query("channelId") channelId: String = "",//所选渠道的id
        @Query("poiId") poiId: String = "",//所选渠道的id
        @Query("selectText") selectText: String = "",//抖音专用，搜索门店名称
        @Query("pageSize") pageSize: String = "20",//抖音专用，当前页数量
        @Query("pageNum") pageNum: String = "1",//抖音专用，当前页页码

    ): ResultData<PageResult>


    //  抖音绑定
    @GET("saas/dytg/bindShop")
    suspend fun bindShop(
        @Query("shopId") shopId: String = "",//门店id（poiId)
        @Query("address") address: String = "",//
        @Query("shopName") shopName: String = "",//
        @Query("channelPoiId") channelPoiId: String = "",//	渠道门店Id
    ): ResultData<String>

    //  抖音商户
    @GET("saas/dytg/bindTenant")
    suspend fun bindTenant(
        @Query("accountId") accountId: String = "",//抖音商户id
    ): ResultData<String>

    //解绑
    @GET("saas/admin/unification/releasebind")
    suspend fun releasebind(
        @Query("channelId") channelId: String,
        @Query("shopId") viewId: String,
    ): ResultData<String>
      //设置渠道店铺的门店
    @GET("saas/poi/updateShop")
    suspend fun updateShop(
        @Query("shopId") shopId: String,
        @Query("poiId") poiId: String,
    ): ResultData<String>

    //是否抖音商户
    @GET("saas/dytg/isTenant")
    suspend fun isTenant(
    ): ResultData<Boolean>



    @GET("saas/poi/deletePoi")
    suspend fun deletePoi(
        @Query("poiId") id: String = "",//门店名称
    ): ResultData<String>

}