package com.meiling.common.network.data

import com.meiling.common.base.IWheel
import java.io.Serializable

data class AccountItemSelect(
    var id: String = "1",
    var name: String = "1"
) : IWheel {
    override fun getShowText(): String {
        return name.toString()
    }
}

data class AccountCityOrShopDto(
    var id: String = "1",
    var name: String = "1"
) : Serializable {
    var isSelect = false
}