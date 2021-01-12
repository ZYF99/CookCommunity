package com.lxh.cookcommunity.ui.fragment.personpersonal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.zgxwxy.tuputech.util.switchThread
import org.kodein.di.generic.instance


class PersonPersonalViewModel(application: Application) : BaseViewModel(application) {
    val apiService by instance<ApiService>()

    val myRecentMomentMutableLiveDataList = MutableLiveData<List<MomentContent>>()

    //拉取最近的动态
    fun fetchRecentMoments() {
        apiService.refreshMomentList(1)
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                myRecentMomentMutableLiveDataList.postValue(it.data?.dataList)
            }.bindLife()
    }

}