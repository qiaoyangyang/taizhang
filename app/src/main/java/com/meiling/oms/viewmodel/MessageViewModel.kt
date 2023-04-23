package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.MessageDto
import com.meiling.common.network.service.meService

class MessageViewModel(application: Application) : BaseViewModel(application) {


    var msgCenterDto = BaseLiveData<MessageDto>()
    fun getMessage(pageIndex: Int) {
        request({ meService.getMsgCenter(pageIndex, "50") }, msgCenterDto)
    }

}