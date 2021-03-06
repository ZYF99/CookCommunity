package com.lxh.cookcommunity.ui.fragment.personal

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

class PersonalViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val userProfileMutableLiveData = MutableLiveData<UserProfileModel>()
    var isLoadingMore = MutableLiveData(false)
    var commonListPageModelLiveData = MutableLiveData<CommonListPageModel<MomentContent>>()
    val changedMomentMutableLiveData = MutableLiveData<Pair<Int, MomentContent?>>()

    val fansNumMutableLiveData = MutableLiveData<Int>()
    val attentionNumMutableLiveData = MutableLiveData<Int>()


    //点赞
    fun like(index: Int, id: Long?) {
        apiService.pushCommentOrLike(
            CommentMomentRequestModel(
                mid = id,
                type = COMMENT_FAVOR,
                content = "",
                image = ""
            )
        ).doOnApiSuccess {
            changedMomentMutableLiveData.postValue(Pair(index, it.data))
        }
    }

    //拉取个人信息
    fun fetchUserProfileAndRecentMoments() {
        apiService.fetchUserProfile()
            .flatMap {
                userProfileMutableLiveData.postValue(it.data)
                //拉取最近的动态
                apiService.refreshMomentList(pageNo = 1, uid = it.data?.uid)
            }.switchThread()
            .catchApiError()
            .retry()
            .doOnSuccess {
                commonListPageModelLiveData.postValue(it.data)
            }
            .bindLife()
    }

    //拉取我关注的人数
    fun fetchMyFollowNum() {
        apiService.fetchMyFollowNum()
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                attentionNumMutableLiveData.postValue(it.data?.collectNum)
            }.bindLife()
    }

    //拉取我的粉丝人数
    fun fetchMyFansNum() {
        apiService.fetchMyFansNum()
            .switchThread()
            .catchApiError()
            .doOnSuccess {
                fansNumMutableLiveData.postValue(it.data?.collectNum)
            }.bindLife()
    }

    //加载更多
    fun loadMore() {
        apiService.refreshMomentList((commonListPageModelLiveData.value?.pageNum ?: 1) + 1)
            .switchThread()
            .catchApiError()
            .doOnSubscribe {
                isLoadingMore.postValue(true)
            }.doOnSuccess { commonListPageResultModel ->

                val listToAdd = commonListPageResultModel.data?.dataList ?: emptyList<MomentContent>()
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