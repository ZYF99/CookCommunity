package com.lxh.cookcommunity.model.api

import com.lxh.cookcommunity.model.api.cook.Course

data class CourseResultModel(
    val courseList:List<Course>? = null
)