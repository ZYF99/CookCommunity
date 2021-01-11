package com.lxh.cookcommunity.model.api.login

data class RegisterRequestModel(
    val account: String? = null,
    val password: String? = null,
    val name: String? = null,
    val gender: String? = "F",
    val avatar: String? = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwx1.sinaimg.cn%2Fmw690%2F5301ff11ly1gb58jwjhikj20p00p0q4l.jpg&refer=http%3A%2F%2Fwx1.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1612937645&t=febbfc9910b57cd1152f7816842bfb14"
    )