package com.meiling.account.bean

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
data class GoosClassify(

    @SerializedName("createTime")
    var createTime: String? = "",
    @PrimaryKey
    @SerializedName("id")
    var id: String="",
    @SerializedName("operationId")
    var operationId: String? = "",
    @SerializedName("sortCode")
    var sortCode: String? = "",
    @SerializedName("sortName")
    var sortName: String? = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("updateTime")
    var updateTime: String? = "",
    @SerializedName("weight")
    var weight: Int? = 0,
    var select: Boolean = false

) {
    @Ignore

    var isExpen: Boolean = false



}
data class GoodsBean(

    @SerializedName("data")
    var data: List<Goods?>? = listOf(),
    @SerializedName("total")
    var total: Int = 0
)

@Entity
data class Goods(
    var chineseAllPinYin: String? = "",
    var chineseFirstPinYin: String? = "",
    var isExpen: Boolean = false,
    @SerializedName("costPrice")
    var costPrice: Int? = 0,
    @SerializedName("createTime")
    var createTime: String? = "",
    @SerializedName("goodsImgurl")
    var goodsImgurl: String? = "",
    @SerializedName("goodsName")
    var goodsName: String? = "",
    @SerializedName("goodsSpecs")
    var goodsSpecs: String? = "",
    @SerializedName("goodsUnit")
    var goodsUnit: String? = "",
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("operationId")
    var operationId: String? = "",

    @SerializedName("retailPrice")
    var retailPrice: Int? = 0,
    @SerializedName("seriesCode")
    var seriesCode: String? = "",
    @SerializedName("skuCode")
    var skuCode: String? = "",
    @SerializedName("sortCode")
    var sortCode: String? = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("unitViewId")
    var unitViewId: String? = "",
    @SerializedName("updateTime")
    var updateTime: String? = "",
    @SerializedName("goodsSpecsValus")
    var goodsSpecsValus: String? = "",
    @PrimaryKey
    @SerializedName("viewId")
    var viewId: String = ""
)

data class GoodsController(
    var pageNum :Int=0,
    var pageSize :Int=10,
    var sortCode :String="",
)