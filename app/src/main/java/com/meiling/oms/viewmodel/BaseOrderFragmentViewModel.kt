package com.meiling.oms.viewmodel

import android.app.Application
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.CancelOrderSend
import com.meiling.common.network.data.OrderDto
import com.meiling.common.network.data.StatusCountDto
import com.meiling.common.network.service.homeService
import com.meiling.common.network.service.orderDisService
import retrofit2.http.Query

class BaseOrderFragmentViewModel(application: Application) : BaseViewModel(application) {

    var orderList = BaseLiveData<OrderDto>()
    var statusCountDto = BaseLiveData<StatusCountDto>()
    var cancelOrderDto = BaseLiveData<String>()
    var anyDto = BaseLiveData<Any>()

    //    logisticsStatus：0.待配送  20.带抢单 30.待取货 50.配送中 70.取消 80.已送达
    fun orderList(
        logisticsStatus: String,
        startTime: String,
        endTime: String,
        businessNumberType: String,//1.订单编号 2.商户单号 3.商会退款 4.交易流水
        pageIndex: Int,
        orderTime: String = "1",//1.下单时间，2 收货时间，出货时间 4,完成时间
        pageSize: String = "20",
        deliverySelect: String = "0",
        isValid: String = "",//全部，1。有效，0。无效
        businessNumber: String = "",
        selectText: String = "",
        channelId: String = "0"//渠道全部传null,根据返回渠道
    ) {
        request({
            homeService.orderStatus(
                logisticsStatus,
                startTime,
                endTime,
                businessNumberType,
                pageIndex.toString(),
                orderTime,
                pageSize,
                deliverySelect,
                isValid,
                businessNumber = businessNumber,
                selectText = selectText,
                channelId = channelId
            )
        }, orderList)
    }

    fun statusCount(
        logisticsStatus: String,
        startTime: String,
        endTime: String,
        businessNumberType: String,
        pageIndex: String,
        orderTime: String = "1",
        pageSize: String = "20",
        deliverySelect: String = "0",
        isValid: String = "",
        businessNumber: String = "",
        channelId: String = "0"
    ) {
        request({
            homeService.statusCount(
                logisticsStatus,
                startTime,
                endTime,
                businessNumberType,
                pageIndex,
                orderTime,
                pageSize,
                deliverySelect,
                isValid,
                businessNumber,
                channelId = channelId
            )
        }, statusCountDto)
    }

    fun cancelOrder(cancelCouponSum: CancelOrderSend) {
        request({ orderDisService.cancelOrderSend(cancelCouponSum) }, cancelOrderDto)
    }

    fun getPrint(
        sourceId: String,
        shopId: String,
        printTemplateType: String
    ) {
        request({ orderDisService.print(sourceId, shopId, printTemplateType) }, anyDto)
    }

}