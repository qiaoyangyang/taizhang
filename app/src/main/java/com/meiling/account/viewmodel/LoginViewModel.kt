package com.meiling.account.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ByTenantId
import com.meiling.common.network.data.LoginDto
import com.meiling.common.network.service.loginService
import com.meiling.common.network.service.meService
import com.meiling.common.utils.MMKVUtils

class LoginViewModel(application: Application) : BaseViewModel(application) {

    //登陆
    val loginData = BaseLiveData<LoginDto>()
    fun mobileLogin(phone: String, pwd: String) {
        request({ loginService.mobileLogin(phone, pwd) }, loginData)
    }





}