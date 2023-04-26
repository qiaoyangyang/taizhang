package com.meiling.common.network.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Merchant(
    @SerializedName("accountThirdChannelId")
    var accountThirdChannelId: String = "",
    @SerializedName("appId")
    var appId: String = "",
    @SerializedName("appSecret")
    var appSecret: String = "",
    @SerializedName("iconUrl")
    var iconUrl: String = "",
    @SerializedName("logisticsType")
    var logisticsType: String = "",
    @SerializedName("status")
    var status: String = "",//1是已绑定 0未绑定
    @SerializedName("thirdMerchantId")
    var thirdMerchantId: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("typeName")
    var typeName: String = "",
    @SerializedName("guideUrl")
    var guideUrl:String =""
) : Serializable