package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ByTenantId
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.service.meService
import com.meiling.oms.service.branchInformationService

class MyViewModel(application: Application) : BaseViewModel(application) {

    var disableAccountDto = BaseLiveData<Any>()
    fun disableAccount(userViewId: String){
        request({ meService.disableAccount(userViewId) }, disableAccountDto)
    }

    //门店数据
    var shopBean = BaseLiveData<ArrayList<ShopBean>>()
    fun citypoi(){
        request({ meService.citypoi() }, shopBean)
    }


}