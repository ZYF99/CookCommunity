package com.lxh.cookcommunity.model.api.moments

import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import com.lxh.cookcommunity.model.api.UserProfileModel

data class MomentContent(
    val mid: Long? = null,
    val profile: UserProfileModel? = null,
    val content: String? = null,
    val images: List<String>? = null,
    val createTime: Long? = null,
    val listOfComment: List<MomentComment>? = null
) {
    val realCommentList: List<MomentComment>?
        get() {
            return listOfComment?.filter { it.type == COMMENT_COMMENT }
        }

    val likeList: List<MomentComment>?
        get() {
            return listOfComment?.filter { it.type == COMMENT_FAVOR }
        }

    val isLikedByMe: Boolean
        get() {
            return listOfComment?.find { it.profile?.uid == SharedPrefModel.nowUserId && it.type == COMMENT_FAVOR } != null
        }

    override fun equals(other: Any?): Boolean {
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = mid.hashCode()
        result = 31 * result + profile.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + images.hashCode()
        result = 31 * result + createTime.hashCode()
        result = 31 * result + (listOfComment ?: emptyList()).sumBy {
            it.hashCode()
        }
        return result
    }
}