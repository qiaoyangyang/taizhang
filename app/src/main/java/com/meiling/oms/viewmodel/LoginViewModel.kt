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
import com.meiling.common.network.data.ForgetDto
import com.meiling.common.network.data.LoginDto
import com.meiling.common.network.service.LoginService
import com.meiling.common.network.service.loginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val sendCode = BaseLiveData<String>()
    val loginData = BaseLiveData<LoginDto>()
    val forgetData = BaseLiveData<ForgetDto>()

    fun sendCode(phone: String) {
        request({ loginService.sendCode(phone) }, sendCode)
    }

    fun mobileLogin(phone: String, pwd: String) {
        request({ loginService.mobileLogin(phone, pwd) }, loginData)
    }

    fun accountLogin(phone: String, pwd: String) {
        request({ loginService.login(phone, pwd) }, loginData)
    }

    fun userNameVerify(account: String) {
        request({ loginService.userNameVerify(account) }, forgetData)
    }


}