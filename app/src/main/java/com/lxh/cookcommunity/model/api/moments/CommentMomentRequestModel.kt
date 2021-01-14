package com.lxh.cookcommunity.model.api.moments

data class CommentMomentRequestModel(
    val content:String? = null,
    val image:String? = null,
    val type:String? = null, //点赞 Favor 评论
    val mid:Long? = null
)