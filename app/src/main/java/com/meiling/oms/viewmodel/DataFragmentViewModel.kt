package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.ResultData
import com.meiling.common.network.data.*
import com.meiling.common.network.service.dataService
import retrofit2.http.GET

class DataFragmentViewModel(application: Application) : BaseViewModel(application) {



    /**
     * 配送
     * */
    var dataList = BaseLiveData<DataDisDto>()
    fun dataDisList(dataDisDto: DataListDto) {
        request({ dataService.dataList(dataDisDto) }, dataList)
    }

    var dataHistoryList = BaseLiveData<DataDisDto>()
    fun dataHistoryDisList(dataDisDto: DataListDto) {
        request({ dataService.dataList(dataDisDto) }, dataHistoryList)
    }

    /**
     * 渠道
     * */
    var channelDataList = BaseLiveData<MutableList<ChannelDataList>>()
    fun channelDataList(dataDisDto: DataListDto) {
        request({ dataService.channelDataList(dataDisDto) }, channelDataList)
    }
    var channelHistoryDataList = BaseLiveData<MutableList<ChannelDataList>>()
    fun channelHistoryDataList(dataDisDto: DataListDto) {
        request({ dataService.channelDataList(dataDisDto) }, channelHistoryDataList)
    }

    /**
     * 门店数据
     * **/

    var dataShopList = BaseLiveData<DataShopShow>()
    fun shopData(dataShop: DataShop) {
        request({ dataService.shopData(dataShop) }, dataShopList)
    }

    var shopDataList = BaseLiveData<DataShopList>()
    fun shopDataList(dataDisDto: DataListDto) {
        request({ dataService.shopDataList(dataDisDto) }, shopDataList)
    }
    var shopHistoryDataList = BaseLiveData<DataShopShow>()
    fun shopHistoryDataList(dataDisDto: DataShop) {
        request({ dataService.shopData(dataDisDto) }, shopHistoryDataList)
    }

    /**
     * 选择门店
     * */


}