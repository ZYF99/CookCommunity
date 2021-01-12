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

    override val refreshFunction: (Int?, Int?) -> Single<ResultModel<CommonListPageModel<Food>>> =
        { pageNo, pageSize ->
            apiService.refreshFoodList(pageNo, pageSize)

        }


    override val searchFunction: (Int?, Int?) -> Single<ResultModel<CommonListPageModel<Food>>> =
        { pageNo, pageSize ->
            apiService.searchFoodList("1", 1, 1)
        }


}