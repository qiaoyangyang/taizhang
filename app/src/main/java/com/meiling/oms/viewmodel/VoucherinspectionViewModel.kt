package com.meiling.oms.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.service.acceptanceCheckService

class VoucherinspectionViewModel(application: Application) :BaseViewModel(application) {

    val shopBean = BaseLiveData<ArrayList<ShopBean>>()
    val doun = BaseLiveData<ArrayList<String>>()
    var Shop = BaseLiveData<Shop>()
    fun cityshop(type: String) {
        var channelId = ""
        if (type == "1") {//抖音
            channelId="11"
        }else if (type=="2"){
            channelId="7"

        }

        request({ acceptanceCheckService.cityshop("1,2", channelId, "1") }, shopBean)
    }
    fun prepare(shopId:String,int: Int,codeurl:String){

        request({ acceptanceCheckService.prepare(codeurl, codeurl, shopId) }, doun)

    }
}