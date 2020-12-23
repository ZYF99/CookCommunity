package com.lxh.cookcommunity.ui.fragment.fooddetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance


class FoodDetailViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val bannerListMutableLiveData = MutableLiveData<List<String>>()
    val foodMutableLiveData = MutableLiveData<Food>()

    //拉取Banner
    fun fetchBannerList() {
        bannerListMutableLiveData.postValue(
            listOf(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607513279220&di=2369ecf649d5b2008b8bed9ad0c9b407&imgtype=0&src=http%3A%2F%2Fi2.chuimg.com%2F50d835d3dde44c83969debae054163f9_1280w_1280h.jpg%3FimageView2%2F2%2Fw%2F600%2Finterlace%2F1%2Fq%2F90",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607513279220&di=2369ecf649d5b2008b8bed9ad0c9b407&imgtype=0&src=http%3A%2F%2Fi2.chuimg.com%2F50d835d3dde44c83969debae054163f9_1280w_1280h.jpg%3FimageView2%2F2%2Fw%2F600%2Finterlace%2F1%2Fq%2F90",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607513279220&di=2369ecf649d5b2008b8bed9ad0c9b407&imgtype=0&src=http%3A%2F%2Fi2.chuimg.com%2F50d835d3dde44c83969debae054163f9_1280w_1280h.jpg%3FimageView2%2F2%2Fw%2F600%2Finterlace%2F1%2Fq%2F90"
            )
        )
    }

}