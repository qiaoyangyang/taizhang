package com.meiling.common.network.data

data class DadaMerchantAddReq(
    var business:String?="",//经营类型
    var phone:String?="",//注册手机号
    var email:String?="",
    var address:String?="",//店铺地址
    var shopName:String?="",
    var lat:String?="",
    var lng:String?="",
    var channelType:String?="",//物流平台标识
    var detailAddress:String?="", //店铺详细地址
    var poiId:String?=""//门店编号
)
