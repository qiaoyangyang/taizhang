package com.meiling.account.bean

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class GoosClassify(

    @SerializedName("createTime")
    var createTime: String? = "",
    @PrimaryKey
    @SerializedName("id")
    var id: String = "",
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
    @Ignore
    var select: Boolean = false

)

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
    @Ignore
    var isExpen: Boolean = false,
    @SerializedName("costPrice")
    var costPrice: String? = "",
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
    var id: String? = "",
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
    var tenantId: String? = "",
    @SerializedName("unitViewId")
    var unitViewId: String? = "",
    @SerializedName("updateTime")
    var updateTime: String? = "",
    @SerializedName("goodsSpecsValus")
    var goodsSpecsValus: String? = "",
    @PrimaryKey
    @SerializedName("viewId")
    var viewId: String = "",


    )

//商品入参
data class GoodsController(
    var pageNum: Int = 0,
    var pageSize: Int = 10,
    var sortCode: String = "",
)

data class StorageGoods(
    var goodsType: Int,//	integer($int32)   良品类型 1.良品, 2.不良品
    var goodsViewId: String,//商品id
    var storageNumber: String,//入库数量
    var storeName: String,//入库门店名称
    var storeViewId: String,//入库门店业务id


)

data class DateSplit(
    var startTime: String,//起始时间
    var endTime: String,//1.良品, 2.不良品
    var goodsType: Int = 0,//1.良品, 2.不良品
    var storeViewId: String = "",

)

data class Statistics(
    var startTime: String = "",//起始时间
    var endTime: String = "",//1.良品, 2.不良品
    var storeViewId: String = "",//1.良品, 2.不良品

)


data class DateSplitList(
    var boolean: Boolean? = false,

    @SerializedName("dateValue")
    var dateValue: String? = "",
    @SerializedName("endTime")
    var endTime: String? = "",
    @SerializedName("goodsTotalNumber")
    var goodsTotalNumber: String? = "",
    @SerializedName("startTime")
    var startTime: String? = ""
)

class GoodsSplit(

    @SerializedName("dateValue")
    var dateValue: String? = "",
    @SerializedName("endTime")
    var endTime: String? = "",
    @SerializedName("infoList")
    var infoList: List<Info?>? = listOf(),
    @SerializedName("startTime")
    var startTime: String? = ""
)

data class Info(
    @SerializedName("createTime")
    var createTime: String? = "",
    @SerializedName("goodsImageUrl")
    var goodsImageUrl: String? = "",
    @SerializedName("goodsName")
    var goodsName: String? = "",
    @SerializedName("goodsNumber")
    var goodsNumber: String? = "",
    @SerializedName("realGoodsNumber")
    var realGoodsNumber: String? = "",
    @SerializedName("goodsSpecs")
    var goodsSpecs: String? = "",
    @SerializedName("goodsType")
    var goodsType: Int? = 0,
    @SerializedName("goodsUnit")
    var goodsUnit: String? = "",
    @SerializedName("goodsWeight")
    var goodsWeight: String? = "",
    @SerializedName("isRevoke")
    var isRevoke: Int? = 0,
    @SerializedName("operationId")
    var operationId: String? = "",
    @SerializedName("seriesCode")
    var seriesCode: String? = "",
    @SerializedName("skuCode")
    var skuCode: String? = "",
    @SerializedName("sortCode")
    var sortCode: String? = "",
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("unitViewId")
    var unitViewId: String? = "",
    @SerializedName("userPhone")
    var userPhone: String? = "",
    @SerializedName("viewId")
    var viewId: String? = "",
    @SerializedName("goodsSpecsValues")
    var goodsSpecsValues: String? = ""


)

data class FormStatistics(
    @SerializedName("goodProductGoodsTotalNumber")
    var goodProductGoodsTotalNumber: String? = "",
    @SerializedName("goodProductRate")
    var goodProductRate: String? = "",
    @SerializedName("goodProductRatio")
    var goodProductRatio: String? = "",
    @SerializedName("goodProductTotalNumber")
    var goodProductTotalNumber: String? = "",
    @SerializedName("goodsTotalNumber")
    var goodsTotalNumber: String? = "",
    @SerializedName("storageGoodsTotalNumber")
    var storageGoodsTotalNumber: String? = "",
    @SerializedName("ratioType")
    var ratioType: Int? = 0

)
class PeriodTimeItem (

    @SerializedName("createDate")
    var createDate: String? = "",
    @SerializedName("dateValue")
    var dateValue: String? = "",
    @SerializedName("defectiveProductNumber")
    var defectiveProductNumber:Double? = 0.0,
    @SerializedName("goodProductNumber")
    var goodProductNumber: Double? = 0.0
)
class Spezifikation(
    var goodsSpecsValus:String="",
     var isselect //是否选择
    : Boolean = false


)

