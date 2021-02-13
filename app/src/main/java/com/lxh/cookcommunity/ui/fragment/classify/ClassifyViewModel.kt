package com.lxh.cookcommunity.ui.fragment.classify

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.zgxwxy.tuputech.util.switchThread
import org.kodein.di.generic.instance


class ClassifyViewModel(application: Application) : BaseViewModel(application) {

    val foodListMutableLiveData = MutableLiveData<List<Food>>()
    val groupMutableLiveData = MutableLiveData<String>()
    val typeMutableLiveData = MutableLiveData<String>()
    val timeMutableLiveData = MutableLiveData<String>()

    val apiService by instance<ApiService>()

    fun fetchFoodList() {
        apiService.filterFood(
            group = groupMutableLiveData.value,
            type = typeMutableLiveData.value,
            time = timeMutableLiveData.value
        ).switchThread()
            .catchApiError()
            .doOnSuccess {
                foodListMutableLiveData.postValue(it.data?.dataList)
            }.bindLife()

    }

}