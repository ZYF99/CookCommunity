package com.lxh.cookcommunity.ui.fragment.studyvideo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.home.FoodVideo
import com.lxh.cookcommunity.model.api.studyvideo.StudyVideoModel
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance

class StudyVideoViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()

    //当前页面序号
    val currentPageMutableLiveData = MutableLiveData<Int>()

    //所有视频的信息
    val StudyModelMutableLiveData = MutableLiveData<StudyVideoModel>()

    //当前视频信息
    val foodVideoMutableLiveData = MutableLiveData<FoodVideo>()

    //拉取视频教程
    fun fetchFoodStudyVideo(foodId:Long?){
        apiService.fetchFoodStudyVideo(foodId)
            .doOnApiSuccess {
                StudyModelMutableLiveData.postValue(it.data)
            }
    }

}