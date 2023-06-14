package com.meiling.account.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.RecordCodeNumber
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.data.Writeoffhistory
import com.meiling.common.network.service.acceptanceCheckService

class VoucherInspectionHistoryViewModel(application: Application) : BaseViewModel(application) {
    val writeoffhistory = BaseLiveData<Writeoffhistory>()
    val cancelstring = BaseLiveData<String>()
    val cancelmeituanstring = BaseLiveData<String>()
    val recordCodeNumber = BaseLiveData<RecordCodeNumber>()
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
    fun coupon(poiId: String,startDate:String,endDate:String,selectText:String,pageIndex:Int,pageSize:String,type:String,status:String,isVoucher:String) {
        request({
            acceptanceCheckService.coupon(
                startDate,
                endDate,
                poiId,
                selectText,
                pageIndex,
                pageSize,

                status,
                type,
                isVoucher
            )
        }, writeoffhistory)


    }
    fun codeNumber(poiId: String,startDate:String,endDate:String,selectText:String,pageIndex:Int,pageSize:String,type:String,status:String,isVoucher:String) {
        request({
            acceptanceCheckService.codeNumber(
                startDate,
                endDate,
                poiId,
                selectText,
                status,
                type,
                isVoucher
            )
        }, recordCodeNumber)

    }

    fun cancel(code: String, shopId: String) {
        request({
            acceptanceCheckService.cancel(code,shopId)
        },cancelstring)
    }
    fun meituancancel(code: String,shopId:String){
        request({ acceptanceCheckService.mttgcancel( code,shopId) }, cancelmeituanstring)
    }

}