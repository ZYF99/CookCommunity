package com.lxh.cookcommunity.model.api.moments

import com.lxh.cookcommunity.model.api.SimpleProfileResp
import java.io.Serializable


const val COMMENT_COMMENT = "comment" //评论
const val COMMENT_FAVOR = "favor" // 点赞

data class MomentComment(
    val commentId: Long,
    val commentType: String,
    val content: String = "favor",
    val commentator: SimpleProfileResp,
    val commentDate: Long
)