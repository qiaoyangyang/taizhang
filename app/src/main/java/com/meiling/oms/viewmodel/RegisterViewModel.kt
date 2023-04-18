package com.meiling.oms.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.BusinessDto

class RegisterViewModel(application: Application):BaseViewModel(application) {

    var businessDto= MutableLiveData<BusinessDto>()
    init {
        businessDto.value= BusinessDto()
    }
}