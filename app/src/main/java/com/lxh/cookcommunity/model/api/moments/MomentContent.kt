package com.lxh.cookcommunity.model.api.moments

import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import com.lxh.cookcommunity.model.api.SimpleProfileResp

data class MomentContent(
    val momentId: Long? = null,
    val publisher: SimpleProfileResp? = null,
    val originPublisher: SimpleProfileResp? = null,
    val topic: String? = null,
    val title: String? = null,
    val classify: Int? = null,
    val content: String? = null,
    val pictures: List<String>? = null,
    val publishedDate: Long? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val publishType: String? = null,
    val momentCommentList: List<MomentComment>? = null
) {
    val realCommentList: List<MomentComment>?
        get() {
            return momentCommentList?.filter { it.commentType == COMMENT_COMMENT }
        }

    val likeList: List<MomentComment>?
        get() {
            return momentCommentList?.filter { it.commentType == COMMENT_FAVOR }
        }

    val isLikedByMe: Boolean
        get() {
            return momentCommentList?.find { it.commentator.openId == SharedPrefModel.getUserModel().uid && it.commentType == COMMENT_FAVOR } != null
        }

    override fun equals(other: Any?): Boolean {
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = momentId.hashCode()
        result = 31 * result + publisher.hashCode()
        result = 31 * result + originPublisher.hashCode()
        result = 31 * result + topic.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + this.classify!!
        result = 31 * result + content.hashCode()
        result = 31 * result + pictures.hashCode()
        result = 31 * result + publishedDate.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + publishType.hashCode()
        result = 31 * result + momentCommentList.hashCode()
        return result
    }
}