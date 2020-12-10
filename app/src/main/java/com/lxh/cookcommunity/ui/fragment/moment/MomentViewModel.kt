package com.lxh.cookcommunity.ui.fragment.moment

import android.app.Application
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.model.api.SimpleProfileResp
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListViewModel
import io.reactivex.Single
import org.kodein.di.generic.instance

const val pic1 =
    "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1564543140,1383105555&fm=26&gp=0.jpg"
const val pic2 =
    "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2059838807,862258708&fm=26&gp=0.jpg"
const val pic3 =
    "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=439570641,3478888563&fm=26&gp=0.jpg"
const val pic4 =
    "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=835592908,594992964&fm=26&gp=0.jpg"

class MomentViewModel(application: Application) : CommonListViewModel<MomentContent>(application) {

    val apiService by instance<ApiService>()

    //刷新动态列表
    override val refreshFunction: (String?, Int?, Int?) -> Single<ResultModel<CommonListPageModel<MomentContent>>> =
        { ds, wes, we ->
            apiService.refreshCommonList("1", 1, 1)
                .doOnSubscribe {
                    commonListLiveData.postValue(
                        listOf(
                            MomentContent(
                                content = "asfokwhdfiu",
                                publisher = SimpleProfileResp.getDefault(),
                                publishedDate = System.currentTimeMillis(),
                                pictures = listOf(pic1, pic2, pic3, pic4)
                            ),
                            MomentContent(
                                content = "ertgregertgh",
                                publisher = SimpleProfileResp.getDefault(),
                                publishedDate = System.currentTimeMillis(),
                                pictures = listOf(pic1, pic2, pic3)
                            ),
                            MomentContent(
                                content = "tyjhnrtymnuyrmnr",
                                publisher = SimpleProfileResp.getDefault(),
                                publishedDate = System.currentTimeMillis(),
                                pictures = listOf(pic1, pic2)
                            ),
                            MomentContent(
                                content = "erfueywrwerewqr",
                                publisher = SimpleProfileResp.getDefault(),
                                publishedDate = System.currentTimeMillis(),
                                pictures = listOf(pic1)
                            )
                        )
                    )
                }
        }

    //搜索动态列表
    override val searchFunction: (String?, Int, Int) -> Single<ResultModel<CommonListPageModel<MomentContent>>> =
        { ds, wes, we ->
            apiService.searchCommonList("1", 1, 1)
                .doOnSubscribe {

                }
        }

}