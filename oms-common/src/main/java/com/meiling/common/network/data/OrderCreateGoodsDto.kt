package com.meiling.common.network.data


import com.google.gson.annotations.SerializedName

data class OrderCreateGoodsDto(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("parentId")
    var num: Int = 1,
    @SerializedName("updateTime")
    var price: String = ""
) {
    var isSelectGoods = false
}
