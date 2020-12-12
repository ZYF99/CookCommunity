package com.lxh.cookcommunity.ui.fragment.personal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.SimpleProfileResp
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.lxh.cookcommunity.ui.fragment.moment.pic1
import com.lxh.cookcommunity.ui.fragment.moment.pic2
import com.lxh.cookcommunity.ui.fragment.moment.pic3
import com.lxh.cookcommunity.ui.fragment.moment.pic4
import org.kodein.di.generic.instance

class PersonalViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()

    val myRecentMomentMutableLiveDataList = MutableLiveData<List<MomentContent>>()

    //拉取我最近的动态
    fun fetchMyMoments() {
        apiService.refreshCommonList("", 0, 10)
            .doOnSubscribe {
                myRecentMomentMutableLiveDataList.postValue(
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
            .doOnApiSuccess {
                myRecentMomentMutableLiveDataList.postValue(it.data?.dataList)
            }
    }

}