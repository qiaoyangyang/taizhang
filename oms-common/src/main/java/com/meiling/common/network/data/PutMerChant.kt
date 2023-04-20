package com.meiling.common.network.data

data class PutMerChant(
    var name:String,
    var tenantId:String,
    var merchantThirdChannelVOList:ArrayList<Merchant>
)
