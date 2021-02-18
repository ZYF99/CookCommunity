package com.lxh.cookcommunity.model.api

import com.lxh.cookcommunity.model.api.home.BannerModel

data class BannerResultModel(
    val bannerList:List<BannerModel>? = null
)