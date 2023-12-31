package com.meiling.account.bean

import com.google.gson.annotations.SerializedName


data class BranchInformation(
    @SerializedName("content")
    var content: ArrayList<BranchInformationContent?>? = ArrayList(),
    @SerializedName("contentT")
    var contentT: String? = "",
    @SerializedName("pageIndex")
    var pageIndex: Int? = 0,
    @SerializedName("pageSize")
    var pageSize: Int? = 0,
    @SerializedName("total")
    var total: Int? = 0,
    @SerializedName("totalPage")
    var totalPage: Int? = 0
)

data class BranchInformationContent(
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
    @SerializedName("cityName")
    var cityName: String? = "",
    @SerializedName("contactPerson")
    var contactPerson: String? = "",
    @SerializedName("districtCode")
    var districtCode: String? = "",
    @SerializedName("factoryId")
    var factoryId: Any? = Any(),
    @SerializedName("factoryName")
    var factoryName: Any? = Any(),
    @SerializedName("fuRepertoryCheck")
    var fuRepertoryCheck: Int? = 0,
    @SerializedName("id")
    var id: String? = "",
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
    @SerializedName("offlineShopProperties")
    var offlineShopProperties: String? = "",
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("poiGroupNames")
    var poiGroupNames: Any? = Any(),
    @SerializedName("provinceCode")
    var provinceCode: String? = "",
    @SerializedName("shopNum")
    var shopNum: Int? = 0,
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
    @SerializedName("viewId")
    var viewId: Long? = 0
)

data class PoiVoBean(

    @SerializedName("poiVo")
    var poiVo: PoiVo? = PoiVo(),

    )

data class PoiVo(
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("etdetailedaddress")
    var etdetailedaddress: String? = "",
    @SerializedName("storeaddress")
    var storeaddress: String? = "",
    @SerializedName("autoTransOrder")
    var autoTransOrder: Int? = 0,
    @SerializedName("cargoType")
    var cargoType: Int? = 0,
    @SerializedName("cityCode")
    var cityCode: String? = "",
    @SerializedName("cityId")
    var cityId: Int? = 0,
    @SerializedName("cityName")
    var cityName: String? = "",
    @SerializedName("contactPerson")
    var contactPerson: String? = "",
    @SerializedName("districtCode")
    var districtCode: String? = "",
    @SerializedName("factoryId")
    var factoryId: Any? = Any(),
    @SerializedName("factoryName")
    var factoryName: Any? = Any(),
    @SerializedName("fuRepertoryCheck")
    var fuRepertoryCheck: Int? = 0,
    @SerializedName("id")
    var id: Int? = 0,
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
    @SerializedName("offlineShopProperties")
    var offlineShopProperties: Any? = Any(),
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("poiGroupNames")
    var poiGroupNames: Any? = Any(),
    @SerializedName("provinceCode")
    var provinceCode: String? = "",
    @SerializedName("shopNum")
    var shopNum: Any? = Any(),
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
    @SerializedName("viewId")
    var viewId: Long? = 0
)


data class ChannelX(
    @SerializedName("createTime")
    var createTime: Long? = 0,
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("logo")
    var logo: String? = "",
    @SerializedName("logoBg")
    var logoBg: String? = "",
    @SerializedName("logoColor")
    var logoColor: String? = "",
    @SerializedName("logoF")
    var logoF: String? = "",
    @SerializedName("logoText")
    var logoText: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("process")
    var process: String? = "",
    @SerializedName("remark")
    var remark: String? = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("type")
    var type: Int? = 0,
    @SerializedName("updateTime")
    var updateTime: Long? = 0,
    @SerializedName("viewId")
    var viewId: Int? = 0,
    var isselect: Boolean
)

data class ChannShopBean(
    @SerializedName("data")
    var data: List<ChannShop?>? = listOf(),

    )

data class ChannShop(
    @SerializedName("channelId")
    var channelId: Int? = 0,
    @SerializedName("channelLogo")
    var channelLogo: String? = "",
    @SerializedName("channelName")
    var channelName: String? = "",
    @SerializedName("channelShopId")
    var channelShopId: String? = "",
    @SerializedName("city")
    var city: Int? = 0,
    @SerializedName("goodsNum")
    var goodsNum: Int? = 0,
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("inventoryModel")
    var inventoryModel: Int? = 0,
    @SerializedName("isPrint")
    var isPrint: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("orderConfirm")
    var orderConfirm: Int? = 0,
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("poiId")
    var poiId: String? = "",
    @SerializedName("poiName")
    var poiName: String? = "",
    @SerializedName("properties")
    var properties: String? = "",
    @SerializedName("runtime")
    var runtime: String? = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("thirdInventoryUrl")
    var thirdInventoryUrl: Any? = Any(),
    @SerializedName("viewId")
    var viewId: String? = "",
    @SerializedName("mtModel")
    var mtModel: Int? = 0
)

data class Unification(
    @SerializedName("url")
    var url: String? = ""

)

data class PageResult(
    @SerializedName("pageResult")
    var pageResult: PageResultX? = PageResultX()
)

data class PageResultX(
    @SerializedName("pageData")
    var pageData: List<TiktokData?>? = listOf(),
    @SerializedName("pageNum")
    var pageNum: Int? = 0,
    @SerializedName("pageSize")
    var pageSize: Int? = 0,
    @SerializedName("pages")
    var pages: Int? = 0,
    @SerializedName("total")
    var total: Int? = 0
)

data class TiktokData(
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("latitude")
    var latitude: Double? = 0.0,
    @SerializedName("longitude")
    var longitude: Double? = 0.0,
    @SerializedName("poi_id")
    var poiId: String? = "",
    @SerializedName("poi_name")
    var poiName: String? = "",
    @SerializedName("status")
    var status: Boolean? = false,
    var isstatus: Boolean? = false
)

