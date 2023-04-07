package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName


data class OrderDto(
    @SerializedName("content")
    var content: ArrayList<Content?>,
    @SerializedName("contentT")
    var contentT: Any?,
    @SerializedName("extra")
    var extra: Any?,
    @SerializedName("pageIndex")
    var pageIndex: Int?,
    @SerializedName("pageSize")
    var pageSize: Int?,
    @SerializedName("total")
    var total: Int?,
    @SerializedName("totalPage")
    var totalPage: Int?
) {
    data class Content(
        @SerializedName("adminUserName")
        var adminUserName: String?,
        @SerializedName("afterSaleInfo")
        var afterSaleInfo: String?,
        @SerializedName("allPaymentTypesVo")
        var allPaymentTypesVo: AllPaymentTypesVo?,
        @SerializedName("canBeReselect")
        var canBeReselect: Boolean?,
        @SerializedName("cardNo")
        var cardNo: String?,
        @SerializedName("cardTypeName")
        var cardTypeName: String?,
        @SerializedName("channelLogo")
        var channelLogo: String?,
        @SerializedName("channelName")
        var channelName: String?,
        @SerializedName("city")
        var city: String?,
        @SerializedName("commission")
        var commission: Any?,
        @SerializedName("deliveryConsume")
        var deliveryConsume: Any?,
        @SerializedName("deliveryConsumeLog")
        var deliveryConsumeLog: Any?,
        @SerializedName("depositPrice")
        var depositPrice: Int?,
        @SerializedName("discountAmount")
        var discountAmount: Int?,
        @SerializedName("discountTotalPrice")
        var discountTotalPrice: Any?,
        @SerializedName("expectedBalancePayTime")
        var expectedBalancePayTime: String?,
        @SerializedName("finalPrice")
        var finalPrice: Int?,
        @SerializedName("fixedResaleOrderPayDepositPriceTypeVoList")
        var fixedResaleOrderPayDepositPriceTypeVoList: List<Any?>?,
        @SerializedName("fixedResaleOrderPayFinalPriceTypeVoList")
        var fixedResaleOrderPayFinalPriceTypeVoList: List<Any?>?,
        @SerializedName("fixedResaleOrderStatus")
        var fixedResaleOrderStatus: Any?,
        @SerializedName("fixedResaleOrderType")
        var fixedResaleOrderType: Any?,
        @SerializedName("giftcardBalance")
        var giftcardBalance: Any?,
        @SerializedName("goodsVoList")
        var goodsVoList: ArrayList<GoodsVo?>?,
        @SerializedName("hasMarkPay")
        var hasMarkPay: Int?,
        @SerializedName("isShowCouponCode")
        var isShowCouponCode: Any?,
        @SerializedName("isShowUserInfo")
        var isShowUserInfo: Any?,
        @SerializedName("miniProgramsMa")
        var miniProgramsMa: Any?,
        @SerializedName("order")
        var order: Order?,
        @SerializedName("order62Code")
        var order62Code: Any?,
        @SerializedName("orderModifyRecord")
        var orderModifyRecord: String?,
        @SerializedName("orderName")
        var orderName: String?,
        @SerializedName("orderPhone")
        var orderPhone: String?,
        @SerializedName("payChannels")
        var payChannels: Any?,
        @SerializedName("payExtendVos")
        var payExtendVos: List<Any?>?,
        @SerializedName("payPriceList")
        var payPriceList: List<Any?>?,
        @SerializedName("payType")
        var payType: Any?,
        @SerializedName("phone")
        var phone: String?,
        @SerializedName("preOrder")
        var preOrder: Any?,
        @SerializedName("prescriptionImage")
        var prescriptionImage: List<Any?>?,
        @SerializedName("promotionMap")
        var promotionMap: List<Any?>?,
        @SerializedName("refund")
        var refund: Int?,
        @SerializedName("shipmentShopName")
        var shipmentShopName: String?,
        @SerializedName("shopName")
        var shopName: String?,
        @SerializedName("spreadFee")
        var spreadFee: Any?,
        @SerializedName("statusColor")
        var statusColor: String?,
        @SerializedName("statusName")
        var statusName: String?,
        @SerializedName("supplierPrice")
        var supplierPrice: Int?,
        @SerializedName("tradeNo")
        var tradeNo: Any?,
        @SerializedName("tradeOut")
        var tradeOut: Any?,

    ) {
        data class AllPaymentTypesVo(
            @SerializedName("其他")
            var 其他: Double?
        )

        data class GoodsVo(
            @SerializedName("actualAmount")
            var actualAmount: Double?,
            @SerializedName("actualPrice")
            var actualPrice: Double?,
            @SerializedName("avater")
            var avater: String?,
            @SerializedName("baseStockUnitName")
            var baseStockUnitName: Any?,
            @SerializedName("baseStockUnitStatus")
            var baseStockUnitStatus: Any?,
            @SerializedName("baseStockUnitTypeId")
            var baseStockUnitTypeId: Any?,
            @SerializedName("baseStockUnitViewId")
            var baseStockUnitViewId: Any?,
            @SerializedName("baseUnitName")
            var baseUnitName: Any?,
            @SerializedName("baseUnitStatus")
            var baseUnitStatus: Any?,
            @SerializedName("baseUnitViewId")
            var baseUnitViewId: Any?,
            @SerializedName("channelId")
            var channelId: Any?,
            @SerializedName("channelImg")
            var channelImg: Any?,
            @SerializedName("countWithUnit")
            var countWithUnit: String?,
            @SerializedName("currentGoodsUnitStockNumber")
            var currentGoodsUnitStockNumber: Any?,
            @SerializedName("customCode")
            var customCode: String?,
            @SerializedName("depositPrice")
            var depositPrice: Double?,
            @SerializedName("gid")
            var gid: String?,
            @SerializedName("gname")
            var gname: String?,
            @SerializedName("goodsStockDeduction")
            var goodsStockDeduction: String?,
            @SerializedName("goodsTotalCost")
            var goodsTotalCost: Double?,
            @SerializedName("goodsType")
            var goodsType: Int?,
            @SerializedName("goodsUnitRatio")
            var goodsUnitRatio: Any?,
            @SerializedName("goodsUnitViewId")
            var goodsUnitViewId: String?,
            @SerializedName("modelType")
            var modelType: Any?,
            @SerializedName("multiUnit")
            var multiUnit: Any?,
            @SerializedName("multiUnitCurrentStock")
            var multiUnitCurrentStock: Any?,
            @SerializedName("negativeNumber")
            var negativeNumber: Double?,
            @SerializedName("number")
            var number: Int?,
            @SerializedName("orderGoodsId")
            var orderGoodsId: Int?,
            @SerializedName("originalPrice")
            var originalPrice: Double?,
            @SerializedName("parentGoodsUnitViewId")
            var parentGoodsUnitViewId: Any?,
            @SerializedName("price")
            var price: String?,
            @SerializedName("profit")
            var profit: Double?,
            @SerializedName("reasonsforpricechange")
            var reasonsforpricechange: String?,
            @SerializedName("refundNum")
            var refundNum: Int?,
            @SerializedName("refundPrice")
            var refundPrice: Double?,
            @SerializedName("shopNames")
            var shopNames: Any?,
            @SerializedName("sku")
            var sku: String?,
            @SerializedName("spuId")
            var spuId: Any?,
            @SerializedName("status")
            var status: Int?,
            @SerializedName("supplierPrice")
            var supplierPrice: Int?,
            @SerializedName("totalPrice")
            var totalPrice: Double?,
            @SerializedName("unitPrice")
            var unitPrice: Double?,
            @SerializedName("unitSpecBarcode")
            var unitSpecBarcode: Any?,
            @SerializedName("unitType")
            var unitType: Int?,
            @SerializedName("unitTypeId")
            var unitTypeId: Any?,
            @SerializedName("unitUseBusinessTypeStr")
            var unitUseBusinessTypeStr: Any?,
            @SerializedName("unitUseType")
            var unitUseType: Any?,
            @SerializedName("specs")
            var specs: String?
        )

        data class Order(
            @SerializedName("activityFee")
            var activityFee: Double?,
            @SerializedName("actualIncome")
            var actualIncome: Double?,
            @SerializedName("actualPayPrice")
            var actualPayPrice: Double?,
            @SerializedName("addressDesensitization")
            var addressDesensitization: String?,
            @SerializedName("adminUserId")
            var adminUserId: Int?,
            @SerializedName("arriveTime")
            var arriveTime: String?,
            @SerializedName("arriveTimeDate")
            var arriveTimeDate: String?,
            @SerializedName("attachment")
            var attachment: String?,
            @SerializedName("auditId")
            var auditId: Any?,
            @SerializedName("boxPriceTotal")
            var boxPriceTotal: Any?,
            @SerializedName("cardMoney")
            var cardMoney: Any?,
            @SerializedName("cardNo")
            var cardNo: Any?,
            @SerializedName("cardType")
            var cardType: Any?,
            @SerializedName("changePrice")
            var changePrice: Double?,
            @SerializedName("channelCreateTime")
            var channelCreateTime: String?,
            @SerializedName("channelDaySn")
            var channelDaySn: Int?,
            @SerializedName("channelId")
            var channelId: Int?,
            @SerializedName("channelOrderNum")
            var channelOrderNum: String?,
            @SerializedName("completeTime")
            var completeTime: Any?,
            @SerializedName("couponCode")
            var couponCode: String?,
            @SerializedName("couponName")
            var couponName: Any?,
            @SerializedName("createTime")
            var createTime: Long?,
            @SerializedName("deliverName")
            var deliverName: String?,
            @SerializedName("deliverPhone")
            var deliverPhone: String?,
            @SerializedName("deliverPrice")
            var deliverPrice: Int?,
            @SerializedName("deliveryFee")
            var deliveryFee: Double?,
            @SerializedName("deliveryGeo")
            var deliveryGeo: String?,
            @SerializedName("deliveryTime")
            var deliveryTime: Any?,
            @SerializedName("deliveryType")
            var deliveryType: Int?,
            @SerializedName("depositPhone")
            var depositPhone: String?,
            @SerializedName("districtCode")
            var districtCode: String?,
            @SerializedName("emergencyFee")
            var emergencyFee: Int?,
            @SerializedName("enterTime")
            var enterTime: Any?,
            @SerializedName("extras")
            var extras: String?,
            @SerializedName("fpoiId")
            var fpoiId: Int?,
            @SerializedName("fshopId")
            var fshopId: Int?,
            @SerializedName("goodNames")
            var goodNames: Any?,
            @SerializedName("goodsCost")
            var goodsCost: Double?,
            @SerializedName("goodsInfo")
            var goodsInfo: Boolean?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("invoiceTitle")
            var invoiceTitle: String?,
            @SerializedName("invoiceType")
            var invoiceType: Int?,
            @SerializedName("isDeliver")
            var isDeliver: Int?,
            @SerializedName("isPostSale")
            var isPostSale: Int?,
            @SerializedName("isShowUserInfo")
            var isShowUserInfo: Boolean?,
            @SerializedName("isUpdate")
            var isUpdate: Int?,
            @SerializedName("isValid")
            var isValid: Int?,
            @SerializedName("lat")
            var lat: String?,
            @SerializedName("lon")
            var lon: String?,
            @SerializedName("negative")
            var negative: Int?,
            @SerializedName("orderHandleType")
            var orderHandleType: Int?,
            @SerializedName("orderIndex")
            var orderIndex: Int?,
            @SerializedName("orderSource")
            var orderSource: Int?,
            @SerializedName("ordererName")
            var ordererName: String?,
            @SerializedName("payPrice")
            var payPrice: Double?,
            @SerializedName("payType")
            var payType: Int?,
            @SerializedName("payee")
            var payee: String?,
            @SerializedName("perfectGoodsInfo")
            var perfectGoodsInfo: Boolean?,
            @SerializedName("platformServiceFee")
            var platformServiceFee: Double?,
            @SerializedName("poiId")
            var poiId: Int?,
            @SerializedName("preMode")
            var preMode: Int?,
            @SerializedName("prescriptionImage")
            var prescriptionImage: String?,
            @SerializedName("recvAddr")
            var recvAddr: String?,
            @SerializedName("recvGender")
            var recvGender: Any?,
            @SerializedName("recvName")
            var recvName: String?,
            @SerializedName("recvPhone")
            var recvPhone: String?,
            @SerializedName("recvUserId")
            var recvUserId: Int?,
            @SerializedName("refundCheck")
            var refundCheck: Int?,
            @SerializedName("refundPrice")
            var refundPrice: Double?,
            @SerializedName("remark")
            var remark: String?,
            @SerializedName("shopId")
            var shopId: Int?,
            @SerializedName("status")
            var status: Int?,
            @SerializedName("takeGoodsCode")
            var takeGoodsCode: String?,
            @SerializedName("tenantId")
            var tenantId: Int?,
            @SerializedName("tenantStatus")
            var tenantStatus: Int?,
            @SerializedName("totalPrice")
            var totalPrice: Double?,
            @SerializedName("type")
            var type: Int?,
            @SerializedName("updateTime")
            var updateTime: Long?,
            @SerializedName("userId")
            var userId: Long?,
            @SerializedName("viewId")
            var viewId: String?,
            @SerializedName("deliveryStatusName")
            var deliveryStatusName: String?,
            @SerializedName("deliveryStatus")
            var deliveryStatus: String?
        )
    }

}

data class StatusCountDto(
    @SerializedName("deliveryCancel")
    var deliveryCancel: Int?,//取消
    @SerializedName("deliveryComplete")
    var deliveryComplete: Int?,//送达
    @SerializedName("deliveryGoods")
    var deliveryGoods: Int?,//取货
    @SerializedName("deliveryNot")
    var deliveryNot: Int?,//待配送
    @SerializedName("deliveryOrder")
    var deliveryOrder: Int?,//抢单
    @SerializedName("deliverying")
    var deliverying: Int?//配送中
)
