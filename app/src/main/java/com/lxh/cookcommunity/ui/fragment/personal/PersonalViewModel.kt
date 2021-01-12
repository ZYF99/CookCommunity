package com.lxh.cookcommunity.ui.fragment.personal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.UserProfileModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.zgxwxy.tuputech.util.switchThread
import org.kodein.di.generic.instance

class PersonalViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val myRecentMomentMutableLiveDataList = MutableLiveData<List<MomentContent>>()
    val userProfileMutableLiveData = MutableLiveData<UserProfileModel>()

    //拉取个人信息
    fun fetchUserProfile() {
        apiService.fetchUserProfile()
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                userProfileMutableLiveData.postValue(it.data)
            }.bindLife()
    }

    //拉取我最近的动态
    fun fetchMyMoments() {
        apiService.refreshMomentList(1)
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                myRecentMomentMutableLiveDataList.postValue(it.data?.dataList)
            }.bindLife()
    }


}