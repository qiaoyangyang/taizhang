package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.service.acceptanceCheckService

class FindViewModel(application: Application) :BaseViewModel(application) {
    val shopBean = BaseLiveData<ArrayList<ShopBean>>()
    fun cityshop(type: String) {
        var channelId = ""
        if (type == "1") {//抖音
            channelId="32"
        }else if (type=="2"){
            channelId="7"

        }

        request({ acceptanceCheckService.cityshop( channelId, "0") }, shopBean)
    }

}