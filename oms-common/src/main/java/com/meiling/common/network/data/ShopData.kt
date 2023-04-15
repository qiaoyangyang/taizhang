package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName
import com.meiling.common.base.IWheel
import java.io.Serializable
import java.math.BigDecimal


data class ShopBean(


    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("shopList")
    val shopList: List<Shop?>? = listOf()
) : IWheel, Serializable {
    override fun getShowText(): String {
        return name.toString()
    }
}


data class Shop(
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("poiId")
    var poiId: String? = "",
    @SerializedName("status")
    var status: String? = ""


) : Serializable, IWheel {
    override fun getShowText(): String {
        return name.toString()
    }
}

data class ThrillBen(


    @SerializedName("amount")
    val amount: Amount? = Amount(),
    @SerializedName("encrypted_code")
    val encryptedCode: String? = "",//验券实际所需要的券码
    @SerializedName("expire_time")
    val expireTime: Int? = 0,
    @SerializedName("sku")
    val sku: Sku? = Sku()
): Serializable

data class Amount(
    @SerializedName("coupon_pay_amount")
    val couponPayAmount: Int? = 0,
    @SerializedName("original_amount")
    val originalAmount: String? = "",//划线价
    @SerializedName("pay_amount")
    val payAmount: String? = "",//购买价格
    @SerializedName("payment_discount_amount")
    val paymentDiscountAmount: Int? = 0
): Serializable

data class Sku(
    @SerializedName("account_id")
    val accountId: String? = "",
    @SerializedName("groupon_type")
    val grouponType: Int? = 0,
    @SerializedName("market_price")
    val marketPrice: Int? = 0,
    @SerializedName("sku_id")
    val skuId: String? = "",
    @SerializedName("sold_start_time")
    val soldStartTime: Int? = 0,
    @SerializedName("third_sku_id")
    val thirdSkuId: String? = "",
    @SerializedName("title")
    val title: String? = ""//券码名称
): Serializable


data class Writeoffhistory(

    @SerializedName("pageData")
    val pageData: ArrayList<WriteoffhistoryPageData?>? = ArrayList(),
    @SerializedName("pageNum")
    val pageNum: Int? = 0,
    @SerializedName("pageSize")
    val pageSize: Int? = 0,
    @SerializedName("pages")
    val pages: Int? = 0,
    @SerializedName("total")
    val total: Int? = 0
)

data class WriteoffhistoryPageData(
    @SerializedName("coupon")
    val coupon: Coupon? = Coupon(),
    @SerializedName("orderViewId")
    val orderViewId: Any? = Any(),
    @SerializedName("shopName")
    val shopName: String? = "",
    @SerializedName("sku")
    val sku: Any? = Any()
) : Serializable

data class Coupon(
    @SerializedName("adminViewId")
    val adminViewId: String? = "",//操作人id
    @SerializedName("cancelType")
    val cancelType: Int? = 0,
    @SerializedName("couponBuyPrice")
    val couponBuyPrice: String? = "",//券码购买价格
    @SerializedName("couponCode")
    val couponCode: String? = "",//券码
    @SerializedName("couponCount")
    val couponCount: Int? = 0,//券码数量
    @SerializedName("couponGoodsOrderSnapshot")
    val couponGoodsOrderSnapshot: Any? = Any(),
    @SerializedName("couponUseOrderViewId")
    val couponUseOrderViewId: Any? = Any(),
    @SerializedName("couponUseTime")
    val couponUseTime: String? = "",//券码核销时间
    @SerializedName("createTime")
    val createTime: Long? = 0,
    @SerializedName("dealMenu")
    val dealMenu: String? = "",//券码三方数据详情
    @SerializedName("dealTitle")
    val dealTitle: String? = "",//券码名称
    @SerializedName("dealValue")
    val dealValue: String? = "",//券码划线价
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("isVoucher")
    val isVoucher: Int? = 0,//1。团购 2。代金
    @SerializedName("operateSource")
    val operateSource: Int? = 0,
    @SerializedName("orderId")
    val orderId: String = "",//券码补单订单id
    @SerializedName("poiId")
    val poiId: Int? = 0,
    @SerializedName("requestId")
    val requestId: String? = "",
    @SerializedName("shopId")
    val shopId: String? = "",
    @SerializedName("shopName")
    val shopName: String? = "",
    @SerializedName("spuViewId")
    val spuViewId: String? = "",
    @SerializedName("status")
    val status: Int? = 0,//-1。撤销 1。未使用 2。已使用
    @SerializedName("tenantId")
    val tenantId: Int? = 0,
    @SerializedName("ticketTransId")
    val ticketTransId: String? = "",
    @SerializedName("type")
    val type: Int? = 0, // 2。美团 5。抖音
    @SerializedName("undoType")
    var undoType: Int? = 0, //1。已撤销 0。未撤销
    @SerializedName("updateTime")
    val updateTime: Long? = 0,
    @SerializedName("userId")
    val userId: String? = "",
    @SerializedName("viewId")
    val viewId: Long? = 0
) : Serializable


data class ThrillItem(
    @SerializedName("adminViewId")
    val adminViewId: String? = "",
    @SerializedName("cancelType")
    val cancelType: Int? = 0,
    @SerializedName("couponBuyPrice")
    val couponBuyPrice: String? = "",
    @SerializedName("couponCode")
    val couponCode: String? = "",
    @SerializedName("couponCount")
    val couponCount: Int? = 0,
    @SerializedName("couponGoodsOrderSnapshot")
    val couponGoodsOrderSnapshot: Any? = Any(),
    @SerializedName("couponUseOrderViewId")
    val couponUseOrderViewId: Any? = Any(),
    @SerializedName("couponUseTime")
    val couponUseTime: String? = "",
    @SerializedName("createTime")
    val createTime: Long? = 0,
    @SerializedName("dealMenu")
    val dealMenu: String? = "",
    @SerializedName("dealTitle")
    val dealTitle: String? = "",
    @SerializedName("dealValue")
    val dealValue: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("isVoucher")
    val isVoucher: Int? = 0,
    @SerializedName("operateSource")
    val operateSource: Int? = 0,
    @SerializedName("orderId")
    val orderId: String? = "",
    @SerializedName("poiId")
    val poiId: Int? = 0,
    @SerializedName("requestId")
    val requestId: Any? = Any(),
    @SerializedName("shopId")
    val shopId: Int? = 0,
    @SerializedName("shopName")
    val shopName: String? = "",
    @SerializedName("spuViewId")
    val spuViewId: Any? = Any(),
    @SerializedName("status")
    val status: Int? = 0,
    @SerializedName("tenantId")
    val tenantId: Int? = 0,
    @SerializedName("ticketTransId")
    val ticketTransId: String? = "",
    @SerializedName("type")
    val type: Int? = 0,
    @SerializedName("undoType")
    val undoType: Int? = 0,
    @SerializedName("updateTime")
    val updateTime: Long? = 0,
    @SerializedName("userId")
    val userId: Any? = Any(),
    @SerializedName("viewId")
    val viewId: Long? = 0
) : Serializable


data class Meituan(

    @SerializedName("count")
    val count: String? = "",
    @SerializedName("couponBuyPrice")
    val couponBuyPrice: String? = "",
    @SerializedName("couponCode")
    val couponCode: String? = "",
    @SerializedName("couponEndTime")
    val couponEndTime: String? = "",
    @SerializedName("couponUseTime")
    val couponUseTime: String? = "",
    @SerializedName("dealBeginTime")
    val dealBeginTime: String? = "",
    @SerializedName("dealId")
    val dealId: String? = "",
    @SerializedName("dealMenu")
    val dealMenu: List<List<DealMenu?>?>? = listOf(),
    @SerializedName("dealPrice")
    val dealPrice: String? = "",
    @SerializedName("dealTitle")
    val dealTitle: String? = "",
    @SerializedName("dealValue")
    val dealValue: Int? = 0,
    @SerializedName("isVoucher")
    val isVoucher: Int? = 0,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("minConsume")
    val minConsume: Int? = 0,
    @SerializedName("platformTitle")
    val platformTitle: String? = "",
    @SerializedName("rawTitle")
    val rawTitle: String? = "",
    @SerializedName("result")
    val result: Int? = 0,
    @SerializedName("shopName")
    val shopName: String? = "",
    @SerializedName("singleValue")
    val singleValue: Int? = 0,
    @SerializedName("volume")
    val volume: Int? = 0
) : Serializable

data class DealMenu(
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("desc")
    val desc: String? = "",
    @SerializedName("images")
    val images: String? = "",
    @SerializedName("notDishes")
    val notDishes: String? = "",
    @SerializedName("price")
    val price: String? = "",
    @SerializedName("specification")
    val specification: String? = "",
    @SerializedName("total")
    val total: String? = "",
    @SerializedName("type")
    val type: String? = ""

) : Serializable


data class RecordCodeNumber(
//    "total":71111.02,"count":604
    @SerializedName("total")
    val total: String,//来源
    @SerializedName("count")
    val count: String//来源
)

data class VerificationScreening(
    var startDate: String,//开始时间
    var endDate: String,//结束时间
    var timetype: Int,//时间类型 0自定义时间 1 昨天 2 今天 3 近七天 4 进30天

    var poiId: String,// 门店ID
    var shopName:  String,// 门店ID
    var poiIdtype: String,// 门店类型 0 全部门店 1 定义门店

    var status: String,// 日期类型  2.收货 -1.来单子 0出货
    var isVoucher: String,// 订单平台  1.美团 2.饿了么 0 全部


) : Serializable