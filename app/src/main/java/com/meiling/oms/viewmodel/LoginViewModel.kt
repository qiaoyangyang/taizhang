package com.meiling.oms.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.constant.ARouteConstants
import com.meiling.common.network.APIException
import com.meiling.common.network.ExceptionHandle
import com.meiling.common.network.ResultData
import com.meiling.common.network.service.LoginService
import com.meiling.common.network.service.loginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val sendCode = BaseLiveData<Boolean>()
    val loginData = BaseLiveData<String>()

    fun sendCode(phone: String) {
        request({ loginService.sendCode(phone) }, sendCode)
    }

    fun login(phone: String, pwd: String) {
        request({ loginService.mobileLogin(phone, pwd) }, loginData)
    }


}