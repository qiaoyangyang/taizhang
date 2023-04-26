package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ForgetDto
import com.meiling.common.network.data.LoginDto
import com.meiling.common.network.service.loginService
import com.meiling.oms.widget.showToast

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val sendCode = BaseLiveData<String>()
    val loginData = BaseLiveData<LoginDto>()
    val forgetData = BaseLiveData<ForgetDto>()
    val repData = BaseLiveData<Any>()

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

    fun resetPwd(account: String, phone: String, code: String, pwd: String) {
        request({ loginService.resetPwd(account, phone, code, pwd) }, repData)
    }

    fun businessCode( phone: String, code: String) {
        request({ loginService.businessCode(phone, code) }, repData)
    }

}