package com.meiling.common.network.data
import com.google.gson.annotations.SerializedName


data class LoginDto(
    @SerializedName("adminUser")
    var adminUser: AdminUser?,
    @SerializedName("isChain")
    var isChain: Int?,
    @SerializedName("isNew")
    var isNew: Int?,
    @SerializedName("lastAccessTenant")
    var lastAccessTenant: Any?,
    @SerializedName("multiUnitMark")
    var multiUnitMark: Boolean?,
    @SerializedName("poiIdList")
    var poiIdList: Any?,
    @SerializedName("role")
    var role: Int?,
    @SerializedName("state")
    var state: Any?
) {
    data class AdminUser(
        @SerializedName("avatar")
        var avatar: String?,
        @SerializedName("createTime")
        var createTime: Long?,
        @SerializedName("creator")
        var creator: Int?,
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
        var modifier: Int?,
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
        var umengToken: Any?,
        @SerializedName("updateTime")
        var updateTime: Long?,
        @SerializedName("username")
        var username: String?,
        @SerializedName("viewId")
        var viewId: Long?
    )



}

data class ForgetDto(
    @SerializedName("encrypted")
    var encrypted: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("phoneNum")
    var phoneNum: String?
)