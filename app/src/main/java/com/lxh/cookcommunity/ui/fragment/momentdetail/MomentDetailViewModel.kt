package com.lxh.cookcommunity.ui.fragment.momentdetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.moments.COMMENT_COMMENT
import com.lxh.cookcommunity.model.api.moments.COMMENT_FAVOR
import com.lxh.cookcommunity.model.api.moments.CommentMomentRequestModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance

class MomentDetailViewModel(application: Application) : BaseViewModel(application) {

    private val apiService by instance<ApiService>()
    val momentMutableLiveData = MutableLiveData<MomentContent>()
    val inputCommentMutableLiveData = MutableLiveData<String>()

    //点赞
    fun like(id: Long? = momentMutableLiveData.value?.mid) {
        apiService.pushCommentOrLike(
            CommentMomentRequestModel(
                mid = id,
                type = COMMENT_FAVOR,
                content = "",
                image = ""
            )
        ).doOnApiSuccess {
            momentMutableLiveData.postValue(it.data)
        }
    }

    //评论
    fun pushComment(id: Long? = momentMutableLiveData.value?.mid) {
        apiService.pushCommentOrLike(
            CommentMomentRequestModel(
                mid = id,
                type = COMMENT_COMMENT,
                content = inputCommentMutableLiveData.value,
                image = ""
            )
        ).doOnApiSuccess {
            momentMutableLiveData.postValue(it.data)
        }
    }

    //拉取动态详情
    fun fetchMomentDetail() {
        apiService.fetchMomentDetail(
            mid = momentMutableLiveData.value?.mid
        ).doOnApiSuccess {
            momentMutableLiveData.postValue(it.data)
        }
    }

}