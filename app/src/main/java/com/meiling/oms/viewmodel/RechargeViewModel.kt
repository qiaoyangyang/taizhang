package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.*
import com.meiling.common.network.service.myRechargeService

class RechargeViewModel(application: Application) : BaseViewModel(application) {

    var rechargeDto = BaseLiveData<String>()
    var balance = BaseLiveData<BalanceDto>()
    var rechargeRecord = BaseLiveData<RechargeRecordDto>()
    var financialRecord = BaseLiveData<FinancialRecord>()
    var financialRecordDetail = BaseLiveData<FinancialRecordDetail>()
    var rechargeListDto = BaseLiveData<ArrayList<RechargeDto>>()

    fun rechargeRequest(rechargeRequest: RechargeRequest) {
        request({ myRechargeService.rechargeRequest(rechargeRequest) }, rechargeDto)
    }

    fun getBalance() {
        request({ myRechargeService.getPayAccountAmountVo() }, balance)
    }

    fun getRecord(rechargeRecordListReq: RechargeRecordListReq) {
        request({ myRechargeService.getRecord(rechargeRecordListReq) }, rechargeRecord)
    }

    fun getFinancialRecord(rechargeRecordListReq: RechargeRecordListReq) {
        request({ myRechargeService.getFinancialRecord(rechargeRecordListReq) }, financialRecord)
    }

    fun getFinancialRecordDetail(
        viewId: String,
        pageIndex: String,
        pageSize: String
    ) {
        request(
            { myRechargeService.getFinancialRecordDetail(viewId, pageIndex, pageSize) },
            financialRecordDetail
        )
    }

    fun getFinancialRecordDetail() {
        request(
            { myRechargeService.tenantWalletList() },
            rechargeListDto
        )
    }
}