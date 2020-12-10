package com.lxh.cookcommunity.manager.api.base

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultModel<T>(
    val status: Int,
    val msg: String,
    val data: T?
)