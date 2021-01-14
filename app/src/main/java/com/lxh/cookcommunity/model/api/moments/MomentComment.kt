package com.lxh.cookcommunity.model.api.moments

import com.lxh.cookcommunity.model.api.UserProfileModel

const val COMMENT_COMMENT = "Reply" //评论
const val COMMENT_FAVOR = "Favor" // 点赞

data class MomentComment(
    val cid: Long? = null,
    val type: String? = null,
    val profile: UserProfileModel? = null,
    val content: String? = null,
    val image: String? = null,
    val createTime: Long? = null
) {

    override fun hashCode(): Int {
        var result = cid?.hashCode() ?: 0
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (profile?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (createTime?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        return hashCode() == other.hashCode()
    }
}