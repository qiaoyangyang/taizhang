package com.meiling.account.bean

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

data class GoosClassify(
    @PrimaryKey
    @SerializedName("viewId")
    var viewId: String = "",// 1603702495480254464
    @SerializedName("adminUserId")
    var adminUserId: Long = 0, // 28193829391283910
    @SerializedName("childList")
    var childList: ArrayList<GoosClassify> = arrayListOf<GoosClassify>(),
    @SerializedName("createTime")
    var createTime: String = "", // 2022-12-16T10:44:04.000+00:00
    @SerializedName("creatorName")
    var creatorName: String? = "", // null
    @SerializedName("fullPath")
    var fullPath: String = "", // 1603702495480254464
    @SerializedName("level")
    var level: Int = 0, // 1
    @SerializedName("modifier")
    var modifier: String = "", // 28193829391283910
    @SerializedName("modifierName")
    var modifierName: String? = "", // null
    @SerializedName("name")
    var name: String = "", // 测试标签001
    @SerializedName("parentId")
    var parentId: String = "", // 0
    @SerializedName("updateTime")
    var updateTime: String = "", // 2022-12-16T10:44:04.000+00:00
    var select: Boolean = true

) {
    @Ignore

    var isExpen: Boolean = false



}