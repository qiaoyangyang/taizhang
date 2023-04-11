package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.BalanceDto
import com.meiling.common.network.data.RechargeRecordListReq
import com.meiling.common.network.data.RechargeRequest
import com.meiling.common.network.service.meService

class RechargeViewModel(application: Application) : BaseViewModel(application) {

    var rechargeDto = BaseLiveData<String>()
    var balance = BaseLiveData<BalanceDto>()
    var rechargeRecord = BaseLiveData<Any>()

    fun rechargeRequest(rechargeRequest: RechargeRequest) {
        request({ meService.rechargeRequest(rechargeRequest) }, rechargeDto)
    }

    fun getBalance() {
        request({ meService.getPayAccountAmountVo() }, balance)
    }

    fun getRecord(rechargeRecordListReq: RechargeRecordListReq) {
        request({ meService.getRecord(rechargeRecordListReq) }, rechargeRecord)
    }
}