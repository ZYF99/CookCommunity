package com.lxh.cookcommunity.ui.fragment.releasemoment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.luck.picture.lib.entity.LocalMedia
import com.lxh.cookcommunity.BuildConfig
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.moments.ReleaseMomentRequestModel
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.lxh.cookcommunity.ui.base.Event
import com.lxh.cookcommunity.ui.base.LiveEvent
import com.zgxwxy.tuputech.util.switchThread
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.kodein.di.generic.instance
import java.io.File

class ReleaseMomentViewModel(application: Application) : BaseViewModel(application) {

    private val apiService by instance<ApiService>()

    //是否正在加载
    val idLoading = MutableLiveData(false)

    //已选图片列表
    val selectedList = MutableLiveData(mutableListOf<LocalMedia>())

    //内容
    val content = MutableLiveData("")

    //发布成功事件
    val releaseSuccessEvent = LiveEvent<Event<Boolean>>()

    //上传图片并发布动态
    fun release() {
        fun justReleaseSingle(urlList: List<String> = emptyList()) =
            apiService.releaseMoment(
                releaseMomentRequestModel = ReleaseMomentRequestModel(
                    content = content.value,
                    images = urlList
                )
            )

        when {
            selectedList.value?.isNotEmpty() == true -> {
                Observable.fromIterable(
                    selectedList.value
                ).flatMapSingle {
                    val file = File(it.cutPath ?: "")
                    val photoRequestBody: RequestBody =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    val photo: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "imageFile",
                        file.name,
                        photoRequestBody
                    )
                    //上传图片
                    apiService.uploadFile(photo)
                }.toList()
                    .flatMap { imageUrlListResult ->
                        justReleaseSingle(imageUrlListResult.mapNotNull {
                            BuildConfig.BASE_URL + "image/" + it.data?.imagePath
                        })
                    }
            }
            else -> justReleaseSingle()
        }.switchThread()
            .catchApiError()
            .dealLoading()
            .doOnSuccess {
                releaseSuccessEvent.postValue(Event(true))
            }.bindLife()
    }

    //处理loading
    private fun <T> Single<T>.dealLoading(): Single<T> {
        return doOnSubscribe { idLoading.postValue(true) }
            .doOnSuccess { idLoading.postValue(false) }
            .doOnError { idLoading.postValue(false) }
    }

}