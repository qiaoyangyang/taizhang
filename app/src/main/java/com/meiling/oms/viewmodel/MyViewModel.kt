package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.service.meService

class MyViewModel(application: Application) : BaseViewModel(application) {

    var disableAccountDto = BaseLiveData<Any>()
    fun disableAccount(userViewId: String){
        request({ meService.disableAccount(userViewId) }, disableAccountDto)
    }

}