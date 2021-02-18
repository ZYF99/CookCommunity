package com.lxh.cookcommunity.ui.fragment.cookpersonal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.cook.Chef
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance


class CookPersonalViewModel(application: Application) : BaseViewModel(application) {

    //厨师个人信息
    val cookMutableLiveData = MutableLiveData<Chef>()
    val fansNumMutableLiveData = MutableLiveData<Int>()

    val apiService by instance<ApiService>()

    fun fetchChefCourse() {
        apiService.fetchChefCourse(cookMutableLiveData.value?.uid)
            .doOnApiSuccess {
                cookMutableLiveData.postValue(
                    cookMutableLiveData.value?.copy(
                        courseList = it.data?.courseList
                    )
                )
            }
    }

    fun fetchChefFans() {
        apiService.fetchChefFans(cookMutableLiveData.value?.uid)
            .doOnApiSuccess {
                fansNumMutableLiveData.postValue(it.data?.followNum)
            }
    }

}