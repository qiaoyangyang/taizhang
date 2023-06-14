package com.meiling.account.wxapi

data class AliPayResp(var map: Map<String, String?>) {
    val resultStatus: String?
        get() = map["resultStatus"]

    private val result: String?
        get() = map["result"]

    private val memo: String?
        get() = map["memo"]

    val isSuccess: Boolean
        get() = resultStatus == "9000"

    val isCancel: Boolean
        get() = resultStatus == "6001"

    val message: String
        get() {
            return when (resultStatus) {
                "9000" -> "支付成功"
                "8000" -> "正在处理中"
                "4000" -> "订单支付失败"
                "5000" -> "重复请求"
                "6001" -> "已取消支付"
                "6002" -> "网络连接出错"
                "6004" -> "正在处理中"
                else -> "支付失败"
            }
        }

    override fun toString(): String {
        return "AliPayResp(map=$map)"
    }


}