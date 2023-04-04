package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName


data class  LoginDto(
    val adminUser: AdminUser,
    val isChain: String,
    val isNew: String,
    val lastAccessTenant: String,
    val multiUnitMark: Boolean,
    val poiIdList: String,
    val role: String,
    val state: String
)

data class AdminUser(
    val avatar: String,
    val createTime: String,
    val creator: String,
    val headPhone: String,
    val id: String,
    val ifPush: String,
    val isNew: String,
    val lastLoginTime: String,
    val modifier: String,
    val nickname: String,
    val openAutoPoi: Int,
    val password: String,
    val phone: String,
    val shopId: String,
    val status: String,
    val tenantId: String,
    val token: String,
    val type: String,
    val umengToken: String,
    val updateTime: String,
    val username: String,
    val viewId: String
)

data class ForgetDto(
    @SerializedName("encrypted")
    var encrypted: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("phoneNum")
    var phoneNum: String?
)