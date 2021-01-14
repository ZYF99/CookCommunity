package com.lxh.cookcommunity.model.api.goods

data class Goods(
    val id: Long? = null,
    val name: String? = null,
    val brief: String? = null,
    val poster: String? = null,
    val images: List<String>? = null,
    val price: Float? = null
){

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (brief?.hashCode() ?: 0)
        result = 31 * result + (poster?.hashCode() ?: 0)
        result = 31 * result + (images?.hashCode() ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Goods

        if (id != other.id) return false
        if (name != other.name) return false
        if (brief != other.brief) return false
        if (poster != other.poster) return false
        if (images != other.images) return false
        if (price != other.price) return false

        return true
    }
}