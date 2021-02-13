package com.lxh.cookcommunity.ui.fragment.mark

import android.app.Application
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.goods.Goods
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListViewModel
import io.reactivex.Single
import org.kodein.di.generic.instance

class MarkListViewModel(application: Application) : CommonListViewModel<Goods>(application) {

    val apiService by instance<ApiService>()

    //刷新动态列表
    override val refreshFunction: (Int?, Int?) -> Single<ResultModel<CommonListPageModel<Goods>>> =
        {  wes, we ->
            apiService.refreshGoodsList(1)
        }

    //搜索动态列表
    override val searchFunction: (String,Int, Int) -> Single<ResultModel<CommonListPageModel<Goods>>> =
        {  s,wes, we ->
            apiService.searchGoodsList(1, 1)
                .doOnSubscribe {

                }
        }

}