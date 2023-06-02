package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class OrderDto(
    @SerializedName("content")
    var content: List<Content?>? = listOf(),
    @SerializedName("contentT")
    var contentT: Any? = Any(),
    @SerializedName("extra")
    var extra: Any? = Any(),
    @SerializedName("pageIndex")
    var pageIndex: Int? = 0,
    @SerializedName("pageSize")
    var pageSize: Int? = 0,
    @SerializedName("total")
    var total: Int? = 0,
    @SerializedName("totalPage")
    var totalPage: Int? = 0,

    ) {
    data class Content(
        @SerializedName("adminUserName")
        var adminUserName: String? = "",
        @SerializedName("afterSaleInfo")
        var afterSaleInfo: String? = "",
        @SerializedName("canBeReselect")
        var canBeReselect: Boolean? = false,
        @SerializedName("cardNo")
        var cardNo: String? = "",
        @SerializedName("cardTypeName")
        var cardTypeName: String? = "",
        @SerializedName("channelLogo")
        var channelLogo: String? = "",
        @SerializedName("channelName")
        var channelName: String? = "",
        @SerializedName("city")
        var city: String? = "",
        @SerializedName("commission")
        var commission: Any? = Any(),
        @SerializedName("deliveryConsume")
        var deliveryConsume: DeliveryConsume? = DeliveryConsume(),
        @SerializedName("deliveryConsumeLog")
        var deliveryConsumeLog: DeliveryConsumeLog? = DeliveryConsumeLog(),
        @SerializedName("depositPrice")
        var depositPrice: Int? = 0,
        @SerializedName("discountAmount")
        var discountAmount: Int? = 0,
        @SerializedName("discountTotalPrice")
        var discountTotalPrice: Any? = Any(),
        @SerializedName("expectedBalancePayTime")
        var expectedBalancePayTime: String? = "",
        @SerializedName("finalPrice")
        var finalPrice: Int? = 0,
        @SerializedName("fixedResaleOrderPayDepositPriceTypeVoList")
        var fixedResaleOrderPayDepositPriceTypeVoList: List<Any?>? = listOf(),
        @SerializedName("fixedResaleOrderPayFinalPriceTypeVoList")
        var fixedResaleOrderPayFinalPriceTypeVoList: List<Any?>? = listOf(),
        @SerializedName("fixedResaleOrderStatus")
        var fixedResaleOrderStatus: Any? = Any(),
        @SerializedName("fixedResaleOrderType")
        var fixedResaleOrderType: Any? = Any(),
        @SerializedName("giftcardBalance")
        var giftcardBalance: Any? = Any(),
        @SerializedName("goodsVoList")
        var goodsVoList: ArrayList<OrderGoodsVo?>? = ArrayList(),
        @SerializedName("hasMarkPay")
        var hasMarkPay: Int? = 0,
        @SerializedName("isShowCouponCode")
        var isShowCouponCode: Any? = Any(),
        @SerializedName("isShowUserInfo")
        var isShowUserInfo: Any? = Any(),
        @SerializedName("miniProgramsMa")
        var miniProgramsMa: Any? = Any(),
        @SerializedName("order")
        var order: Order? = Order(),
        @SerializedName("order62Code")
        var order62Code: Any? = Any(),
        @SerializedName("orderModifyRecord")
        var orderModifyRecord: String? = "",
        @SerializedName("orderName")
        var orderName: String? = "",
        @SerializedName("orderPhone")
        var orderPhone: String? = "",
        @SerializedName("payChannels")
        var payChannels: Any? = Any(),
        @SerializedName("payExtendVos")
        var payExtendVos: List<Any?>? = listOf(),
        @SerializedName("payPriceList")
        var payPriceList: List<Any?>? = listOf(),
        @SerializedName("payType")
        var payType: Any? = Any(),
        @SerializedName("phone")
        var phone: String? = "",
        @SerializedName("preOrder")
        var preOrder: Any? = Any(),
        @SerializedName("prescriptionImage")
        var prescriptionImage: List<Any?>? = listOf(),
        @SerializedName("promotionMap")
        var promotionMap: List<Any?>? = listOf(),
        @SerializedName("refund")
        var refund: Int? = 0,
        @SerializedName("shipmentShopName")
        var shipmentShopName: String? = "",
        @SerializedName("shopName")
        var shopName: String? = "",
        @SerializedName("spreadFee")
        var spreadFee: Any? = Any(),
        @SerializedName("statusColor")
        var statusColor: String? = "",
        @SerializedName("statusName")
        var statusName: String? = "",
        @SerializedName("supplierPrice")
        var supplierPrice: Int? = 0,
        @SerializedName("tradeNo")
        var tradeNo: Any? = Any(),
        @SerializedName("tradeOut")
        var tradeOut: Any? = Any(),
        @SerializedName("distance")
        var distance: String? = "",
        @SerializedName("shop")
        var shop: OrderShop? = OrderShop(),
        @SerializedName("poi")
        var poi: OrderPoi? = OrderPoi()
    ):Serializable {


        data class Order(
            @SerializedName("activityFee")
            var activityFee: Double? = 0.0,
            @SerializedName("actualIncome")
            var actualIncome: Double? = 0.0,
            @SerializedName("actualPayPrice")
            var actualPayPrice: Double? = 0.0,
            @SerializedName("addressDesensitization")
            var addressDesensitization: String? = "",
            @SerializedName("adminUserId")
            var adminUserId: Int? = 0,
            @SerializedName("arriveTime")
            var arriveTime: String? = "",
            @SerializedName("arriveTimeDate")
            var arriveTimeDate: String? = "",
            @SerializedName("attachment")
            var attachment: String? = "",
            @SerializedName("auditId")
            var auditId: Any? = Any(),
            @SerializedName("boxPriceTotal")
            var boxPriceTotal: Any? = Any(),
            @SerializedName("cardMoney")
            var cardMoney: Any? = Any(),
            @SerializedName("cardNo")
            var cardNo: Any? = Any(),
            @SerializedName("cardType")
            var cardType: Any? = Any(),
            @SerializedName("changePrice")
            var changePrice: Double? = 0.0,
            @SerializedName("channelCreateTime")
            var channelCreateTime: String? = "",
            @SerializedName("channelDaySn")
            var channelDaySn: Int? = 0,
            @SerializedName("channelId")
            var channelId: Int? = 0,
            @SerializedName("channelOrderNum")
            var channelOrderNum: String? = "",
            @SerializedName("completeTime")
            var completeTime: Long? = 0,
            @SerializedName("couponCode")
            var couponCode: String? = "",
            @SerializedName("couponName")
            var couponName: Any? = Any(),
            @SerializedName("createTime")
            var createTime: Long? = 0,
            @SerializedName("deliverName")
            var deliverName: String? = "",
            @SerializedName("deliverPhone")
            var deliverPhone: String? = "",
            @SerializedName("deliverPrice")
            var deliverPrice: Int? = 0,
            @SerializedName("deliveryFee")
            var deliveryFee: Double? = 0.0,
            @SerializedName("deliveryGeo")
            var deliveryGeo: String? = "",
            @SerializedName("deliveryStatusName")
            var deliveryStatusName: String? = "",
            @SerializedName("deliveryTime")
            var deliveryTime: Long? = 0,
            @SerializedName("deliveryType")
            var deliveryType: String? = "",
            @SerializedName("depositPhone")
            var depositPhone: String? = "",
            @SerializedName("districtCode")
            var districtCode: String? = "",
            @SerializedName("emergencyFee")
            var emergencyFee: Int? = 0,
            @SerializedName("enterTime")
            var enterTime: Any? = Any(),
            @SerializedName("extras")
            var extras: String? = "",
            @SerializedName("fpoiId")
            var fpoiId: Int? = 0,
            @SerializedName("fshopId")
            var fshopId: Int? = 0,
            @SerializedName("goodNames")
            var goodNames: Any? = Any(),
            @SerializedName("goodsCost")
            var goodsCost: Double? = 0.0,
            @SerializedName("goodsInfo")
            var goodsInfo: Boolean? = false,
            @SerializedName("id")
            var id: Int? = 0,
            @SerializedName("invoiceTitle")
            var invoiceTitle: String? = "",
            @SerializedName("invoiceType")
            var invoiceType: Int? = 0,
            @SerializedName("isDeliver")
            var isDeliver: Int? = 0,
            @SerializedName("isPostSale")
            var isPostSale: Int? = 0,
            @SerializedName("isShowUserInfo")
            var isShowUserInfo: Boolean? = false,
            @SerializedName("isUpdate")
            var isUpdate: Int? = 0,
            @SerializedName("isValid")
            var isValid: Int? = 0,
            @SerializedName("lat")
            var lat: String? = "",
            @SerializedName("logisticsStatus")
            var logisticsStatus: String? = "",
            @SerializedName("lon")
            var lon: String? = "",
            @SerializedName("negative")
            var negative: Int? = 0,
            @SerializedName("orderHandleType")
            var orderHandleType: Int? = 0,
            @SerializedName("orderIndex")
            var orderIndex: Int? = 0,
            @SerializedName("orderSource")
            var orderSource: Int? = 0,
            @SerializedName("ordererName")
            var ordererName: String? = "",
            @SerializedName("payPrice")
            var payPrice: Double? = 0.0,
            @SerializedName("payType")
            var payType: Int? = 0,
            @SerializedName("payee")
            var payee: String? = "",
            @SerializedName("perfectGoodsInfo")
            var perfectGoodsInfo: Boolean? = false,
            @SerializedName("platformServiceFee")
            var platformServiceFee: Double? = 0.0,
            @SerializedName("poiId")
            var poiId: String? = "",
            @SerializedName("preMode")
            var preMode: Int? = 0,
            @SerializedName("prescriptionImage")
            var prescriptionImage: String? = "",
            @SerializedName("recvAddr")
            var recvAddr: String? = "",
            @SerializedName("recvGender")
            var recvGender: Any? = Any(),
            @SerializedName("recvName")
            var recvName: String? = "",
            @SerializedName("recvPhone")
            var recvPhone: String? = "",
            @SerializedName("recvUserId")
            var recvUserId: Long? = 0,
            @SerializedName("refundCheck")
            var refundCheck: Int? = 0,
            @SerializedName("refundPrice")
            var refundPrice: Double? = 0.0,
            @SerializedName("remark")
            var remark: String? = "",
            @SerializedName("shopId")
            var shopId: Int? = 0,
            @SerializedName("status")
            var status: Int? = 0,
            @SerializedName("takeGoodsCode")
            var takeGoodsCode: String? = "",
            @SerializedName("tenantId")
            var tenantId: Int? = 0,
            @SerializedName("tenantStatus")
            var tenantStatus: Int? = 0,
            @SerializedName("totalPrice")
            var totalPrice: Double? = 0.0,
            @SerializedName("type")
            var type: Int? = 0,
            @SerializedName("updateTime")
            var updateTime: Long? = 0,
            @SerializedName("userId")
            var userId: Long? = 0,
            @SerializedName("viewId")
            var viewId: String? = ""
        ) : Serializable
    }
}
data class OrderShop(
    @SerializedName("accountType")
    var accountType: Int? = 0,
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("appointmentTime")
    var appointmentTime: Int? = 0,
    @SerializedName("autoTime")
    var autoTime: String? = "",
    @SerializedName("channelId")
    var channelId: Int? = 0,
    @SerializedName("channelShopId")
    var channelShopId: String? = "",
    @SerializedName("city")
    var city: Int? = 0,
    @SerializedName("createTime")
    var createTime: Long? = 0,
    @SerializedName("deductPoint")
    var deductPoint: Double? = 0.0,
    @SerializedName("deliveryPrice")
    var deliveryPrice: Double? = 0.0,
    @SerializedName("deliveryRadius")
    var deliveryRadius: Double? = 0.0,
    @SerializedName("deliverySwitch")
    var deliverySwitch: Any? = Any(),
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("earliestSendTime")
    var earliestSendTime: String? = "",
    @SerializedName("financeType")
    var financeType: Int? = 0,
    @SerializedName("freeDeliveryThreshold")
    var freeDeliveryThreshold: Double? = 0.0,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("initialDeliveryPrice")
    var initialDeliveryPrice: Double? = 0.0,
    @SerializedName("inventoryModel")
    var inventoryModel: Int? = 0,
    @SerializedName("isPopup")
    var isPopup: Int? = 0,
    @SerializedName("isPrint")
    var isPrint: Int? = 0,
    @SerializedName("lat")
    var lat: String? = "",
    @SerializedName("lon")
    var lon: String? = "",
    @SerializedName("miniProgramsMa")
    var miniProgramsMa: String? = "",
    @SerializedName("mtModel")
    var mtModel: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("needPos")
    var needPos: Int? = 0,
    @SerializedName("orderConfirm")
    var orderConfirm: Int? = 0,
    @SerializedName("orderDispatch")
    var orderDispatch: Int? = 0,
    @SerializedName("orderPrint")
    var orderPrint: Int? = 0,
    @SerializedName("orderTimeLimit")
    var orderTimeLimit: Double? = 0.0,
    @SerializedName("paramEffective")
    var paramEffective: Any? = Any(),
    @SerializedName("password")
    var password: Any? = Any(),
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("pickTime")
    var pickTime: Any? = Any(),
    @SerializedName("poiId")
    var poiId: Int? = 0,
    @SerializedName("preOrderSetting")
    var preOrderSetting: Int? = 0,
    @SerializedName("printerCount")
    var printerCount: Int? = 0,
    @SerializedName("properties")
    var properties: String? = "",
    @SerializedName("requestParam")
    var requestParam: String? = "",
    @SerializedName("runtime")
    var runtime: String? = "",
    @SerializedName("selfMention")
    var selfMention: Int? = 0,
    @SerializedName("selfMentionLimit")
    var selfMentionLimit: Double? = 0.0,
    @SerializedName("shopImage")
    var shopImage: String? = "",
    @SerializedName("shopNotice")
    var shopNotice: String? = "",
    @SerializedName("showUserInfo")
    var showUserInfo: Int? = 0,
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("thirdInventoryUrl")
    var thirdInventoryUrl: Any? = Any(),
    @SerializedName("trueOrFalse")
    var trueOrFalse: Any? = Any(),
    @SerializedName("type")
    var type: Int? = 0,
    @SerializedName("uid")
    var uid: Any? = Any(),
    @SerializedName("updateTime")
    var updateTime: Long? = 0,
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("viewId")
    var viewId: Long? = 0,
    @SerializedName("weeks")
    var weeks: String? = ""
) : Serializable

data class OrderPoi(
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("autoTransOrder")
    var autoTransOrder: Int? = 0,
    @SerializedName("cargoType")
    var cargoType: Int? = 0,
    @SerializedName("cityCode")
    var cityCode: String? = "",
    @SerializedName("cityId")
    var cityId: Int? = 0,
    @SerializedName("contactPerson")
    var contactPerson: String? = "",
    @SerializedName("createTime")
    var createTime: Long? = 0,
    @SerializedName("districtCode")
    var districtCode: String? = "",
    @SerializedName("executeUserId")
    var executeUserId: Int? = 0,
    @SerializedName("fuRepertoryCheck")
    var fuRepertoryCheck: Int? = 0,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("keyPerson")
    var keyPerson: String? = "",
    @SerializedName("lat")
    var lat: String? = "",
    @SerializedName("lon")
    var lon: String? = "",
    @SerializedName("manyPeopleInventory")
    var manyPeopleInventory: Int? = 0,
    @SerializedName("merchantId")
    var merchantId: String? = "",
    @SerializedName("mobilePhone")
    var mobilePhone: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("poiGroupId")
    var poiGroupId: Any? = Any(),
    @SerializedName("poiGroupNames")
    var poiGroupNames: Any? = Any(),
    @SerializedName("provinceCode")
    var provinceCode: String? = "",
    @SerializedName("serviceStatus")
    var serviceStatus: Int? = 0,
    @SerializedName("shopProperties")
    var shopProperties: Any? = Any(),
    @SerializedName("sinceCode")
    var sinceCode: String? = "",
    @SerializedName("stationChannelId")
    var stationChannelId: Any? = Any(),
    @SerializedName("stationCommonId")
    var stationCommonId: String? = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("type")
    var type: Int? = 0,
    @SerializedName("updateTime")
    var updateTime: Long? = 0,
    @SerializedName("viewId")
    var viewId: Long? = 0
) : Serializable

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
    var deliverying: Int?,//配送中,
    @SerializedName("deliveryAll")
    var deliveryAll: Int?//全部,
)

data class DeliveryConsumeLog(
    @SerializedName("createTime")
    var createTime: String? = "",
    @SerializedName("deliveryConsumeId")
    var deliveryConsumeId: Int? = 1,
    @SerializedName("id")
    var id: Int? = 1,
    @SerializedName("remark")
    var remark: String? = "",
    @SerializedName("status")
    var status: Int? = 1,
    @SerializedName("statusName")
    var statusName: String? = "",
    @SerializedName("updateTime")
    var updateTime: String? = ""
) : Serializable

data class DeliveryConsume(
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("stationChannelId")
    var stationChannelId: String? = "",
    @SerializedName("viewId")
    var viewId: String? = "",
//    @SerializedName("vrcId")
//    var vrcId: Int?,
//    @SerializedName("weight")
//    var weight: String?
) : Serializable

data class SelectDialogDto(
    var startDate: String,//开始时间
    var endDate: String,//结束时间
    var timetype: Int,//时间类型 0自定义时间 1 昨天 2 今天 3 近七天 4 进30天
    var channelId: String?,// 平台  渠道全部传null,根据返回渠道
    var orderTime: String,// 日期类型  1.下单时间，2 收货时间，出货时间 4,完成时间

) : Serializable

data class SelectOrderDialogDto(
    var channelId: String? = "0",// 平台  渠道全部传null,根据返回渠道
    var orderSort: String? = "1",// 排序
) : Serializable


data class OrderGoodsVo(
    @SerializedName("actualAmount")
    var actualAmount: Double? = 0.0,
    @SerializedName("actualPrice")
    var actualPrice: Double? = 0.0,
    @SerializedName("avater")
    var avater: String? = "",
    @SerializedName("baseStockUnitName")
    var baseStockUnitName: Any? = Any(),
    @SerializedName("baseStockUnitStatus")
    var baseStockUnitStatus: Any? = Any(),
    @SerializedName("baseStockUnitTypeId")
    var baseStockUnitTypeId: Any? = Any(),
    @SerializedName("baseStockUnitViewId")
    var baseStockUnitViewId: Any? = Any(),
    @SerializedName("baseUnitName")
    var baseUnitName: Any? = Any(),
    @SerializedName("baseUnitStatus")
    var baseUnitStatus: Any? = Any(),
    @SerializedName("baseUnitViewId")
    var baseUnitViewId: Any? = Any(),
    @SerializedName("channelId")
    var channelId: Any? = Any(),
    @SerializedName("channelImg")
    var channelImg: Any? = Any(),
    @SerializedName("countWithUnit")
    var countWithUnit: String? = "",
    @SerializedName("currentGoodsUnitStockNumber")
    var currentGoodsUnitStockNumber: Any? = Any(),
    @SerializedName("customCode")
    var customCode: String? = "",
    @SerializedName("depositPrice")
    var depositPrice: Double? = 0.0,
    @SerializedName("gid")
    var gid: Any? = Any(),
    @SerializedName("gname")
    var gname: String? = "",
    @SerializedName("goodsStockDeduction")
    var goodsStockDeduction: String? = "",
    @SerializedName("goodsTotalCost")
    var goodsTotalCost: Double? = 0.0,
    @SerializedName("goodsType")
    var goodsType: Int? = 0,
    @SerializedName("goodsUnitRatio")
    var goodsUnitRatio: Any? = Any(),
    @SerializedName("goodsUnitViewId")
    var goodsUnitViewId: String? = "",
    @SerializedName("modelType")
    var modelType: Any? = Any(),
    @SerializedName("multiUnit")
    var multiUnit: Any? = Any(),
    @SerializedName("multiUnitCurrentStock")
    var multiUnitCurrentStock: Any? = Any(),
    @SerializedName("negativeNumber")
    var negativeNumber: Double? = 0.0,
    @SerializedName("number")
    var number: Int? = 0,
    @SerializedName("orderGoodsId")
    var orderGoodsId: Int? = 0,
    @SerializedName("originalPrice")
    var originalPrice: Double? = 0.0,
    @SerializedName("parentGoodsUnitViewId")
    var parentGoodsUnitViewId: Any? = Any(),
    @SerializedName("price")
    var price: Double? = 0.0,
    @SerializedName("profit")
    var profit: Double? = 0.0,
    @SerializedName("reasonsforpricechange")
    var reasonsforpricechange: String? = "",
    @SerializedName("refundNum")
    var refundNum: Int? = 0,
    @SerializedName("refundPrice")
    var refundPrice: Double? = 0.0,
    @SerializedName("shopNames")
    var shopNames: Any? = Any(),
    @SerializedName("sku")
    var sku: String? = "",
    @SerializedName("spuId")
    var spuId: Any? = Any(),
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("supplierPrice")
    var supplierPrice: Int? = 0,
    @SerializedName("totalPrice")
    var totalPrice: Double? = 0.0,
    @SerializedName("unitPrice")
    var unitPrice: Double? = 0.0,
    @SerializedName("unitSpecBarcode")
    var unitSpecBarcode: Any? = Any(),
    @SerializedName("unitType")
    var unitType: Int? = 0,
    @SerializedName("unitTypeId")
    var unitTypeId: Any? = Any(),
    @SerializedName("unitUseBusinessTypeStr")
    var unitUseBusinessTypeStr: Any? = Any(),
    @SerializedName("unitUseType")
    var unitUseType: Any? = Any(),
    @SerializedName("specs")
    var specs: String? =""
):Serializable