package com.lxh.cookcommunity.ui.fragment.studyvideo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.model.api.home.FoodVideo
import com.lxh.cookcommunity.ui.base.BaseViewModel

class StudyVideoViewModel(application: Application) : BaseViewModel(application) {

    //当前页面序号
    val currentPageMutableLiveData = MutableLiveData<Int>()

    //视频信息
    val foodVideoMutableLiveData = MutableLiveData<FoodVideo>()

}