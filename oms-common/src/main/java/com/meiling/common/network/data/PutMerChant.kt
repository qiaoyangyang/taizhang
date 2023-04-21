package com.meiling.common.network.data

data class PutMerChant(
    var name:String,//品牌名
    var tenantId:String,
    var merchantThirdChannelVOList:ArrayList<Merchant>
)
