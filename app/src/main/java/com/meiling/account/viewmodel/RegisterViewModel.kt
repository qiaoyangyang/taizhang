package com.meiling.account.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.BusinessDto
import com.meiling.common.network.data.CreateSelectPoiDto
import com.meiling.common.network.data.PrinterConfigDto
import com.meiling.common.network.data.Printing
import com.meiling.common.network.service.accountService
import com.meiling.common.network.service.meService

class RegisterViewModel(application: Application):BaseViewModel(application) {

    var businessDto= MutableLiveData<BusinessDto>()
    init {
        businessDto.value= BusinessDto()
    }
    var printChannelList = BaseLiveData<ArrayList<Printing>>()
    fun getprintChannelList() {
        request({ meService.printChannelList() }, printChannelList)
    }
    var printDetail = BaseLiveData<Printing>()
    fun printDetail(deviceID:String) {
        request({ meService.printDetail(deviceID) }, printDetail)
    }
    var delDev = BaseLiveData<String>()
    fun delDev(deviceID:String) {
        request({ meService.delDev(deviceID) }, delDev)
    }
    var addDev = BaseLiveData<String>()
    fun addDev(printerConfigDto: PrinterConfigDto) {
        request({ meService.addDev(printerConfigDto) }, delDev)
    }
    var update = BaseLiveData<String>()
    fun update(printerConfigDto: PrinterConfigDto) {
        request({ meService.update(printerConfigDto) }, delDev)
    }
    var getPoiListsize = BaseLiveData<CreateSelectPoiDto>()
    fun getPoiList(){
        request({ accountService.getPoiList(1,"100") }, getPoiListsize)
    }

}