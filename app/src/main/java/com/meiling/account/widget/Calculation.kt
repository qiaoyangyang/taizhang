package com.meiling.account.widget

import java.math.BigDecimal

/**
 * str1 减去 str2
 * */

fun calculationMinDataStr(str1: String?, str2: String?): String {
    var str1Num = if ("" == str1 || null == str1 || "0" == str1) "0" else str1
    var str2Num = if ("" == str2 || null == str2 || "0" == str2) "0" else str2
    return try {
        var str1 = BigDecimal(str1Num)
        var str2 = BigDecimal(str2Num)
        var strResult = str1.subtract(str2)
        strResult.toString()
    } catch (e: Exception) {
        "0"
    }
}

/**
 * @param str1    被除数
 * @param str2    除数
 * @return 两个参数的商
 */
fun calculationDivDataStr(str1: String?, str2: String?): String? {
    var str1Num = if ("" == str1 || null == str1 || "0" == str1) "0" else str1
    var str2Num = if ("" == str2 || null == str2 || "0" == str1) "0" else str2
    return try {
        if (str2Num == "0") {
            "0"
        } else {
            val b1 = BigDecimal(str1Num)
            val b2 = BigDecimal(str2Num)
            val divide = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP)
            divide.toString()
        }

    } catch (e: java.lang.Exception) {
        "0"
    }
}