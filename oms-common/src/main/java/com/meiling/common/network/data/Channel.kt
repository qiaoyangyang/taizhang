package com.meiling.common.network.data


import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("createTime")
    var createTime: Long? = 0,
    @SerializedName("id")
    var id: String? = ""
//    @SerializedName("logo")
//    var logo: String = "",
//    @SerializedName("logoBg")
//    var logoBg: String = "",
//    @SerializedName("logoColor")
//    var logoColor: String = "",
//    @SerializedName("logoF")
//    var logoF: String = "",
//    @SerializedName("logoText")
//    var logoText: String = "",
//    @SerializedName("name")
//    var name: String = "",
//    @SerializedName("process")
//    var process: String = "",
//    @SerializedName("remark")
//    var remark: String = "",
//    @SerializedName("status")
//    var status: Int = 0,
//    @SerializedName("type")
//    var type: Int = 0,
//    @SerializedName("updateTime")
//    var updateTime: Long = 0,
//    @SerializedName("viewId")
//    var viewId: Int = 0
)