package com.lxh.cookcommunity.ui.fragment.releasemoment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.luck.picture.lib.entity.LocalMedia
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.ui.base.BaseViewModel
import io.reactivex.Single
import org.kodein.di.generic.instance

class ReleaseMomentViewModel(application: Application) : BaseViewModel(application) {

    private val apiService by instance<ApiService>()

    //是否正在加载
    val idLoading = MutableLiveData(false)

    //已选图片列表
    val selectedList = MutableLiveData(mutableListOf<LocalMedia>())

    //内容
    val content = MutableLiveData("")

    //进度
    val progress = MutableLiveData(0)


    //上传图片并发布动态
    fun release() {

    }

    //处理loading
    private fun <T> Single<T>.dealLoading(): Single<T> {
        return doOnSubscribe { idLoading.postValue(true) }
            .doOnSuccess { idLoading.postValue(false) }
            .doOnError { idLoading.postValue(false) }
    }

}