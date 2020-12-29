package com.lxh.cookcommunity.ui.fragment.editinfo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.ui.base.BaseViewModel
import io.reactivex.Single
import okhttp3.ResponseBody
import org.kodein.di.generic.instance

var userInfoHasChanged = false

class EditInfoViewModel(application: Application) : BaseViewModel(application) {

    private val apiService by instance<ApiService>()

    val avatar = MutableLiveData("")
    val name = MutableLiveData("默认昵称")
    val gender = MutableLiveData("男")
    val signature = MutableLiveData("")
    val isUpdating = MutableLiveData(false)

    //修改头像
    fun editAvatar() {
        /*imageService.upLoadImage(avatar.value!!) {
            //onProgress
            uploadProgress.postValue(it)
        }.doOnSubscribe { isUpdating.postValue(true) }
            .doFinally { isUpdating.postValue(false) }
            .flatMap { key ->
                val url = getImageUrlFromServer(key)
                avatar.postValue(url)
                editUserInfo("avatar", url)?.switchThread()
            }?.doOnApiSuccess {

            }*/

    }

    //修改昵称
    fun editName() {
        /*editUserInfo("name", name.value!!)?.doOnApiSuccess {

        }*/
    }

    //修改性别
    fun editGender() {
        /*editUserInfo("gender", gender.value!!)
            ?.doOnApiSuccess {

            }*/
    }


    //修改个人签名
    fun editSignature() {
        /*editUserInfo("signature", signature.value!!)?.doOnApiSuccess {

        }*/
    }

    //注销
    fun logout() {
        /*return userService.logout()
            .switchThread()
            .catchApiError()*/
    }

    fun initData() {
        //初始化原始信息
        /*getLocalUserInfo().run {
            this@EditInfoViewModel.avatar.postValue(avatar)
            this@EditInfoViewModel.name.postValue(name)
            this@EditInfoViewModel.gender.postValue(gender)
            this@EditInfoViewModel.birthday.postValue(birthday)
            this@EditInfoViewModel.signature.postValue(signature)
            this@EditInfoViewModel.profession.postValue(profession)
            this@EditInfoViewModel.aboutMe.postValue(aboutMe)
        }*/
    }

    /*private fun <T> editUserInfo(key: String, value: T): Single<ResultModel<String>>? {
        return userService.editUserInfo(
            hashMapOf(Pair(key, value.toString()))
        ).dealLoading()
            .doOnSuccess {
                userInfoHasChanged = true
            }

    }*/

}