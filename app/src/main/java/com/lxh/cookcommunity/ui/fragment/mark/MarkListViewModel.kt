package com.lxh.cookcommunity.ui.fragment.mark

import android.app.Application
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.model.api.SimpleProfileResp
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.goods.Goods
import com.lxh.cookcommunity.model.api.moments.COMMENT_COMMENT
import com.lxh.cookcommunity.model.api.moments.MomentComment
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListViewModel
import com.lxh.cookcommunity.ui.fragment.moment.pic1
import com.lxh.cookcommunity.ui.fragment.moment.pic2
import com.lxh.cookcommunity.ui.fragment.moment.pic3
import com.lxh.cookcommunity.ui.fragment.moment.pic4
import io.reactivex.Single
import org.kodein.di.generic.instance

class MarkListViewModel(application: Application) : CommonListViewModel<Goods>(application) {

    val apiService by instance<ApiService>()

    //刷新动态列表
    override val refreshFunction: (String?, Int?, Int?) -> Single<ResultModel<CommonListPageModel<Goods>>> =
        { ds, wes, we ->
            apiService.refreshGoodsList("1", 1, 1)
                .doOnSubscribe {
                    val item = Goods(
                        name = "辣条",
                        brief = "好吃，便宜",
                        poster = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg1.tbcdn.cn%2Ftfscom%2Fi3%2F2113534920%2FTB272IlspXXXXb7XpXXXXXXXXXX_%21%212113534920.jpg&refer=http%3A%2F%2Fimg1.tbcdn.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611372275&t=a5e59d988d0c70d4447ea0b6ec47b738",
                        images = listOf(
                            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg1.tbcdn.cn%2Ftfscom%2Fi3%2F2113534920%2FTB272IlspXXXXb7XpXXXXXXXXXX_%21%212113534920.jpg&refer=http%3A%2F%2Fimg1.tbcdn.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611372275&t=a5e59d988d0c70d4447ea0b6ec47b738",
                            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zjolcdn.com%2Fmlf%2Fdzw%2Fszxw%2Fktx%2F201805%2FW020180517582550631016.jpg&refer=http%3A%2F%2Fimg.zjolcdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611372275&t=7f1ded2c892dcdd235466ab93e239e1d",
                            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.alicdn.com%2Fimgextra%2Fi4%2F2069608638%2FO1CN01hZKQpV2DgFiUXmYEp_%21%212069608638.jpg_640x640.jpg&refer=http%3A%2F%2Fimg.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611372275&t=2e2e79f35e2f60f2404b503fac3cb600"
                        ),
                        price = 14.5f
                    )
                    commonListLiveData.postValue(listOf(item,item,item,item,item,item))
                }
        }

    //搜索动态列表
    override val searchFunction: (String?, Int, Int) -> Single<ResultModel<CommonListPageModel<Goods>>> =
        { ds, wes, we ->
            apiService.searchGoodsList("1", 1, 1)
                .doOnSubscribe {

                }
        }

}