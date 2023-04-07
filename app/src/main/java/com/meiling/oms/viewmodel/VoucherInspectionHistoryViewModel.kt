package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.data.Writeoffhistory
import com.meiling.common.network.service.acceptanceCheckService

class VoucherInspectionHistoryViewModel(application: Application) : BaseViewModel(application) {
    val writeoffhistory = BaseLiveData<Writeoffhistory>()
    fun coupon() {
        request({ acceptanceCheckService.
        coupon("2023-04-06", "2023-04-06", "156207178", "", "1", "20", "", "", "") }, writeoffhistory)
    }

}