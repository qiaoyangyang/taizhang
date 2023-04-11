package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName


data class RechargeRequest(
    @SerializedName("actuallyAmount")
    var actuallyAmount: String?,
    @SerializedName("payEntrance")
    var payEntrance: String?,
    @SerializedName("payType")
    var payType: String?,
    @SerializedName("userSign")
    var userSign: String?
)

data class RechargeRecordListReq(
    @SerializedName("createUserId")
    var createUserId: String?,
    @SerializedName("endDate")
    var endDate: String?,
    @SerializedName("pageIndex")
    var pageIndex: Int?,
    @SerializedName("pageSize")
    var pageSize: String?,
    @SerializedName("startDate")
    var startDate: String?,
    @SerializedName("tenantId")
    var tenantId: String?
)


data class BalanceDto(
    @SerializedName("payAmount")
    var payAmount: String?,
    @SerializedName("unitPrice")
    var unitPrice: String?,
    @SerializedName("availableAmount")
    var availableAmount: String?,
    @SerializedName("createUserId")
    var createUserId: String?
)