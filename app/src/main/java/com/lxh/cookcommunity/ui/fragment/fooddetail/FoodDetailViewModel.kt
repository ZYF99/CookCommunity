package com.lxh.cookcommunity.ui.fragment.fooddetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.CollectFoodRequestModel
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance


class FoodDetailViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val bannerListMutableLiveData = MutableLiveData<List<String>>()
    val foodMutableLiveData = MutableLiveData<Food>()

    fun checkCollect(action:()->Unit){
        apiService.checkFoodCollect(
            CollectFoodRequestModel(data = foodMutableLiveData.value?.id)
        ).doOnApiSuccess {
                action()
        }
    }

    fun collectFood(action:()->Unit){
        apiService.collectFood(
            CollectFoodRequestModel(data = foodMutableLiveData.value?.id)
        ).doOnApiSuccess {
            action()
        }
    }

}