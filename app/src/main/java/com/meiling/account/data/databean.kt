package com.meiling.account.data

import com.google.gson.annotations.SerializedName

data class AppUpdate(

    @SerializedName("code")
    var code: Int? = 0,
    @SerializedName("msg")
    var msg: String? = "",
    @SerializedName("result")
    var result: Result? = Result()
)

data class Result(
    @SerializedName("appId")
    var appId: String? = "",
    @SerializedName("appName")
    var appName: String? = "",
    @SerializedName("createTime")
    var createTime: String? = "",
    @SerializedName("downloadUrl")
    var downloadUrl: String? = "",
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("updateId")
    var updateId: Int? = 0,
    @SerializedName("updateInstall")
    var updateInstall: Int? = 0,
    @SerializedName("updateLog")
    var updateLog: String? = "",
    @SerializedName("updateTime")
    var updateTime: String? = "",
    @SerializedName("useragent")
    var useragent: String? = "",
    @SerializedName("versionCode")
    var versionCode: String? = "",
    @SerializedName("versionName")
    var versionName: String? = ""
)



data class AndIn(
    var startWarehouseDate: String,
    var endWarehouseDate: String,
    var voucherType: Int=3,
)