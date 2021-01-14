package com.lxh.cookcommunity.ui.fragment.personpersonal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.UserProfileModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.moments.COMMENT_FAVOR
import com.lxh.cookcommunity.model.api.moments.CommentMomentRequestModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.zgxwxy.tuputech.util.switchThread
import org.kodein.di.generic.instance

class PersonPersonalViewModel(application: Application) : BaseViewModel(application) {
    val apiService by instance<ApiService>()

    var isLoadingMore = MutableLiveData(false)
    var commonListPageModelLiveData = MutableLiveData<CommonListPageModel<MomentContent>>()
    var personProfileLiveData = MutableLiveData<UserProfileModel>()
    val changedMomentMutableLiveData = MutableLiveData<Pair<Int,MomentContent?>>()

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

    //拉取最近的动态
    fun fetchRecentMoments() {
        apiService.refreshMomentList(1)
            .switchThread()
            .catchApiError()
            .retry()
            .doOnSuccess {
                commonListPageModelLiveData.postValue(it.data)
            }.bindLife()
    }

    //加载更多
    fun loadMore() {
        apiService.refreshMomentList((commonListPageModelLiveData.value?.pageNum ?: 1) + 1)
            .switchThread()
            .catchApiError()
            .retry()
            .doOnSubscribe {
                isLoadingMore.postValue(true)
            }.doOnSuccess { commonListPageResultModel ->

                val listToAdd =
                    commonListPageResultModel.data?.dataList ?: emptyList<MomentContent>()
                val newList = commonListPageModelLiveData.value?.dataList?.apply {
                    addAll(listToAdd)
                }
                commonListPageModelLiveData.postValue(commonListPageResultModel.data.apply {
                    this?.dataList = newList
                })
            }.doFinally {
                isLoadingMore.postValue(false)
            }.bindLife()
    }

}