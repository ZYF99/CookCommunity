package com.lxh.cookcommunity.ui.fragment.personal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.UserProfileModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.zgxwxy.tuputech.util.switchThread
import org.kodein.di.generic.instance

class PersonalViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val myRecentMomentMutableLiveDataList = MutableLiveData<List<MomentContent>>()
    val userProfileMutableLiveData = MutableLiveData<UserProfileModel>()
    var isLoadingMore = MutableLiveData(false)
    var commonListPageModelLiveData = MutableLiveData<CommonListPageModel<MomentContent>>()

    //拉取个人信息
    fun fetchUserProfile() {
        apiService.fetchUserProfile()
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                userProfileMutableLiveData.postValue(it.data)
            }.bindLife()
    }

    //拉取最近的动态
    fun fetchRecentMoments() {
        apiService.refreshMomentList(1)
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                commonListPageModelLiveData.postValue(it.data)
            }.bindLife()
    }

    //加载更多
    fun loadMore() {
        apiService.refreshMomentList((commonListPageModelLiveData.value?.pageNum ?: 1) + 1)
            .switchThread()
            .catchApiError()
            .doOnSubscribe {
                isLoadingMore.postValue(true)
            }.doOnSuccess { commonListPageResultModel ->

                val listToAdd = commonListPageResultModel.data?.dataList ?: emptyList<MomentContent>()
                val newList = commonListPageModelLiveData.value?.dataList?.apply {
                    addAll(listToAdd)
                }
                commonListPageModelLiveData.postValue(commonListPageResultModel.data.apply {
                    this?.dataList = newList
                })
            }.doFinally {
                isLoadingMore.postValue(false)
            }.bindLife()
    }


}