package com.meiling.account.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.*
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.MMKVUtils

class LoginViewModel(application: Application) : BaseViewModel(application) {

    //登陆
    val loginData = BaseLiveData<LoginDto>()
    fun mobileLogin(phone: String, pwd: String) {
        request({ loginService.mobileLogin(UserLoginData(phone,pwd)) }, loginData)
    }

    //用户信息详情
    val userBean = BaseLiveData<userInfoBean>()
    fun userInfo() {
        request({ loginService.userInfo() }, userBean)
    }

    //
    //用户所在门店
    val UserStoreList = BaseLiveData<ArrayList<UserStoreList>>()
    fun userStoreList() {
        request({ loginService.userStoreList() }, UserStoreList)
    }








}