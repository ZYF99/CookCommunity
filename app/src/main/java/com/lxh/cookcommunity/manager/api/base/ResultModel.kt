package com.lxh.cookcommunity.manager.api.base

import com.lxh.cookcommunity.manager.Meta
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultModel<T>(
    val meta: Meta?,
    val data: T?
)