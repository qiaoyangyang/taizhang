package com.meiling.account.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.*
import com.meiling.common.network.service.acceptanceCheckService

class VoucherinspectionViewModel(application: Application) :BaseViewModel(application) {

    val shopBean = BaseLiveData<ArrayList<ShopBean>>()

    val thrillBen = BaseLiveData<ArrayList<ThrillBen>>()
    val verifythrillBen = BaseLiveData<ArrayList<ThrillItem>>()

    val meituan = BaseLiveData<String>()
    val consume = BaseLiveData<String>()
    val cancel = BaseLiveData<String>()

    var Shop = BaseLiveData<Shop>()

    fun cityshop(type: String) {
        var channelId = ""
        if (type == "1") {//抖音
            channelId="32"
        }else if (type=="2"){
            channelId="7"

        }

        request({ acceptanceCheckService.cityshop( channelId, "0") }, shopBean)
    }

    fun prepare(shopId:String,int: Int,codeurl:String){
        if (int==0){
            request({ acceptanceCheckService.prepare("", codeurl, shopId) }, thrillBen)

        }else{
            request({ acceptanceCheckService.prepare(codeurl, "", shopId) }, thrillBen)

        }

    }
    fun verify(shopId:String,code:String){

        request({ acceptanceCheckService.verify( code, shopId) }, verifythrillBen)

    }

    fun mttgprepare(couponCode:String,shopId:String){
        request({ acceptanceCheckService.mttgprepare( couponCode, shopId) }, meituan)
    }
    fun consume(couponCode:String,count:String,shopId:String){
        request({ acceptanceCheckService.consume( couponCode, count,shopId) }, consume)
    }

    fun cancel(shopId:String){
        request({ acceptanceCheckService.cancel( shopId) }, cancel)
    }

}