package com.lxh.cookcommunity.ui.fragment.editinfo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.BuildConfig
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.editinfo.EditInfoRequestModel
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.zgxwxy.tuputech.util.switchThread
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.kodein.di.generic.instance
import java.io.File

var userInfoHasChanged = false

class EditInfoViewModel(application: Application) : BaseViewModel(application) {

    private val apiService by instance<ApiService>()

    val avatar = MutableLiveData("")
    val name = MutableLiveData("默认昵称")
    val gender = MutableLiveData("男")
    val signature = MutableLiveData("")

    //拉取个人信息
    fun fetchUserProfile() {
        userInfoHasChanged = false
        apiService.fetchUserProfile()
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                avatar.postValue(it.data?.avatar)
                name.postValue(it.data?.name)
                gender.postValue(if (it.data?.gender == "F") "女" else "男")
                signature.postValue(it.data?.aboutMe)
            }.bindLife()
    }

    //修改头像
    fun editAvatar() {
        val file = File(avatar.value ?: "")
        val photoRequestBody: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val photo: MultipartBody.Part = MultipartBody.Part.createFormData(
            "imageFile",
            file.name,
            photoRequestBody
        )

        apiService.uploadFile(photo)
            .flatMap {
                apiService.editUserProfile(
                    EditInfoRequestModel(avatar = BuildConfig.BASE_URL + "image/" + it.data?.imagePath)
                )
            }.switchThread()
            .catchApiError()
            .doOnSuccess {
                userInfoHasChanged = true
            }.bindLife()
    }

    //修改昵称
    fun editName() {
        apiService.editUserProfile(
            EditInfoRequestModel(name = name.value)
        ).doOnApiSuccess {
            userInfoHasChanged = true
        }
    }

    //修改性别
    fun editGender() {
        apiService.editUserProfile(
            EditInfoRequestModel(gender = if (gender.value == "女") "F" else "M")
        ).doOnApiSuccess {
            userInfoHasChanged = true
        }
    }

    //修改个人签名
    fun editSignature() {
        apiService.editUserProfile(
            EditInfoRequestModel(aboutMe = signature.value)
        ).doOnApiSuccess {
            userInfoHasChanged = true
        }
    }

}