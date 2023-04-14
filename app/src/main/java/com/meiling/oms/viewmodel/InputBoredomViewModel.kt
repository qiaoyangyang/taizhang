package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.data.ThrillBen
import com.meiling.common.network.data.ThrillItem
import com.meiling.common.network.service.acceptanceCheckService
import com.meiling.common.network.service.loginService

class InputBoredomViewModel(application: Application) : BaseViewModel(application) {
    val shopBean = BaseLiveData<ArrayList<ShopBean>>()
    val thrillBen = BaseLiveData<ArrayList<ThrillBen>>()
    val ThrillItem = BaseLiveData<ArrayList<ThrillItem>>()
    val meituan = BaseLiveData<String>()
    var Shop = BaseLiveData<Shop>()
    fun cityshop(type: String) {
        var channelId = ""
        if (type == "1") {
            channelId="32"
        }

        request({ acceptanceCheckService.cityshop( channelId, "0") }, shopBean)
    }
    fun prepare(shopId:String,int: Int,codeurl:String){

        request({ acceptanceCheckService.prepare(codeurl, "", shopId) }, thrillBen)

    }

    fun verify(shopId:String,code:String){

        request({ acceptanceCheckService.verify( code, shopId) }, ThrillItem)

    }
    fun mttgprepare(couponCode:String,shopId:String){
        request({ acceptanceCheckService.mttgprepare( couponCode, shopId) }, meituan)
    }


}