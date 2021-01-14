package com.lxh.cookcommunity.model.api.cook

/**
 * 厨师
 * */
data class Cook(
    val id: Long? = null,
    val name: String? = null,
    val avatar: String? = null,
    val courseList: List<Course>? = null
){

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (avatar?.hashCode() ?: 0)
        result = 31 * result + (courseList?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cook

        if (id != other.id) return false
        if (name != other.name) return false
        if (avatar != other.avatar) return false
        if (courseList != other.courseList) return false

        return true
    }
}