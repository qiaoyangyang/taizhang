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

data class FinancialRecordDetailListReq(
    @SerializedName("viewId")
    var viewId: Int?,
    @SerializedName("pageIndex")
    var pageIndex: Int?,
    @SerializedName("pageSize")
    var pageSize: String?,

)


data class BalanceDto(
    @SerializedName("payAmount")
    var payAmount: String?,
    @SerializedName("unitPrice")
    var unitPrice: String?,
    @SerializedName("availableAmount")
    var availableAmount: String?,
    @SerializedName("createUserId")
    var createUserId: String?,
    @SerializedName("freezeAmount")
    var freezeAmount: String?
)

data class RechargeRecordDto(
    @SerializedName("pageData")
    var pageData: List<PageData?>?,
    @SerializedName("pageNum")
    var pageNum: Int?,
    @SerializedName("pageSize")
    var pageSize: Int?,
    @SerializedName("pages")
    var pages: Int?,
    @SerializedName("total")
    var total: Int?
)

data class PageData(
    @SerializedName("createTime")
    var createTime: String?,
    @SerializedName("createUserId")
    var createUserId: Long?,
    @SerializedName("dealTradeNo")
    var dealTradeNo: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("payAmount")
    var payAmount: String?,
    @SerializedName("payDate")
    var payDate: String?,
    @SerializedName("payEntrance")
    var payEntrance: Int?,
    @SerializedName("payEntranceName")
    var payEntranceName: String?,
    @SerializedName("payType")
    var payType: Int?,
    @SerializedName("payTypeName")
    var payTypeName: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("tenantId")
    var tenantId: Int?,
    @SerializedName("tradeNo")
    var tradeNo: String?,
    @SerializedName("updateTime")
    var updateTime: Long?,
    @SerializedName("updateUserId")
    var updateUserId: Long?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("viewId")
    var viewId: String?
)

data class FinancialRecordDetail(
    @SerializedName("pageResult")
    var pageResult: PageResult?,
    @SerializedName("sum")
    var sum: Double?
)

data class PageResult(
    @SerializedName("pageData")
    var pageData: List<PageData?>?,
    @SerializedName("pageNum")
    var pageNum: Int?,
    @SerializedName("pageSize")
    var pageSize: Int?,
    @SerializedName("pages")
    var pages: Int?,
    @SerializedName("total")
    var total: Int?
) {
    data class PageData(
        @SerializedName("createTime")
        var createTime: String?,
        @SerializedName("createUserId")
        var createUserId: Int?,
        @SerializedName("distributePoiId")
        var distributePoiId: Int?,
        @SerializedName("distributePoiName")
        var distributePoiName: Any?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("orderArrivalTime")
        var orderArrivalTime: String?,
        @SerializedName("orderChannel")
        var orderChannel: Int?,
        @SerializedName("orderChannelName")
        var orderChannelName: String?,
        @SerializedName("orderCompleteTime")
        var orderCompleteTime: String?,
        @SerializedName("orderPoiId")
        var orderPoiId: Int?,
        @SerializedName("orderPoiName")
        var orderPoiName: Any?,
        @SerializedName("orderSerialNumber")
        var orderSerialNumber: String?,
        @SerializedName("orderStatus")
        var orderStatus: Int?,
        @SerializedName("orderViewId")
        var orderViewId: String?,
        @SerializedName("remark")
        var remark: String?,
        @SerializedName("settlementAmount")
        var settlementAmount: String?,
        @SerializedName("settlementDate")
        var settlementDate: String?,
        @SerializedName("settlementType")
        var settlementType: Int?,
        @SerializedName("settlementTypeName")
        var settlementTypeName: String?,
        @SerializedName("status")
        var status: Int?,
        @SerializedName("tenantId")
        var tenantId: Int?,
        @SerializedName("updateTime")
        var updateTime: Long?,
        @SerializedName("updateUserId")
        var updateUserId: Int?,
        @SerializedName("viewId")
        var viewId: String?
    )
}

data class FinancialRecord(
    @SerializedName("pageResult")
    var pageResult: PageResult?,
    @SerializedName("sum")
    var sum: Double?
) {
    data class PageResult(
        @SerializedName("pageData")
        var pageData: List<PageData?>?,
        @SerializedName("pageNum")
        var pageNum: Int?,
        @SerializedName("pageSize")
        var pageSize: Int?,
        @SerializedName("pages")
        var pages: Int?,
        @SerializedName("total")
        var total: Int?
    ) {
        data class PageData(
            @SerializedName("count")
            var count: String?,
            @SerializedName("description")
            var description: String?,
            @SerializedName("remark")
            var remark: String?,
            @SerializedName("settlementAmount")
            var settlementAmount: String?,
            @SerializedName("settlementDate")
            var settlementDate: String?,
            @SerializedName("settlementType")
            var settlementType: Int?,
            @SerializedName("settlementTypeName")
            var settlementTypeName: String?,
            @SerializedName("tenantId")
            var tenantId: Int?,
            @SerializedName("viewId")
            var viewId: String?
        )
    }
}