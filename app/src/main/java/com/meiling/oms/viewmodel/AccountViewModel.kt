package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.AccountListDto
import com.meiling.common.network.data.ReqCreateAccount
import com.meiling.common.network.data.RequestAccount
import com.meiling.common.network.service.accountService

class AccountViewModel(application: Application) : BaseViewModel(application) {
    /**
     * 获取列表
     * */
    var accountListDto = BaseLiveData<AccountListDto>()
    var disableAccount = BaseLiveData<Any>()
    fun getAccountList(requestAccount: RequestAccount) {
        request({ accountService.orderStatus(requestAccount) }, accountListDto)
    }

    fun setDisableAccount(userViewId: String, accountStatus: String) {
        request({ accountService.disableAccount(userViewId, accountStatus) }, disableAccount)
    }

//    fun getPoiList(pageIndex: Int, pageSize: String) {
//        request({ accountService.getPoiList(pageIndex) }, disableAccount)
//    }

    fun saveAndUpdate(reqCreateAccount: ReqCreateAccount) {
        request({ accountService.saveAndUpdate(reqCreateAccount) }, disableAccount)
    }

}

