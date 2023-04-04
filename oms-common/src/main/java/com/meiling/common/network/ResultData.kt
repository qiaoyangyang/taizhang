package com.meiling.common.network

data class ResultData<T>(
    val code: Int,
    val msg: String,
    val data: T
)