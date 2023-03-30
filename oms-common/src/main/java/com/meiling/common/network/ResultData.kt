package com.meiling.common.network

data class ResultData<T>(
    val code: Int,
    val message: String,
    val data: T
)