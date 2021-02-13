package com.lxh.cookcommunity.model.api

data class CollectFoodRequestModel(
    val type: String? = "COLLECT",
    val data: Long? = null
)