package com.meiling.common.network.service


import com.meiling.common.network.ResultData
import com.meiling.common.network.RetrofitClient
import com.meiling.common.network.data.*
import okhttp3.MultipartBody
import retrofit2.http.*

val loginService: LoginService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RetrofitClient().getInstance().createApiClient(LoginService::class.java)
}

interface LoginService {


    /**
     * 登录
     * **/
    @POST("api/user/passwordLogin")
    suspend fun mobileLogin(
        @Body dataDisDto: UserLoginData,
    ): ResultData<LoginDto>

    /**
     *
    用户信息详情
     * **/
    @GET("api/user/current/userInfo")
    suspend fun userInfo(
    ): ResultData<userInfoBean>

    /**
     *用户所在门店
     * **/
    @GET("api/user/userStoreList")
    suspend fun userStoreList(
    ): ResultData<ArrayList<UserStoreList>>


}