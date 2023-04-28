package com.meiling.oms.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.service.meService
import com.meiling.oms.bean.*
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
        cityName: String,
        id: String
    ) {
        request({
            branchInformationService.poiadd(
                id,
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

    //门店数据
    var shopBean = BaseLiveData<ArrayList<ShopBean>>()
    fun citypoi() {
        request({ meService.citypoi() }, shopBean)
    }

    var channel = BaseLiveData<ArrayList<ChannelX>>()

    //渠道获取数据
    fun getShopAndChannelVO() {
        request({ branchInformationService.getShopAndChannelVO() }, channel)
    }
    var channShop = BaseLiveData<ChannShopBean>()
    //渠道获取数据
    fun shop_list(channelId:String,poiId:String) {
        request({ branchInformationService.shop_list(channelId,poiId) }, channShop)
    }


    //美图绑定
    var urlauth = BaseLiveData<Unification>()
    fun urlauth(channelId: String, poiId: String, businessId: String) {
        request({ branchInformationService.urlauth(channelId, poiId, businessId) }, urlauth)
    }

    //  删除门店
    var deletePoi = BaseLiveData<String>()
    fun deletePoi(poiId: String) {
        request({ branchInformationService.deletePoi(poiId) }, deletePoi)
    }



}