package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName
import com.meiling.common.base.IWheel


data class ShopBean(


    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("shopList")
    val shopList: List<Shop?>? = listOf()
): IWheel {
    override fun getShowText(): String {
        return name.toString()
    }
}


data class Shop(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("poiId")
    val poiId: Int? = 0,
    @SerializedName("status")
    val status: Int? = 0
): IWheel {
    override fun getShowText(): String {
        return name.toString()
    }
}


