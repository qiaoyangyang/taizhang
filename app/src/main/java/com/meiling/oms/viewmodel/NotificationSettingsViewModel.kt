package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.utils.NotificationDto

class NotificationSettingsViewModel(application: Application) : BaseViewModel(application) {



    var notificationDto = BaseLiveData<ArrayList<NotificationDto>>()
    fun notificationSettings() {

    }
}