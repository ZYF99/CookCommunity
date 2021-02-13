package com.lxh.cookcommunity.model.api.studyvideo

import com.lxh.cookcommunity.model.api.home.FoodVideo

data class StudyVideoModel(
    val cid:Long? = null,
    val name:String? = null,
    val image:String? = null,
    val description:String? = null,
    val curriculumRspList:List<FoodVideo>? = null
)