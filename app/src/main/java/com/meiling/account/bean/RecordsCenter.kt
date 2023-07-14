package com.meiling.account.bean

import com.google.gson.annotations.SerializedName


data class Ranking(
    var textcolor: Int? = 0,

    @SerializedName("goodsImgurl") var goodsImgurl: String? = "",
    @SerializedName("goodsName") var goodsName: String? = "",
    @SerializedName("goodsNumber") var goodsNumber: Float? = 0f,
    @SerializedName("goodsUnit") var goodsUnit: String? = "",
    @SerializedName("goodsViewId") var goodsViewId: Any? = Any(),
    @SerializedName("rank") var rank: Int? = 0,
    @SerializedName("rankColour") var rankColour: String? = "",
    @SerializedName("skuCode") var skuCode: String? = ""


)