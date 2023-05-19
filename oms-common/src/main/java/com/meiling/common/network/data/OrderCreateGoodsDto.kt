package com.meiling.common.network.data


import com.google.gson.annotations.SerializedName

data class OrderCreateGoodsDto(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("num")
    var num: Int = 1,
    @SerializedName("salePrice")
    var salePrice: String = "",
    @SerializedName("totalPrice")
    var totalPrice: String = "",
    @SerializedName("goodsSpec")
    var goodsSpec: String = "默认规格",
    @SerializedName("imgUrl")
    var imgUrl: String = "https://static.igoodsale.com/default-goods.png"
) {
    var isSelectGoods = false
}

data class OrderCreateGoodsDto1(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("num")
    var num: Int = 1,
    @SerializedName("salePrice")
    var salePrice: String = "",
    @SerializedName("totalPrice")
    var totalPrice: String = "",
    @SerializedName("goodsSpec")
    var goodsSpec: String = "默认规格",
    @SerializedName("imgUrl")
    var imgUrl: String = "https://static.igoodsale.com/default-goods.png"
)

//let goodsDic = [ "goodsSpec": "默认规格",
//"imgUrl": "https://static.igoodsale.com/default-goods.png",
//"name": "string",
//"num": 0,
//"salePrice": 0,
//"totalPrice": 0
//] as [String : Any]


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

data class OrderCreateSaveDto(
    @SerializedName("arriveTime")
    var arriveTime: String? = "",
    @SerializedName("channelId")
    var channelId: String? = "11",
    @SerializedName("deliveryType")
    var deliveryType: String? = "",
    @SerializedName("lat")
    var lat: String? = "",
    @SerializedName("lon")
    var lon: String? = "",
    @SerializedName("poiId")
    var poiId: String? = "",
    @SerializedName("recvAddr")
    var recvAddr: String? = "",
    @SerializedName("recvName")
    var recvName: String? = "",
    @SerializedName("recvPhone")
    var recvPhone: String? = "",
    @SerializedName("remark")
    var remark: String? = "",
//    @SerializedName("shopId")
//    var shopId: String? = "",
    @SerializedName("selfGoodsDtoList")
    var selfGoodsDtoList: ArrayList<OrderCreateGoodsDto1>?
)

data class OrderCreateFinishDto(
    @SerializedName("activityFee")
    var activityFee: Int? = 0,
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
    var attachment: Any? = Any(),
    @SerializedName("auditId")
    var auditId: Any? = Any(),
    @SerializedName("boxPriceTotal")
    var boxPriceTotal: Any? = Any(),
    @SerializedName("cardMoney")
    var cardMoney: Int? = 0,
    @SerializedName("cardNo")
    var cardNo: String? = "",
    @SerializedName("cardType")
    var cardType: Int? = 0,
    @SerializedName("changePrice")
    var changePrice: Int? = 0,
    @SerializedName("channelCreateTime")
    var channelCreateTime: String? = "",
    @SerializedName("channelDaySn")
    var channelDaySn: Int? = 0,
    @SerializedName("channelId")
    var channelId: Int? = 0,
    @SerializedName("channelOrderNum")
    var channelOrderNum: String? = "",
    @SerializedName("completeTime")
    var completeTime: Any? = Any(),
    @SerializedName("couponCode")
    var couponCode: Any? = Any(),
    @SerializedName("couponName")
    var couponName: Any? = Any(),
    @SerializedName("createTime")
    var createTime: Any? = Any(),
    @SerializedName("deliverName")
    var deliverName: String? = "",
    @SerializedName("deliverPhone")
    var deliverPhone: String? = "",
    @SerializedName("deliverPrice")
    var deliverPrice: Int? = 0,
    @SerializedName("deliveryFee")
    var deliveryFee: Int? = 0,
    @SerializedName("deliveryGeo")
    var deliveryGeo: String? = "",
    @SerializedName("deliveryStatusName")
    var deliveryStatusName: Any? = Any(),
    @SerializedName("deliveryTime")
    var deliveryTime: Any? = Any(),
    @SerializedName("deliveryType")
    var deliveryType: Int? = 0,
    @SerializedName("depositPhone")
    var depositPhone: String? = "",
    @SerializedName("districtCode")
    var districtCode: Any? = Any(),
    @SerializedName("emergencyFee")
    var emergencyFee: Any? = Any(),
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
    var goodsCost: Any? = Any(),
    @SerializedName("goodsInfo")
    var goodsInfo: Boolean? = false,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("invoiceTitle")
    var invoiceTitle: String? = "",
    @SerializedName("invoiceType")
    var invoiceType: Any? = Any(),
    @SerializedName("isDeliver")
    var isDeliver: Int? = 0,
    @SerializedName("isPostSale")
    var isPostSale: Int? = 0,
    @SerializedName("isShowUserInfo")
    var isShowUserInfo: Any? = Any(),
    @SerializedName("isUpdate")
    var isUpdate: Int? = 0,
    @SerializedName("isValid")
    var isValid: Any? = Any(),
    @SerializedName("lat")
    var lat: String? = "",
    @SerializedName("logisticsStatus")
    var logisticsStatus: Any? = Any(),
    @SerializedName("lon")
    var lon: String? = "",
    @SerializedName("negative")
    var negative: Any? = Any(),
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
    var platformServiceFee: Int? = 0,
    @SerializedName("poiId")
    var poiId: Int? = 0,
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
    var refundCheck: Any? = Any(),
    @SerializedName("refundPrice")
    var refundPrice: Any? = Any(),
    @SerializedName("remark")
    var remark: String? = "",
    @SerializedName("shopId")
    var shopId: Int? = 0,
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("takeGoodsCode")
    var takeGoodsCode: Any? = Any(),
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("tenantStatus")
    var tenantStatus: Int? = 0,
    @SerializedName("totalPrice")
    var totalPrice: Double? = 0.0,
    @SerializedName("type")
    var type: Int? = 0,
    @SerializedName("updateTime")
    var updateTime: Any? = Any(),
    @SerializedName("userId")
    var userId: Long? = 0,
    @SerializedName("viewId")
    var viewId: String? = ""
)
