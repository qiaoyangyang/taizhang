package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.*
import com.meiling.common.network.service.dataService
import com.meiling.common.network.service.orderDisService

class OrderDisFragmentViewModel(application: Application) : BaseViewModel(application) {

    /**
     * 配送
     * */
    var orderSendAddress = BaseLiveData<OrderSendAddress>()
    fun getOrderAndPoiDeliveryDate(poiId: String, orderId: String, logisticsType: String) {
        request(
            { orderDisService.getOrderAndPoiDeliveryDate(poiId, orderId, logisticsType) },
            orderSendAddress
        )
    }

    /**
     * 发起获取物流商
     * */
    var orderSendConfirmList = BaseLiveData<ArrayList<OrderSendChannel>>()
    fun orderSendConfirm(orderSendRequest: OrderSendRequest) {
        request(
            { orderDisService.orderSendConfirm(orderSendRequest) },
            orderSendConfirmList
        )
    }

    /**
     * 发起配送
     * */
    var sendSuccess = BaseLiveData<Any>()
    fun insertOrderSend(logisticsConfirmDtoList: LogisticsConfirmDtoList) {
        request(
            { orderDisService.insertOrderSend(logisticsConfirmDtoList) },
            sendSuccess
        )
    }
//    /**
//     * 配送
//     * */
//    var orderSendAddress = BaseLiveData<OrderSendAddress>()
//    fun getOrderAndPoiDeliveryDate(poiId: String, orderId: String, logisticsType: String) {
//        request(
//            { orderDisService.getOrderAndPoiDeliveryDate(poiId, orderId, logisticsType) },
//            orderSendAddress
//        )
//    }

}