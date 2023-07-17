package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName


data class userInfoBean(

    @SerializedName("createTime")
    var createTime: String? = "",
    @SerializedName("departFlag")
    var departFlag: Int? = 0,
    @SerializedName("jobNumber")
    var jobNumber: String? = "",
    @SerializedName("operationId")
    var operationId: String? = "",
    @SerializedName("password")
    var password: String? = "",
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("updateTime")
    var updateTime: String? = "",
    @SerializedName("userName")
    var userName: String? = "",
    @SerializedName("viewId")
    var viewId: String? = ""
)

data class Store(
    @SerializedName("createTime")
    var createTime: String? = "",
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("operationId")
    var operationId: String? = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("storeName")
    var storeName: String? = "",
    @SerializedName("storeViewId")
    var storeViewId: String? = "",
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("updateTime")
    var updateTime: String? = "",
    @SerializedName("userViewId")
    var userViewId: String? = "",
    @SerializedName("viewId")
    var viewId: String? = ""
)

class UserStoreList(

    @SerializedName("createTime")
    var createTime: String? = "",
    @SerializedName("createUser")
    var createUser: String? = "",
    @SerializedName("groupId")
    var groupId: Any? = Any(),
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("operationId")
    var operationId: String? = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("storeAddress")
    var storeAddress: String? = "",
    @SerializedName("storeName")
    var storeName: String? = "",
    @SerializedName("storePhone")
    var storePhone: String? = "",
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("updateTime")
    var updateTime: String? = "",
    @SerializedName("viewId")
    var viewId: String? = ""
)