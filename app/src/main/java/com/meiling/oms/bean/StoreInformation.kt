package com.meiling.oms.bean
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
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
    var test:String?=""

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
