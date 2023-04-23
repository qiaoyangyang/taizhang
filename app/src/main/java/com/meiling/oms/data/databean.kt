package com.meiling.oms.data

import com.google.gson.annotations.SerializedName

data class AppUpdate(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("msg")
    var msg: String?
) {
    data class Data(
        @SerializedName("appId")
        var appId: String?,
        @SerializedName("appName")
        var appName: String?,
        @SerializedName("downloadUrl")
        var downloadUrl: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("updateId")
        var updateId: Int?,
        @SerializedName("updateInstall")
        var updateInstall: Int?,
        @SerializedName("updateLog")
        var updateLog: String?,
        @SerializedName("updateTime")
        var updateTime: Long?,
        @SerializedName("useragent")
        var useragent: String?,
        @SerializedName("versionCode")
        var versionCode: String?,
        @SerializedName("versionName")
        var versionName: String?
    )
}