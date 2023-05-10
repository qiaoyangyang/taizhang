package com.meiling.oms.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.constant.SPConstants
import com.meiling.common.network.data.ByTenantId
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.service.meService
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.bean.BranchInformation
import com.meiling.oms.bean.PoiVoBean
import com.meiling.oms.bean.*
import com.meiling.oms.service.branchInformationService
import okhttp3.Headers.Companion.toHeaders

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
                PoiVoBean?.value?.poiVo?.storeaddress!! + "@@" + PoiVoBean?.value?.poiVo?.etdetailedaddress,
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

    fun poiaddFromRegist(
        lat: String,
        lon: String,
        provinceCode: String,
        cityCode: String,
        districtCode: String,
        cityName: String,
        id: String,
    ) {
        request({
            branchInformationService.poiaddFromRegist(
                id,
                PoiVoBean?.value?.poiVo?.name!!,
                PoiVoBean?.value?.poiVo?.sinceCode!!,
                PoiVoBean?.value?.poiVo?.phone!!,
                PoiVoBean?.value?.poiVo?.storeaddress!! + "@@" + PoiVoBean?.value?.poiVo?.etdetailedaddress,
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
    //抖音返回列表
    var douyin = BaseLiveData<PageResult>()
    fun douurlauth(channelId: String, poiId: String, selectText: String) {
        request({ branchInformationService.douurlauth(channelId, poiId, selectText) }, douyin)
    }
    //抖音返回列表
    var bindShop = BaseLiveData<String>()
    fun bindShop(shopId: String,address:String,shopName:String,channelPoiId:String) {
        request({ branchInformationService.bindShop(shopId,address ,shopName,channelPoiId) }, bindShop)
    }
    //解绑
    var releasebind = BaseLiveData<String>()
    fun releasebind(channelId: String,shopId:String) {
        request({ branchInformationService.releasebind(channelId ,shopId) }, releasebind)
    }
    // 设置发货门店
    var updateShop = BaseLiveData<String>()
    fun updateShop(shopId: String,poiId:String) {
        request({ branchInformationService.updateShop(shopId ,poiId) }, updateShop)
    }

    //抖音返回列表
    var bindTenant = BaseLiveData<String>()
    fun bindTenant(accountId: String) {
        request({ branchInformationService.bindTenant(accountId) }, bindTenant)
    }

    //  删除门店
    var deletePoi = BaseLiveData<String>()
    fun deletePoi(poiId: String) {
        request({ branchInformationService.deletePoi(poiId) }, deletePoi)
    }
    //  是否绑定租户
    var isTenant = BaseLiveData<Boolean>()
    fun isTenant() {
        request({ branchInformationService.isTenant() }, isTenant)
    }



}