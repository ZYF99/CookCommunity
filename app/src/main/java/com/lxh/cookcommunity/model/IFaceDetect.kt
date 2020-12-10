package com.lxh.cookcommunity.model

/**
* 作为人脸识别数据类必须实现的接口
* */
interface IFaceDetect {
    //获取特征码Json
    fun getFaceCodeJson():String?
    //获取头像URL
    fun getAvatarUrl():String?
    //绑定特征码
    fun bindFaceCode(faceCodeJson:String?)
}