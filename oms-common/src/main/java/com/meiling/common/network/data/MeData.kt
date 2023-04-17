package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName


data class MessageDto(
    @SerializedName("content")
    var content: List<Content?>?,
    @SerializedName("contentT")
    var contentT: Any?,
    @SerializedName("extra")
    var extra: String?,
    @SerializedName("pageIndex")
    var pageIndex: Int?,
    @SerializedName("pageSize")
    var pageSize: Int?,
    @SerializedName("total")
    var total: Int?,
    @SerializedName("totalPage")
    var totalPage: Int?
) {
    data class Content(
        @SerializedName("content")
        var content: String?,
        @SerializedName("goodsMessage")
        var goodsMessage: Any?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("messageType")
        var messageType: Int?,
        @SerializedName("orderMessage")
        var orderMessage: OrderMessage?,
        @SerializedName("orderViewId")
        var orderViewId: String?,
        @SerializedName("params")
        var params: String?,
        @SerializedName("pushTime")
        var pushTime: String?,
        @SerializedName("read")
        var read: Int?,
        @SerializedName("repositoryOrderMessage")
        var repositoryOrderMessage: Any?,
        @SerializedName("shopId")
        var shopId: Int?,
        @SerializedName("tenantId")
        var tenantId: Int?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("type")
        var type: Int?
    ) {
        data class OrderMessage(
            @SerializedName("channelId")
            var channelId: Int?,
            @SerializedName("channelImg")
            var channelImg: String?,
            @SerializedName("receiverInfo")
            var receiverInfo: String?
        )
    }
}
