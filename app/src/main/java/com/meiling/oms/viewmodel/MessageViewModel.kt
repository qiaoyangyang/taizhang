package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.service.meService

class MessageViewModel(application: Application) : BaseViewModel(application) {


    var msgCenterDto = BaseLiveData<Any>()
    fun getMessage() {
        request({ meService.getMsgCenter("", "", "") }, msgCenterDto)
    }

}