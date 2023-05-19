package com.meiling.common.utils

import java.math.BigDecimal
import java.text.DecimalFormat

object SaveDecimalUtils {


    /**
     * b
     */
    fun decimalUtils(s1: Double): String? {
        //保留两位小数方式一
        val df1 = DecimalFormat("0.00")
        val big = BigDecimal(s1)
        return df1.format(big)
    }

}