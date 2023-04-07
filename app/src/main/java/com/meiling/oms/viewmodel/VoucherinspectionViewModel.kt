package com.meiling.oms.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.data.ThrillBen
import com.meiling.common.network.service.acceptanceCheckService

class VoucherinspectionViewModel(application: Application) :BaseViewModel(application) {

    val shopBean = BaseLiveData<ArrayList<ShopBean>>()
    val thrillBen = BaseLiveData<ArrayList<ThrillBen>>()
    var Shop = BaseLiveData<Shop>()
    fun cityshop(type: String) {
        var channelId = ""
        if (type == "1") {//抖音
            channelId="32"
        }else if (type=="2"){
            channelId="7"

        }

        request({ acceptanceCheckService.cityshop("1,2", channelId, "1") }, shopBean)
    }
    fun prepare(shopId:String,int: Int,codeurl:String){
        if (int==0){

        }
        request({ acceptanceCheckService.prepare("", codeurl, shopId) }, thrillBen)

    }
    fun verify(shopId:String,code:String){

        request({ acceptanceCheckService.verify( code, shopId) }, thrillBen)

    }
}