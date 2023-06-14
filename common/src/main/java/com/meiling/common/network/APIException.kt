package com.meiling.common.network

class APIException : RuntimeException {

    var msg: String //错误消息
    var errCode: Int = 0 //错误码
    var errorLog: String? //错误日志

    constructor(errCode: Int, error: String?, errorLog: String? = "") : super(error) {
        this.msg = error ?: "请求失败，请稍后再试"
        this.errCode = errCode
        this.errorLog = errorLog ?: this.msg
    }

    constructor(error: Error, e: Throwable?) {
        errCode = error.getKey()
        msg = error.getValue()
        errorLog = e?.message
    }
}