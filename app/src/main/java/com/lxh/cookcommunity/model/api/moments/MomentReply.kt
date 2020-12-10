package com.lxh.cookcommunity.model.api.moments

import com.lxh.cookcommunity.model.api.SimpleProfileResp
import java.io.Serializable


const val COMMENT_COMMENT = 0 //评论
const val COMMENT_LIKE = 1 // 点赞

data class MomentComment(
    val commentId: Long,
    val commentType: Int,
    val content: String = "favor",
    val commentator: SimpleProfileResp,
    val commentDate: Long,
    val commentReplyList: List<CommentReply>
)

data class CommentReply(
    val replyId: Long,
    val content: String,
    //val replyBy: UsrProfile,
    val replyDate: Long
): Serializable