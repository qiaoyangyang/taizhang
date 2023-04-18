package com.meiling.common.network.data


import com.google.gson.annotations.SerializedName
import com.meiling.common.base.IWheel
import java.io.Serializable

data class Children(
    @SerializedName("children")
    var children: ArrayList<Children>? = null,
    @SerializedName("createTime")
    var createTime: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("parentId")
    var parentId: String = "",
    @SerializedName("updateTime")
    var updateTime: String = ""
): IWheel, Serializable {
    override fun getShowText(): String {
        return name
    }
}