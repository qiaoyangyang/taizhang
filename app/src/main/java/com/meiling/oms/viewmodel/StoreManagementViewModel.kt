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

class StoreManagementViewModel(application: Application) : BaseViewModel(application) {
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
        PoiVoBean.value = PoiVoBean()
    }

    fun poi(id: String) {
        request({ branchInformationService.poi(id) }, poidata)
    }

    //新建
    var poiaddpoidata = BaseLiveData<String>()
    fun poiadd(
        lat: String,
        lon: String,
        provinceCode: String,
        cityCode: String,
        districtCode: String,
        cityName: String
    ) {
        request({
            branchInformationService.poiadd(
                PoiVoBean?.value?.poiVo?.name!!,
                PoiVoBean?.value?.poiVo?.sinceCode!!,
                PoiVoBean?.value?.poiVo?.phone!!,
                PoiVoBean?.value?.poiVo?.storeaddress!! + " " + PoiVoBean?.value?.poiVo?.etdetailedaddress,
                lat,
                lon,
                PoiVoBean?.value?.poiVo?.contactPerson!!,
                PoiVoBean?.value?.poiVo?.mobilePhone!!,
                provinceCode,
                cityCode,
                districtCode, cityName


            )
        }, poiaddpoidata)

    }



}