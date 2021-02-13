package com.lxh.cookcommunity.ui.fragment.commonlist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.zgxwxy.tuputech.util.switchThread
import io.reactivex.Single
import java.util.*

abstract class CommonListViewModel<T>(application: Application) : BaseViewModel(application) {
    var commonListPageModelLiveData = MutableLiveData<CommonListPageModel<T>>()
    //var commonListLiveData = MutableLiveData<List<T>>(ArrayList())

    var isLoadingMore = MutableLiveData(false)
    var isRefreshing = MutableLiveData(false)

    abstract val refreshFunction: (Int?, Int?) -> Single<ResultModel<CommonListPageModel<T>>>

    abstract val searchFunction: ((String,Int, Int) -> Single<ResultModel<CommonListPageModel<T>>>)

    //刷新列表
    fun refreshList() {
        refreshFunction.invoke(1, 10)
            .switchThread()
            .catchApiError()
            .retry()
            .doOnSubscribe {
                isRefreshing.postValue(true)
            }.doOnSuccess { filmList ->
                commonListPageModelLiveData.postValue(filmList?.data)
            }.doFinally {
                isRefreshing.postValue(false)
            }.bindLife()
    }

    //搜索列表
    fun search(key: String) {
        searchFunction.invoke(key,1, 10)
            .switchThread()
            .catchApiError()
            .retry()
            .doOnSubscribe {
                isRefreshing.postValue(true)
            }.doOnSuccess { filmList ->
                commonListPageModelLiveData.postValue(filmList?.data)
            }.doFinally {
                isRefreshing.postValue(false)
            }.bindLife()
    }

    //加载更多
    fun loadMore() {
        refreshFunction.invoke((commonListPageModelLiveData.value?.pageNum ?: 1) + 1, 10)
            .switchThread()
            .catchApiError()
            .retry()
            .doOnSubscribe {
                isLoadingMore.postValue(true)
            }.doOnSuccess { commonListPageResultModel ->

                val listToAdd = commonListPageResultModel.data?.dataList ?: emptyList<T>()
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