package com.lxh.cookcommunity.ui.fragment.momentdetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.moments.COMMENT_COMMENT
import com.lxh.cookcommunity.model.api.moments.COMMENT_FAVOR
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance

class MomentDetailViewModel(application: Application) : BaseViewModel(application) {

    private val apiService by instance<ApiService>()
    val momentMutableLiveData = MutableLiveData<MomentContent>()
    val inputCommentMutableLiveData = MutableLiveData<String>()

    //点赞
    fun like() {
        apiService.pushCommentOrLike(
            id = 2384632874,
            type = COMMENT_FAVOR,
            content = inputCommentMutableLiveData.value
        ).doOnApiSuccess {

        }
    }

    //评论
    fun pushComment() {
        apiService.pushCommentOrLike(
            id = 2384632874,
            type = COMMENT_COMMENT,
            content = inputCommentMutableLiveData.value
        ).doOnApiSuccess {

        }
    }


}