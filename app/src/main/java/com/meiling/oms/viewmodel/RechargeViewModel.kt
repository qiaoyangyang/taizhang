package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.*
import com.meiling.common.network.service.meService
import retrofit2.http.Query

class RechargeViewModel(application: Application) : BaseViewModel(application) {

    var rechargeDto = BaseLiveData<String>()
    var balance = BaseLiveData<BalanceDto>()
    var rechargeRecord = BaseLiveData<RechargeRecordDto>()
    var financialRecord = BaseLiveData<FinancialRecord>()
    var financialRecordDetail = BaseLiveData<FinancialRecordDetail>()

    fun rechargeRequest(rechargeRequest: RechargeRequest) {
        request({ meService.rechargeRequest(rechargeRequest) }, rechargeDto)
    }

    fun getBalance() {
        request({ meService.getPayAccountAmountVo() }, balance)
    }

    fun getRecord(rechargeRecordListReq: RechargeRecordListReq) {
        request({ meService.getRecord(rechargeRecordListReq) }, rechargeRecord)
    }

    fun getFinancialRecord(rechargeRecordListReq: RechargeRecordListReq) {
        request({ meService.getFinancialRecord(rechargeRecordListReq) }, financialRecord)
    }

    fun getFinancialRecordDetail(
        viewId: String,
        pageIndex: String,
        pageSize: String
    ) {
        request(
            { meService.getFinancialRecordDetail(viewId, pageIndex, pageSize) },
            financialRecordDetail
        )
    }
}