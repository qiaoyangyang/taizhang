package com.meiling.oms.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import com.meiling.oms.bean.BranchInformation
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



}