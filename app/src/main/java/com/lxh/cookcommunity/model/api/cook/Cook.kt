package com.lxh.cookcommunity.model.api.cook

/**
 * 厨师
 * */
data class Cook(
    val id: Long? = null,
    val name: String? = null,
    val avatar: String? = null,
    val courseList: List<Course>? = null
)