package com.lxh.cookcommunity.ui.fragment.classify

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.base.BaseViewModel


class ClassifyViewModel(application: Application) : BaseViewModel(application) {

    val foodListMutableLiveData = MutableLiveData<List<Food>>()

    fun fetchFoodList() {
        foodListMutableLiveData.postValue(
            listOf(
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food(),
                Food()
            )
        )
    }

}