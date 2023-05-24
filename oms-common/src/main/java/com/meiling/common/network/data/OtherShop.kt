package com.meiling.common.network.data


import com.google.gson.annotations.SerializedName

data class OtherShop(
    @SerializedName("thirdShopId")
    var thirdShopId: String = "",
    @SerializedName("thirdShopName")
    var thirdShopName: String = "",
    var select:Boolean?=false
):java.io.Serializable