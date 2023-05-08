package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName
import com.meiling.common.base.IWheel
import java.io.Serializable


data class AccountItemSelect(
    var id: String = "1",
    var name: String = "1"
) : IWheel {
    override fun getShowText(): String {
        return name.toString()
    }
}

data class AccountCityOrShopDto(
    var id: String = "1",
    var name: String = "1"
) : Serializable {
    var isSelect = false
}


data class RequestAccount(
    @SerializedName("pageIndex")
    var pageIndex: Int?,
    @SerializedName("pageSize")
    var pageSize: Int?,
    @SerializedName("status")
    var status: Int = 0
)

data class AccountListDto(
    @SerializedName("content")
    var content: ArrayList<AccountListContentDto?>?,
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
)

data class AccountListContentDto(
    @SerializedName("creator")
    var creator: Long?,
    @SerializedName("creatorName")
    var creatorName: String?,
    @SerializedName("creatorTime")
    var creatorTime: String?,
    @SerializedName("isNow")
    var isNow: Boolean,
    @SerializedName("lastLoginTime")
    var lastLoginTime: Any?,
    @SerializedName("modifier")
    var modifier: Long?,
    @SerializedName("modifierName")
    var modifierName: String?,
    @SerializedName("modifierTime")
    var modifierTime: String?,
    @SerializedName("nickname")
    var nickname: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("roleName")
    var roleName: String?,
    @SerializedName("roleViewId")
    var roleViewId: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("userId")
    var userId: Int?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("viewId")
    var viewId: String
)

data class CreateSelectPoiDto(
    @SerializedName("content")
    var content: List<PoiContentList?>?,
    @SerializedName("pageIndex")
    var pageIndex: Int?,
    @SerializedName("pageSize")
    var pageSize: Int?,
    @SerializedName("total")
    var total: Int?,
    @SerializedName("totalPage")
    var totalPage: Int?
)

data class PoiContentList(
    @SerializedName("address")
    var address: String?,
    @SerializedName("autoTransOrder")
    var autoTransOrder: Int?,
    @SerializedName("cargoType")
    var cargoType: Int?,
    @SerializedName("cityCode")
    var cityCode: String?,
    @SerializedName("cityId")
    var cityId: Int?,
    @SerializedName("cityName")
    var cityName: String?,
    @SerializedName("contactPerson")
    var contactPerson: String?,
    @SerializedName("districtCode")
    var districtCode: String?,
    @SerializedName("factoryId")
    var factoryId: Any?,
    @SerializedName("factoryName")
    var factoryName: Any?,
    @SerializedName("fuRepertoryCheck")
    var fuRepertoryCheck: Int?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("lat")
    var lat: String?,
    @SerializedName("lon")
    var lon: String?,
    @SerializedName("manyPeopleInventory")
    var manyPeopleInventory: Int?,
    @SerializedName("merchantId")
    var merchantId: String?,
    @SerializedName("mobilePhone")
    var mobilePhone: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("offlineShopProperties")
    var offlineShopProperties: Any?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("poiGroupNames")
    var poiGroupNames: Any?,
    @SerializedName("provinceCode")
    var provinceCode: String?,
    @SerializedName("shopNum")
    var shopNum: Int?,
    @SerializedName("shopProperties")
    var shopProperties: Any?,
    @SerializedName("sinceCode")
    var sinceCode: String?,
    @SerializedName("stationChannelId")
    var stationChannelId: Any?,
    @SerializedName("stationCommonId")
    var stationCommonId: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("tenantId")
    var tenantId: Int?,
    @SerializedName("type")
    var type: Int?,
    @SerializedName("viewId")
    var viewId: Long?
) {
    var isSelect = false
}

data class CreateShopBean(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("shopList")
    val shopList: List<ShopList?> = listOf()
) {
    var isSelect = false
}

data class ShopList(
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("poiId")
    var poiId: String? = "",
    @SerializedName("status")
    var status: String? = ""


)

data class ReqCreateAccount(
    @SerializedName("adminUserId")
    var adminUserId: String? = "",
    @SerializedName("channelPoiDtoList")
    var channelPoiDtoList: List<Any?> = arrayListOf(),
    @SerializedName("cityByChannelPoiDtoList")
    var cityByChannelPoiDtoList: List<Any?> = arrayListOf(),
    @SerializedName("cityPoiDtoList")
    var cityPoiDtoList: List<CityPoiDto?> = arrayListOf(),
    @SerializedName("nickname")
    var nickname: String? = "",
    @SerializedName("openAutoPoi")
    var openAutoPoi: Int? = 2,
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("poiShopIds")
    var poiShopIds: String? = "",
    @SerializedName("roleId")
    var roleId: String? = "",
    @SerializedName("shopPoiDtoList")
    var shopPoiDtoList: List<ShopPoiDto?> = arrayListOf(),
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("authStorePoiAll")
    var authStorePoiAll: String? = ""
)

data class CityPoiDto(
    @SerializedName("cityIds")
    var cityIds: Int?,
    @SerializedName("poiIds")
    var poiIds: String?
)

data class ShopPoiDto(
    @SerializedName("poiIds")
    var poiIds: String?
):Serializable

data class RoleListDto(
    @SerializedName("viewId")
    var viewId: String?,
    @SerializedName("name")
    var name: String?
)

/**
 * 编辑账号
 * */
data class AccountDetailDto(
    @SerializedName("adminUser")
    var adminUser: AdminUser?,
    @SerializedName("authorization")
    var authorization: List<Any?>?,
    @SerializedName("channelList")
    var channelList: Any?,
    @SerializedName("channelListStr")
    var channelListStr: String?,
    @SerializedName("channelPoiVoList")
    var channelPoiVoList: List<Any?>?,
    @SerializedName("channelShopsStr")
    var channelShopsStr: Any?,
    @SerializedName("cityByChannelPoiVoList")
    var cityByChannelPoiVoList: List<Any?>?,
    @SerializedName("cityPoiVoList")
    var cityPoiVoList: ArrayList<CityPoiDto> = arrayListOf(),
    @SerializedName("ifAutoContact")
    var ifAutoContact: Any?,
    @SerializedName("isNow")
    var isNow: Any?,
    @SerializedName("lastLoginTime")
    var lastLoginTime: Any?,
    @SerializedName("openAutoPoi")
    var openAutoPoi: Any?,
    @SerializedName("poiList")
    var poiList: Any?,
    @SerializedName("poiListStr")
    var poiListStr: String?,
    @SerializedName("poiShopsStr")
    var poiShopsStr: Any?,
    @SerializedName("roleId")
    var roleId: Long?,
    @SerializedName("roleName")
    var roleName: String?,
    @SerializedName("shopIdList")
    var shopIdList: Any?,
    @SerializedName("shopIdListStr")
    var shopIdListStr: String?,
    @SerializedName("shopPoiVoList")
    var shopPoiVoList: List<ShopPoiDto>,
    @SerializedName("unAuthorization")
    var unAuthorization: List<UnAuthorization?>?
) {
    data class AdminUser(
        @SerializedName("avatar")
        var avatar: String?,
        @SerializedName("createTime")
        var createTime: Long?,
        @SerializedName("creator")
        var creator: Long?,
        @SerializedName("headPhone")
        var headPhone: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("ifPush")
        var ifPush: Int?,
        @SerializedName("isNew")
        var isNew: Int?,
        @SerializedName("lastLoginTime")
        var lastLoginTime: Long?,
        @SerializedName("modifier")
        var modifier: Long?,
        @SerializedName("nickname")
        var nickname: String?,
        @SerializedName("openAutoPoi")
        var openAutoPoi: Int?,
        @SerializedName("password")
        var password: String?,
        @SerializedName("phone")
        var phone: String?,
        @SerializedName("shopId")
        var shopId: Int?,
        @SerializedName("status")
        var status: Int?,
        @SerializedName("tenantId")
        var tenantId: Int?,
        @SerializedName("token")
        var token: String?,
        @SerializedName("type")
        var type: Int?,
        @SerializedName("umengToken")
        var umengToken: String?,
        @SerializedName("updateTime")
        var updateTime: Long?,
        @SerializedName("username")
        var username: String?,
        @SerializedName("viewId")
        var viewId: Long?
    )

    data class ShopPoiVo(
        @SerializedName("channelIds")
        var channelIds: Any?,
        @SerializedName("cityIds")
        var cityIds: String?,
        @SerializedName("poiIds")
        var poiIds: String?,
        @SerializedName("shopIds")
        var shopIds: String?
    )

    data class UnAuthorization(
        @SerializedName("channel")
        var channel: String?,
        @SerializedName("channelId")
        var channelId: Int?,
        @SerializedName("city")
        var city: String?,
        @SerializedName("cityId")
        var cityId: Int?,
        @SerializedName("poiId")
        var poiId: Int?,
        @SerializedName("poiName")
        var poiName: String?,
        @SerializedName("shopId")
        var shopId: Int?,
        @SerializedName("shopName")
        var shopName: String?,
        @SerializedName("viewId")
        var viewId: Long?
    )
}
