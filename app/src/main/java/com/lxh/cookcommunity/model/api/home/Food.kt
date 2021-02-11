package com.lxh.cookcommunity.model.api.home

import com.lxh.cookcommunity.model.api.cook.Chef

data class Food(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val material: String? = null,
    val image: String? = null,
    val foodVideoList: List<FoodVideo>? = null,
    val chefProfile: Chef? = null
) {
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (foodVideoList?.hashCode() ?: 0)
        result = 31 * result + (chefProfile?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Food

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (image != other.image) return false
        if (foodVideoList != other.foodVideoList) return false
        if (chefProfile != other.chefProfile) return false

        return true
    }
}

data class FoodVideo(
    val id: Long? = null,
    val name: String? = null, //步骤名
    val warning: String? = null, //注意事项
    val videoUrl: String? = null, //视频URL
    val thumbnailUrl: String? = null //缩略图URL
){

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (warning?.hashCode() ?: 0)
        result = 31 * result + (videoUrl?.hashCode() ?: 0)
        result = 31 * result + (thumbnailUrl?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FoodVideo

        if (id != other.id) return false
        if (name != other.name) return false
        if (warning != other.warning) return false
        if (videoUrl != other.videoUrl) return false
        if (thumbnailUrl != other.thumbnailUrl) return false

        return true
    }
}