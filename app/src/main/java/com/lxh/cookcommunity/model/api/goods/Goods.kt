package com.lxh.cookcommunity.model.api.goods

data class Goods(
    val id: Long? = null,
    val name: String? = null,
    val brief: String? = null,
    val poster: String? = null,
    val images: List<String>? = null,
    val price: Float? = null
)