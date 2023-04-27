package com.meiling.common.utils

import com.meiling.common.base.IWheel

data class NotificationDto(var name: String, var num: String, var type: String, var time: String)

data class PlayNumber(
    var id: String = "1",
    var name: String = "1"
) : IWheel {
    override fun getShowText(): String {
        return name.toString()
    }
}