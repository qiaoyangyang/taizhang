package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.MMKVUtils

class HomeViewModel(application: Application) :BaseViewModel(application) {

    var setUmTokenDto = BaseLiveData<Any>()
    fun setUmToken() {
        request(
            { loginService.imToken(MMKVUtils.getString("UmengToken")) }, setUmTokenDto)
    }

}