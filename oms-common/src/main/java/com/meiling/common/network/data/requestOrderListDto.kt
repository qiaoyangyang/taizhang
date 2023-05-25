package com.meiling.common.network.data
import com.google.gson.annotations.SerializedName



class DataListDto(var startTime:String,var endTime:String,var poiIds : ArrayList<String> ){}

data class DataShop(
    @SerializedName("adminUserIds")
    var adminUserIds: List<Any?>?,
    @SerializedName("channelIds")
    var channelIds: List<Any?>?,
    @SerializedName("classificationViewIds")
    var classificationViewIds: List<Any?>?,
    @SerializedName("deliveryType")
    var deliveryType: List<Any?>?,
    @SerializedName("endTime")
    var endTime: String?,
    @SerializedName("goodsType")
    var goodsType: List<Any?>?,
    @SerializedName("isValid")
    var isValid: Int?,
    @SerializedName("orderStatus")
    var orderStatus: List<Any?>?,
    @SerializedName("orderType")
    var orderType: List<Any?>?,
    @SerializedName("pageIndex")
    var pageIndex: Int?,
    @SerializedName("pageSize")
    var pageSize: Int?,
    @SerializedName("poiIds")
    var poiIds: List<Any?>?,
    @SerializedName("refundStatus")
    var refundStatus: List<Any?>?,
    @SerializedName("sortType")
    var sortType: Int?,
    @SerializedName("startTime")
    var startTime: String?,
    @SerializedName("timeType")
    var timeType: Int?
)