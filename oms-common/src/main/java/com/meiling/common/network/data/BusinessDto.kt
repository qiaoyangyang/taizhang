package com.meiling.common.network.data


data class BusinessDto(
    var businessCategory: String? = "",//经营类目，id逗号隔开
    var city: String? = "",//id逗号隔开
    var enterpriseName: String? = "",//企业名称
    var password: String? = "",
    var phone: String? = "",
    var logo: String? = "https://static.igoodsale.com/default-logo-header.png",
    var salesChannel: String? = "",//id逗号隔开
    var tenantHead: String? = "",//管理员姓名
    var tenantName: String? = "",//品牌名称
    var tenantType: String? = "1",//身份 1企业 2个人 3其他
    var userName: String? = "",
    var isChain: String? = "1",//是否连锁 1：是 0：否
)