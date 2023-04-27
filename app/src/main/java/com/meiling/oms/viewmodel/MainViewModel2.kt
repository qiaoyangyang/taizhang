package com.meiling.oms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meiling.common.network.data.ByTenantId

class MainViewModel2() : ViewModel() {

    //  公共数据
    var getByTenantId = MutableLiveData<ByTenantId>()

    init {
        getByTenantId.value=ByTenantId()
    }
}