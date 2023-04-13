package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName
import com.meiling.common.base.IWheel
import java.io.Serializable


data class OrderSendAddress(
    @SerializedName("cargoType")
    var cargoType: String?,
    @SerializedName("channelType")
    var channelType: String?,
    @SerializedName("cityCode")
    var cityCode: String?,
    @SerializedName("deliverId")
    var deliverId: String?,
    @SerializedName("deliveryName")
    var deliveryName: String?,
    @SerializedName("deliveryPhone")
    var deliveryPhone: String?,
    @SerializedName("districtCode")
    var districtCode: String?,
    @SerializedName("exitChannel")
    var exitChannel: Boolean,
    @SerializedName("goodsWeight")
    var goodsWeight: String?,
    @SerializedName("lat")
    var lat: String?,
    @SerializedName("lon")
    var lon: String?,
    @SerializedName("merchantId")
    var merchantId: String,
    @SerializedName("method")
    var method: String,
    @SerializedName("originId")
    var originId: Any,
    @SerializedName("poiAddr")
    var poiAddr: String?,
    @SerializedName("poiCityCode")
    var poiCityCode: String?,
    @SerializedName("poiDistrictCode")
    var poiDistrictCode: String?,
    @SerializedName("poiId")
    var poiId: String?,
    @SerializedName("poiLat")
    var poiLat: String?,
    @SerializedName("poiLon")
    var poiLon: String?,
    @SerializedName("poiName")
    var poiName: String?,
    @SerializedName("poiPhone")
    var poiPhone: String?,
    @SerializedName("poiProvinceCode")
    var poiProvinceCode: String?,
    @SerializedName("provinceCode")
    var provinceCode: String?,
    @SerializedName("recvAddr")
    var recvAddr: String?,
    @SerializedName("recvName")
    var recvName: String?,
    @SerializedName("recvPhone")
    var recvPhone: String?,
    @SerializedName("sendCity")
    var sendCity: String?,
    @SerializedName("stationChannelId")
    var stationChannelId: String?,
    @SerializedName("stationCommonId")
    var stationCommonId: String?,
    @SerializedName("tips")
    var tips: String?,
//    @SerializedName("recvAddrDetail")
//    var recvAddrDetail: String,
    @SerializedName("arriveTime")
    var arriveTime: String?
) : Serializable

data class OrderSendRequest(
    @SerializedName("cargoPrice")
    var cargoPrice: String,
    @SerializedName("cargoType")
    var cargoType: String,
    @SerializedName("deliveryTime")
    var deliveryTime: String,
    @SerializedName("deliveryType")
    var deliveryType: String,
    @SerializedName("orderId")
    var orderId: String,
    @SerializedName("wight")
    var wight: String,
    @SerializedName("orderAndPoiDeliveryDateDto")
    var orderAndPoiDeliveryDateDto: OrderSendAddress
)

data class OrderSendChannel(
    @SerializedName("channelType")
    var channelType: String,
    @SerializedName("deliverId")
    var deliverId: String?,
    @SerializedName("distance")
    var distance: String?,
    @SerializedName("errMsg")
    var errMsg: String,
    @SerializedName("iconUrl")
    var iconUrl: String,
    @SerializedName("lowest")
    var lowest: String,
    @SerializedName("originId")
    var originId: String,
    @SerializedName("payMoney")
    var payMoney: String,
    @SerializedName("stationChannelId")
    var stationChannelId: String?,
    @SerializedName("totalMoney")
    var totalMoney: String,
    @SerializedName("typeName")
    var typeName: String
) {
    var select = false
}

data class OrderSendShopSelect(
    var id: String = "1",
    var name: String = "1"
): IWheel {
    override fun getShowText(): String {
        return name.toString()
    }
}

data class LogisticsConfirmDtoList(var logisticsConfirmDtoList: ArrayList<LogisticsInsertDto>)

data class LogisticsInsertDto(
    @SerializedName("amount")
    var amount: String = "",
    @SerializedName("cargoPrice")
    var cargoPrice: String = "",
    @SerializedName("cargoType")
    var cargoType: String = "",
    @SerializedName("deliveryTime")
    var deliveryTime: String = "",
    @SerializedName("deliveryType")
    var deliveryType: String = "",
    @SerializedName("distance")
    var distance: String? = "",
    @SerializedName("orderId")
    var orderId: String = "",
    @SerializedName("orgId")
    var orgId: String = "",
    @SerializedName("wight")
    var wight: String = "",
    @SerializedName("orderAndPoiDeliveryDateDto")
    var orderAndPoiDeliveryDateDto: OrderSendAddress,
    @SerializedName("channelType")
    var channelType: String = "",
    @SerializedName("deliveryName")
    var deliveryName: String = "",
    @SerializedName("deliveryPhone")
    var deliveryPhone: String = "",
    @SerializedName("deliveryId")
    var deliveryId: String? = "",
)

class EventBusCloseOrderDistributionDialog()

class EventBusChangeAddress(var orderSendAddress:OrderSendAddress,var log:String)

class EventBusChangeAddressEXPRESS(var log:String)

class EventBusRefreshOrder()//刷新数据

class CancelOrderSend(
    var deliveryConsumerId: String,
    var poiId: String,
    var stationChannelId: String,
)

data class OrderSendDetail(
    @SerializedName("actualAmount")
    var actualAmount: String,
    @SerializedName("addition")
    var addition: String,
    @SerializedName("adminUserId")
    var adminUserId: String,
    @SerializedName("amount")
    var amount: String,
    @SerializedName("appointTime")
    var appointTime: String,
    @SerializedName("channelDelNum")
    var channelDelNum: String,
    @SerializedName("channelType")
    var channelType: String,
    @SerializedName("createTime")
    var createTime: String,
    @SerializedName("cutAmount")
    var cutAmount: String,
    @SerializedName("deliveryConsumeLogs")
    var deliveryConsumeLogs: ArrayList<DeliveryConsumeLog>,
    @SerializedName("deliveryName")
    var deliveryName: String,
    @SerializedName("deliveryPhone")
    var deliveryPhone: String,
    @SerializedName("distance")
    var distance: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("lat")
    var lat: String,
    @SerializedName("lng")
    var lng: String,
    @SerializedName("orderId")
    var orderId: String,
    @SerializedName("pickupPassword")
    var pickupPassword: String,
    @SerializedName("poiId")
    var poiId: String,
    @SerializedName("random")
    var random: Int,
    @SerializedName("recvAddr")
    var recvAddr: String,
    @SerializedName("recvName")
    var recvName: String,
    @SerializedName("recvPhone")
    var recvPhone: String,
    @SerializedName("remark")
    var remark: String,
    @SerializedName("senderAddr")
    var senderAddr: String,
    @SerializedName("senderName")
    var senderName: String,
    @SerializedName("senderPhone")
    var senderPhone: String,
    @SerializedName("shopId")
    var shopId: Int,
    @SerializedName("source")
    var source: String,
    @SerializedName("stationChannelId")
    var stationChannelId: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("statusName")
    var statusName: String,
    @SerializedName("tenantId")
    var tenantId: String,
    @SerializedName("tips")
    var tips: String,
    @SerializedName("type")//10 tc 30 self 20 express
    var type: String,
    @SerializedName("updateTime")
    var updateTime: String,
    @SerializedName("viewId")
    var viewId: String,
    @SerializedName("vrcId")
    var vrcId: String,
    @SerializedName("weight")
    var weight: String,
    @SerializedName("icon")
    var icon: String
) {
    data class DeliveryConsumeLog(
        @SerializedName("createTime")
        var createTime: String,
        @SerializedName("deliveryConsumeId")
        var deliveryConsumeId: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("remark")
        var remark: String,
        @SerializedName("status")
        var status: String,
        @SerializedName("updateTime")
        var updateTime: Long,
        @SerializedName("statusName")
        var statusName: String
    )
}

data class SelectLabel(
    @SerializedName("label")
    var label: String,
    @SerializedName("value")
    var value: String
)

data class OrderSendSetting(
    @SerializedName("address")
    var address: String,
    @SerializedName("cargoType")
    var cargoType: String,
    @SerializedName("channelType")
    var channelType: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("configId")
    var configId: Long,
    @SerializedName("coordinateType")
    var coordinateType: String,
    @SerializedName("deliveryName")
    var deliveryName: String?,
    @SerializedName("deliveryPhone")
    var deliveryPhone: String?,
    @SerializedName("district")
    var district: String,
    @SerializedName("goodsWeight")
    var goodsWeight: String,
    @SerializedName("lat")
    var lat: String,
    @SerializedName("logisticsType")
    var logisticsType: String,
    @SerializedName("lon")
    var lon: String,
    @SerializedName("method")
    var method: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("province")
    var province: String,
    @SerializedName("tenantId")
    var tenantId: Int,
    @SerializedName("tips")
    var tips: String,
    @SerializedName("channelTypeName")
    var channelTypeName: String?,
    @SerializedName("cargoTypeName")
    var cargoTypeName: String
)

data class SettingRule(var name: String, var value: String?)

data class OrderSendAddTips(
    @SerializedName("channelAmount")
    var channelAmount: String,
    @SerializedName("channelName")
    var channelName: String,
    @SerializedName("channelType")
    var channelType: String,
    @SerializedName("iconUrl")
    var iconUrl: String,
    @SerializedName("receiverAddress")
    var receiverAddress: String,
    @SerializedName("receiverName")
    var receiverName: String,
    @SerializedName("receiverPhone")
    var receiverPhone: String,
    @SerializedName("tip")
    var tip: String,
    var Addtip: String = "1",
){

}
data class OrderSendAddShow(var num: Int, var address: String,var city: String,var lon: String,var lat: String)

data class DefaultLogistics(var defaultLogistics: String)

data class OrderSendAddTipRequest(
    @SerializedName("deliveryConsumerId")
    var deliveryConsumerId: String,
    @SerializedName("poiId")
    var poiId: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("tip")
    var tip: String
)


data class OrderSendSelectCity(
    @SerializedName("result")
    var result: String,
    @SerializedName("data")
    var data: DataOrder
)

data class DataOrder(
    @SerializedName("city")
    var city: List<City>
) {
    data class City(
        @SerializedName("citylist")
        var citylist: List<String>,
        @SerializedName("letter")
        var letter: String
    )
}

data class OutWareHouseList(
    @SerializedName("departmentId")
    var departmentId: String,
    @SerializedName("departmentName")
    var departmentName: String,
    @SerializedName("departmentType")
    var departmentType: Int,
    @SerializedName("lat")
    var lat: String,
    @SerializedName("lon")
    var lon: String,
) :Serializable

