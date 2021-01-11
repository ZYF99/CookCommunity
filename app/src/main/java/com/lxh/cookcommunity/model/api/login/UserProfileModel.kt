package com.lxh.cookcommunity.model.api.login

data class UserProfileModel(
    val uid: Long? = null,
    val account: String? = null,
    val name: String? = null,
    val gender: String? = "F",
    val aboutMe: String? = "个性签名",
    val avatar: String? = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwx1.sinaimg.cn%2Fmw690%2F5301ff11ly1gb58jwjhikj20p00p0q4l.jpg&refer=http%3A%2F%2Fwx1.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1612937645&t=febbfc9910b57cd1152f7816842bfb14",
    val status:Int? = 0,
    val createTime:Long? = null,
    val updateTime:Long? = null,
    val lastLoginTime:Long? = null,
    val token:String? = null

)