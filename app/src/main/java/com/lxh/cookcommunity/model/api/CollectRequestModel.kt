package com.lxh.cookcommunity.model.api

data class CollectRequestModel(
    val type: String? = "COLLECT",
    val data: Long? = null
)