package com.lxh.cookcommunity.model.api.home

import com.lxh.cookcommunity.model.api.cook.Cook
import com.lxh.cookcommunity.model.api.cook.Course

data class Food(
    val id: Long? = null,
    val name: String? = "西红柿炒鸡蛋",
    val brief: String? = "简单健康有营养，作晚餐更容易消化",
    val imgUrl: String? = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607509002469&di=652b9685d6c67ef5b995b2aba32ca9d6&imgtype=0&src=http%3A%2F%2Fi8.meishichina.com%2Fattachment%2Frecipe%2F2018%2F04%2F21%2F20180421152427817730310606182.jpg%40%2521p800",
    val foodVideoList: List<FoodVideo>? = listOf(
        FoodVideo(
            name = "名字一啊",
            warning = "注意注意注意啊",
            videoUrl = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
            thumbnailUrl = "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"
        ),
        FoodVideo(
            name = "名字二啊",
            warning = "注意注意注意啊",
            videoUrl = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
            thumbnailUrl = "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"
        ), FoodVideo(
            name = "名字三啊",
            warning = "注意注意注意啊",
            videoUrl = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
            thumbnailUrl = "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"
        )

    ),
    val cook: Cook? = Cook(
        name = "张大厨",
        avatar = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.mp.itc.cn%2Fq_mini%2Cc_zoom%2Cw_640%2Fupload%2F20170730%2F2dee9839d5574941bd04b81caa8dd49b_th.jpg&refer=http%3A%2F%2Fimg.mp.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611299154&t=7531c95e59a372539901a921f461b084",
        courseList = listOf(Course(), Course(), Course(), Course(), Course())
    )
) {
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (brief?.hashCode() ?: 0)
        result = 31 * result + (imgUrl?.hashCode() ?: 0)
        result = 31 * result + (foodVideoList?.hashCode() ?: 0)
        result = 31 * result + (cook?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Food

        if (id != other.id) return false
        if (name != other.name) return false
        if (brief != other.brief) return false
        if (imgUrl != other.imgUrl) return false
        if (foodVideoList != other.foodVideoList) return false
        if (cook != other.cook) return false

        return true
    }
}

data class FoodVideo(
    val id: Long? = null,
    val name: String? = null, //步骤名
    val warning: String? = null, //注意事项
    val videoUrl: String? = null, //视频URL
    val thumbnailUrl: String? = null //缩略图URL
){

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (warning?.hashCode() ?: 0)
        result = 31 * result + (videoUrl?.hashCode() ?: 0)
        result = 31 * result + (thumbnailUrl?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FoodVideo

        if (id != other.id) return false
        if (name != other.name) return false
        if (warning != other.warning) return false
        if (videoUrl != other.videoUrl) return false
        if (thumbnailUrl != other.thumbnailUrl) return false

        return true
    }
}