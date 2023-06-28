package com.meiling.common.network.data


import com.google.gson.annotations.SerializedName

data class OrderCreateGoodsDto(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("num")
    var num: Int = 1,
    @SerializedName("salePrice")
    var salePrice: String = "",
    @SerializedName("totalPrice")
    var totalPrice: String = "",
    @SerializedName("goodsSpec")
    var goodsSpec: String = "默认规格",
    @SerializedName("imgUrl")
    var imgUrl: String = "https://static.igoodsale.com/default-goods.png"
) {
    var isSelectGoods = false
}

data class OrderCreateGoodsDto1(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("num")
    var num: Int = 1,
    @SerializedName("salePrice")
    var salePrice: String = "",
    @SerializedName("totalPrice")
    var totalPrice: String = "",
    @SerializedName("goodsSpec")
    var goodsSpec: String = "默认规格",
    @SerializedName("imgUrl")
    var imgUrl: String = "https://static.igoodsale.com/default-goods.png"
)

//let goodsDic = [ "goodsSpec": "默认规格",
//"imgUrl": "https://static.igoodsale.com/default-goods.png",
//"name": "string",
//"num": 0,
//"salePrice": 0,
//"totalPrice": 0
//] as [String : Any]


//"arriveTime":"",//收货时间
//"channelId":"11",//渠道ID
//"lat":"",//维度
//"lon":"",//经度
//"poiId":"",//发货门店id
//"recvAddr":"",//收货地址
//"recvName":"",//收货人
//"recvPhone":"",//收货人电话
//"remark":"",//备注
//"shopId":"",//店铺id
//"deliveryType":"",//配送类型(1配送,2自提,3,现售)
//"selfGoodsDtoList":goodsArr

data class OrderCreateSaveDto(
    @SerializedName("arriveTime")
    var arriveTime: String? = "",
    @SerializedName("channelId")
    var channelId: String? = "11",
    @SerializedName("deliveryType")
    var deliveryType: String? = "",
    @SerializedName("lat")
    var lat: String? = "",
    @SerializedName("lon")
    var lon: String? = "",
    @SerializedName("poiId")
    var poiId: String? = "",
    @SerializedName("recvAddr")
    var recvAddr: String? = "",
    @SerializedName("recvName")
    var recvName: String? = "",
    @SerializedName("recvPhone")
    var recvPhone: String? = "",
    @SerializedName("remark")
    var remark: String? = "",
//    @SerializedName("shopId")
//    var shopId: String? = "",
    @SerializedName("selfGoodsDtoList")
    var selfGoodsDtoList: ArrayList<OrderCreateGoodsDto1>?
)
data class OrderCreateAddressDiscern(
    @SerializedName("city")
    var city: String? = "",
    @SerializedName("city_code")
    var cityCode: String? = "",
    @SerializedName("county")
    var county: String? = "",
    @SerializedName("county_code")
    var countyCode: String? = "",
    @SerializedName("detail")
    var detail: String? = "",
    @SerializedName("lat")
    var lat: String? = "",
    @SerializedName("lng")
    var lng: String? = "",
    @SerializedName("person")
    var person: String? = "",
    @SerializedName("phonenum")
    var phonenum: String? = "",
    @SerializedName("province")
    var province: String? = "",
    @SerializedName("province_code")
    var provinceCode: String? = "",
    @SerializedName("town")
    var town: String? = "",
    @SerializedName("town_code")
    var townCode: String? = ""
)
