package com.meiling.common.network.data
import com.google.gson.annotations.SerializedName



data class BalanceItem(
    @SerializedName("accountNo")
    var accountNo: String = "",
    @SerializedName("balance")
    var balance: String = "",
    @SerializedName("channelType")
    var channelType: String = "",
    @SerializedName("iconUrl")
    var iconUrl: String = "",
    @SerializedName("stationCommonId")
    var stationCommonId: ArrayList<BalanceChildItem> = ArrayList()
)

data class BalanceChildItem(
    var id:String,
    var name:String
)