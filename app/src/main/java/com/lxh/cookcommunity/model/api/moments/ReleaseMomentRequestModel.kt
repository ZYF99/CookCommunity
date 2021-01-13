package com.lxh.cookcommunity.model.api.moments

data class ReleaseMomentRequestModel(
    val content:String? = null,
    val images:List<String>? = null
)