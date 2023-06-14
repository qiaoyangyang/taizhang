package com.meiling.account.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.service.dataService

class DataViewModel(application: Application) :BaseViewModel(application) {
    val shopBean = BaseLiveData<ArrayList<ShopBean>>()
    var Shop = BaseLiveData<Shop>()

    fun cityShop(type: String) {
        request({ dataService.cityPoi(type) }, shopBean)
    }

}