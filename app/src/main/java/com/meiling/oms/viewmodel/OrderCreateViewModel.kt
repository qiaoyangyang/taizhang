package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.*
import com.meiling.common.network.service.orderCreateService

class OrderCreateViewModel(application: Application) : BaseViewModel(application) {

    var cityPoiOfflineDto = BaseLiveData<ArrayList<ShopBean>>()
    var saveCreateDto = BaseLiveData<OrderDetail>()
    var orderCreateAddressDiscern = BaseLiveData<OrderCreateAddressDiscern>()
    fun getCityPoiOffline() {
        request({ orderCreateService.getCityPoiOffline() }, cityPoiOfflineDto)
    }

    //"arriveTime":"",//收货时间
//"channelId":"11",//渠道ID
//"lat":"",//维度
//"lon":"",//经度
//"poiId":"",//发货门店id
//"recvAddr":"",//收货地址
//"recvName":"",//收货人
//"recvPhone":"",//收货人电话
//"remark":"",//备注
//"shopId":"",//店铺id
//"deliveryType":"",//配送类型(1配送,2自提,3,现售)
//"selfGoodsDtoList":goodsArr
    fun saveOrder(orderCreateSave: OrderCreateSaveDto) {
        if (orderCreateSave.deliveryType == "1") {
            request({
                orderCreateService.saveOrder(
                    OrderCreateSaveDto(
                        arriveTime = orderCreateSave.arriveTime,
                        channelId = "11",
                        poiId = orderCreateSave.poiId,
                        lat = orderCreateSave.lat,
                        lon = orderCreateSave.lon,
                        deliveryType = orderCreateSave.deliveryType,
                        recvAddr = orderCreateSave.recvAddr,
                        recvName = orderCreateSave.recvName,
                        recvPhone = orderCreateSave.recvPhone,
                        remark = orderCreateSave.remark,
                        selfGoodsDtoList = orderCreateSave.selfGoodsDtoList
                    )
                )
            }, saveCreateDto)
        } else {
            request({
                orderCreateService.saveOrder(
                    OrderCreateSaveDto(
                        arriveTime = orderCreateSave.arriveTime,
                        channelId = "11",
                        poiId = orderCreateSave.poiId,
                        deliveryType = orderCreateSave.deliveryType,
                        recvName = orderCreateSave.recvName,
                        recvPhone = orderCreateSave.recvPhone,
                        remark = orderCreateSave.remark,
                        selfGoodsDtoList = orderCreateSave.selfGoodsDtoList
                    )
                )
            }, saveCreateDto)
        }

    }


    fun addressParse(keyWorlds: String) {
        request({ orderCreateService.addressParse(keyWorlds) }, orderCreateAddressDiscern)
    }
}