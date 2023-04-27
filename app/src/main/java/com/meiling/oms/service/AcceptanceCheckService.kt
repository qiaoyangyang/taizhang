package com.meiling.oms.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import com.meiling.oms.bean.BranchInformation
import com.meiling.oms.bean.Channel
import com.meiling.oms.bean.PoiVoBean
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


    @POST("saas/channel/getShopAndChannelVO")
    suspend fun getShopAndChannelVO(
        @Query("id") id: String = "",//门店名称
    ): ResultData<Channel>


    @GET("saas/admin/auth")
    suspend fun urlauth(
        @Query("channelId") channelId: String = "",//所选渠道的id
        @Query("poiId") poiId: String = "",//所选渠道的id
        @Query("businessId") businessId: String = "",//美团专用 1：美团点评 2：美团外卖 3：美团闪惠
    ): ResultData<String>


    @GET("saas/poi/deletePoi")
    suspend fun deletePoi(
        @Query("poiId") id: String = "",//门店名称
    ): ResultData<String>

}