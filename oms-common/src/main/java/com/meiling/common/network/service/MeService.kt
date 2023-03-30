package com.meiling.common.network.service

import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.UserDataVO
import com.meiling.common.network.data.UserInfoVO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


val meService: MeService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(MeService::class.java)
}

interface MeService {


    @GET("/api/v1/app/user/userData")
    suspend fun userData(@Query("userId") userId: String): ResultData<UserDataVO>


    @GET("/api/v1/app/user/userInfo")
    suspend fun userInfo(): ResultData<UserInfoVO>

    @POST("/api/v1/app/user/modifyInfo")
    suspend fun modifyInfo(@Body map: Map<String, String>): ResultData<String>


}