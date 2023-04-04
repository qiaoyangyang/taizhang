package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName


data class  LoginDto(
    val adminUser: AdminUser,
    val isChain: Int,
    val isNew: Int,
    val lastAccessTenant: Any,
    val multiUnitMark: Boolean,
    val poiIdList: Any,
    val role: Int,
    val state: Any
)

data class AdminUser(
    val avatar: String,
    val createTime: Long,
    val creator: Int,
    val headPhone: String,
    val id: Int,
    val ifPush: Int,
    val isNew: Int,
    val lastLoginTime: Long,
    val modifier: Int,
    val nickname: String,
    val openAutoPoi: Int,
    val password: String,
    val phone: String,
    val shopId: Int,
    val status: Int,
    val tenantId: Int,
    val token: String,
    val type: Int,
    val umengToken: Any,
    val updateTime: Long,
    val username: String,
    val viewId: Long
)

data class ForgetDto(
    @SerializedName("encrypted")
    var encrypted: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("phoneNum")
    var phoneNum: String?
)