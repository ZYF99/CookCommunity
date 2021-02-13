package com.lxh.cookcommunity.ui.fragment.moment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.moments.COMMENT_FAVOR
import com.lxh.cookcommunity.model.api.moments.CommentMomentRequestModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListViewModel
import io.reactivex.Single
import org.kodein.di.generic.instance

class MomentViewModel(application: Application) : CommonListViewModel<MomentContent>(application) {

    val apiService by instance<ApiService>()

    val changedMomentMutableLiveData = MutableLiveData<Pair<Int,MomentContent?>>()

    //刷新动态列表
    override val refreshFunction: (Int?, Int?) -> Single<ResultModel<CommonListPageModel<MomentContent>>> =
        {  pageNo, pageSize ->
            apiService.refreshMomentList(pageNo,pageSize)

        }

    //搜索动态列表
    override val searchFunction: (String,Int, Int) -> Single<ResultModel<CommonListPageModel<MomentContent>>> =
        { s,wes, we ->
            apiService.searchMomentList("1", 1, 1)
                .doOnSubscribe {

                }
        }

    //点赞
    fun like(index:Int,id: Long?) {
        apiService.pushCommentOrLike(
            CommentMomentRequestModel(
                mid = id,
                type = COMMENT_FAVOR,
                content = "",
                image = ""
            )
        ).doOnApiSuccess {
            changedMomentMutableLiveData.postValue(Pair(index,it.data))
        }
    }

}