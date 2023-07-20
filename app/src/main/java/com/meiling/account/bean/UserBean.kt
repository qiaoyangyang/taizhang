package com.meiling.account.bean

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class UserBean(
    var password: String? = "",
    var phone: String?="",
    var issave: Int = 0,//是否保存密码
    @Ignore
    var isselect: Boolean = false,//是否选择
    @PrimaryKey

    var adminUserId: String="",

    var username: String?="",


)


