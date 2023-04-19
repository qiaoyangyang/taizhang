package com.meiling.oms.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.oms.bean.BranchInformation
import com.meiling.oms.bean.PoiVo
import com.meiling.oms.bean.PoiVoBean
import com.meiling.oms.service.branchInformationService

class StoreManagementViewModel (application: Application) : BaseViewModel(application) {
    /**
     *门店列表
     * */
    var dataList = BaseLiveData<BranchInformation>()
    fun poilis() {
        request({ branchInformationService.poilis() }, dataList)
    }

    //  详情
   var poidata = BaseLiveData<PoiVoBean>()
   var PoiVoBean = MutableLiveData<PoiVoBean>()
    init {
        PoiVoBean.value=PoiVoBean()
    }
    fun poi(id:String) {
        request({ branchInformationService.poi(id) }, poidata)
    }

}