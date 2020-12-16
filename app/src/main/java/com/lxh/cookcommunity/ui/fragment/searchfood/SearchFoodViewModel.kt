package com.lxh.cookcommunity.ui.fragment.searchfood

import android.app.Application
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListViewModel
import io.reactivex.Single
import org.kodein.di.generic.instance

class SearchFoodViewModel(application: Application) : CommonListViewModel<Food>(application) {

    val apiService by instance<ApiService>()

    override val refreshFunction: (String?, Int?, Int?) -> Single<ResultModel<CommonListPageModel<Food>>> =
        { ds, wes, we ->
            apiService.refreshFoodList("1", 1, 1)
                .doOnSubscribe {
                    commonListLiveData.postValue(listOf(Food(), Food(), Food()))
                }
        }


    override val searchFunction: (String?, Int?, Int?) -> Single<ResultModel<CommonListPageModel<Food>>> =
        { ds, wes, we ->
            apiService.searchFoodList("1", 1, 1)
                .doOnSubscribe {
                    commonListLiveData.postValue(listOf(Food(), Food(), Food()))
                }
        }


}