package com.meiling.common.network.data
import com.google.gson.annotations.SerializedName


data class ByTenantId(
    @SerializedName("logistics")
    var logistics: Int? = 0,//物流是否绑定 1绑定;-1没绑定
    @SerializedName("poi")
    var poi: Int? = 0,//门店是否创建 1绑定;-1没绑定
    @SerializedName("shop")
    var shop: Int? = 0//渠道是否创建 1绑定;-1没绑定
)