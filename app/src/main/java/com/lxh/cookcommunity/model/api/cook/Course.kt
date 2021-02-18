package com.lxh.cookcommunity.model.api.cook

data class Course(
    val cid: Long? = null,
    val name: String? = "三分钟学会番茄炒蛋",
    val image: String? = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=347095799,621118368&fm=26&gp=0.jpg",
    val description: String? = "超简单实用的一道菜",
    val price: Float? = 18.8f
)