package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ByTenantId
import com.meiling.common.network.service.loginService
import com.meiling.common.network.service.meService
import com.meiling.common.utils.MMKVUtils

class MainViewModel(application: Application) : BaseViewModel(application) {


    var setUmTokenDto = BaseLiveData<Any>()
    fun setUmToken() {
        request(
            { loginService.imToken(MMKVUtils.getString("UmengToken")) }, setUmTokenDto)
    }
    //  公共数据
    var getByTenantId = BaseLiveData<ByTenantId>()
    fun getByTenantId() {
        request({ meService.getByTenantId() }, getByTenantId)
    }


}