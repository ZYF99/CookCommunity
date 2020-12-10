package com.lxh.cookcommunity.manager.sharedpref

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref
import com.lxh.cookcommunity.util.Constants

/**
 *  user data need to be encrypt / decrypt
 *
 */
object SharedPrefModel : KotprefModel() {
    override val kotprefName: String = Constants.SHARED_PREF_FILE_NAME

    //do not use getter after login , this maybe clear , use MainApplication.nowUserId instead.
    var userAccount: String by stringPref()
    var password: String by stringPref()
    var nowUserId: Long by longPref() //当前在设备上登陆过的用户id

    var hasLogin: Boolean by booleanPref(false) //是否已登录

    var macAddress: String by stringPref() //设备Mac地址

    var cacheDir: String = "" //本地文件夹

    //var companySearchCondition: CompanySearchConditionModel by gsonPref(CompanySearchConditionModel())
    //use setUserModel to set data
    var userSettingMap: MutableMap<Long?, UserModel> by gsonPref(hashMapOf())

    fun getUserModel(userId: Long? = nowUserId): UserModel =
        userSettingMap[userId] ?: UserModel().apply {
            userSettingMap[userId] = this
        }

    fun setUserModel(userId: Long? = nowUserId, modify: UserModel.() -> Unit) {
        val map = userSettingMap
        val user = map[userId] ?: UserModel()
        user.apply { modify.invoke(this) }
        map[userId] = user
        userSettingMap = map
    }

    fun updateUserModelSet(map: Map<Long?, UserModel>) {
        userSettingMap = map.toMutableMap()
    }

    fun setDefault() {
        setUserModel {

        }
        userAccount = ""
        password = ""
    }
}
