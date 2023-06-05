package com.meiling.oms.bean

import com.meiling.common.network.data.ListSelectModel

data class LogisticsBean(var name:String, override var isSelect: Boolean=false): ListSelectModel {

    override fun getSelectTypeName(): String {
        return name
    }
}