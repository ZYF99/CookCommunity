package com.lxh.cookcommunity.ui.fragment.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.cook.Chef
import com.lxh.cookcommunity.model.api.goods.Goods
import com.lxh.cookcommunity.model.api.home.BannerModel
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.zgxwxy.tuputech.util.switchThread
import org.kodein.di.generic.instance

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val foodListMutableLiveData = MutableLiveData<List<Food>>()
    val bannerListMutableLiveData = MutableLiveData<List<BannerModel>>()
    val isRefreshingMutableLiveData = MutableLiveData<Boolean>()

    //拉取轮播图列表
    fun fetchBannerList() {
        apiService.refreshBanner()
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                bannerListMutableLiveData.postValue(it.data?.bannerList)
            }
            .bindLife()
    }

    //拉取菜单列表
    fun fetchFoodList() {
        apiService.refreshRecommendDished()
            .switchThread()
            .catchApiError()
            .doFinally {
                isRefreshingMutableLiveData.postValue(false)
            }
            .doOnSuccess {
                foodListMutableLiveData.postValue(it.data?.dataList)
            }.bindLife()
    }

    //拉取商品详情
    fun fetchGoodDetail(goodsId: Long?, action: (Goods?) -> Unit) {
        apiService.fetchGoodsDetail(goodsId)
            .doOnApiSuccess {
                action(it.data)
            }
    }

    //拉取厨子详情
    fun fetchChefDetail(chefId: Long?, action: (Chef?) -> Unit) {
        apiService.fetchChefDetail(chefId)
            .doOnApiSuccess {
                action(it.data)
            }
    }

    //拉取菜品详情
    fun fetchFoodDetail(foodId: Long?, action: (Food?) -> Unit) {
        apiService.fetchFoodDetail(foodId)
            .doOnApiSuccess {
                action(it.data)
            }
    }

}