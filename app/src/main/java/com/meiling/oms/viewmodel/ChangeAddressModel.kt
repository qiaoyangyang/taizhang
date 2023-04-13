package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.service.homeService
import retrofit2.http.Path
import retrofit2.http.Query

class ChangeAddressModel(application: Application) : BaseViewModel(application) {

    var changeAddressSuccess = BaseLiveData<Any>()

    var recvAddr = BaseLiveData<String>()
    var recvAddrDeatil = BaseLiveData<String>()
    var lat = BaseLiveData<String>()
    var lon = BaseLiveData<String>()
    var arriveTime = BaseLiveData<String>()

    var isChangeAddress = BaseLiveData<Boolean>()

    fun changeAddress(
        orderId: String,
        recvName: String,
        recvPhone: String,
        recvAddr: String,
        lat: String,
        lon: String,
        arriveTime: String
    ) {
        request(
            {
                homeService.updateAddress(
                    orderId,
                    recvName,
                    recvPhone,
                    recvAddr,
                    lat,
                    lon,
                    arriveTime
                )
            }, changeAddressSuccess
        )

    }

    fun updateRemark(remark: String,orderId: String) {
        request(
            {
                homeService.updateRemark(
                    remark,orderId
                )
            }, changeAddressSuccess
        )

    }

}