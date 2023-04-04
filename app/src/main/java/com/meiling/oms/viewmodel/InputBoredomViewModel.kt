package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.service.acceptanceCheckService
import com.meiling.common.network.service.loginService

class InputBoredomViewModel(application: Application) : BaseViewModel(application) {
    val shopBean = BaseLiveData<ArrayList<ShopBean>>()
    fun cityshop(type: String) {
        var channelId = ""
        if (type == "1") {
            channelId="11"
        }

        request({ acceptanceCheckService.cityshop("1,2", channelId, "1") }, shopBean)
    }


}