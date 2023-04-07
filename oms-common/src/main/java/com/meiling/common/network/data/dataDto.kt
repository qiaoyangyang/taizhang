package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName

data class DataDisDto(
    @SerializedName("deliveryConsumeLists")
    var deliveryConsumeLists: List<DeliveryConsumeLists?>?,
    @SerializedName("sumAmount")
    var sumAmount: String?,//配送
    @SerializedName("sumAmountAndTips")
    var sumAmountAndTips: String?,//总计
    @SerializedName("sumOrderNum")
    var sumOrderNum: String?,//发单数量
    @SerializedName("sumTips")
    var sumTips: String?,//消费
    @SerializedName("sumAvg")
    var sumAvg: String?//客单价
) {
    data class DeliveryConsumeLists(
        @SerializedName("amount")
        var amount: String?,//配送费
        @SerializedName("amountAndTips")
        var amountAndTips: String?,
        @SerializedName("avgAmount")
        var avgAmount: String?,//客单价
        @SerializedName("logisticsName")
        var logisticsName: String?,
        @SerializedName("logisticsType")
        var logisticsType: String?,
        @SerializedName("orderNum")
        var orderNum: String?,//发单数量
        @SerializedName("poiId")
        var poiId: String?,
        @SerializedName("tips")
        var tips: String?//消费
    )
}

data class ChannelDataList(
    @SerializedName("channelId")
    var channelId: String?,
    @SerializedName("channelName")
    var channelName: String?,
    @SerializedName("incomeNum")
    var incomeNum: String?,
    @SerializedName("orderNum")
    var orderNum: String?
)

data class DataShopList(
    @SerializedName("date")
    var date: String?,
    @SerializedName("orderStaticsGroupByHours")
    var orderStaticsGroupByHours: List<OrderStaticsGroupByHour?>?,
    @SerializedName("total")
    var total: Int?
) {
    data class OrderStaticsGroupByHour(
        @SerializedName("count")
        var count: String?,
        @SerializedName("hour")
        var hour: String?,
        @SerializedName("price")
        var price: String?
    )
}

data class DataShopShow(
    @SerializedName("actualPayPriceTotal")
    var actualPayPriceTotal: String?,
    @SerializedName("costTotal")
    var costTotal: String?,
    @SerializedName("deliveryFeeTotal")
    var deliveryFeeTotal: String?,
    @SerializedName("discountPriceTotal")
    var discountPriceTotal: String?,
    @SerializedName("discountRatio")
    var discountRatio: String?,
    @SerializedName("orderCountTotal")
    var orderCountTotal: String?,
    @SerializedName("orderRefundTotal")
    var orderRefundTotal: String?,
    @SerializedName("payInfo")
    var payInfo: PayInfo?,
    @SerializedName("payIntegralNumTotal")
    var payIntegralNumTotal: String?,
    @SerializedName("payPriceTotal")
    var payPriceTotal: String?,
    @SerializedName("refundCountTotal")
    var refundCountTotal: String?,
    @SerializedName("refundInfo")
    var refundInfo: RefundInfo?,
    @SerializedName("refundIntegralNumTotal")
    var refundIntegralNumTotal: String?,
    @SerializedName("refundPriceTotal")
    var refundPriceTotal: String?,
    @SerializedName("saleGoodsCountTotal")
    var saleGoodsCountTotal: String?,
    @SerializedName("serviceChargeTotal")
    var serviceChargeTotal: String?,
    @SerializedName("totalPriceTotal")
    var totalPriceTotal: String?,
    @SerializedName("validGoodsCountTotal")
    var validGoodsCountTotal: String?,
    @SerializedName("validPriceTotal")
    var validPriceTotal: String?,
    @SerializedName("wipeZeroPriceTotal")
    var wipeZeroPriceTotal: String?
) {
    data class PayInfo(
        @SerializedName("其他")
        var 其他: String?
    )

    data class RefundInfo(
        @SerializedName("其他")
        var 其他: String?
    )
}
