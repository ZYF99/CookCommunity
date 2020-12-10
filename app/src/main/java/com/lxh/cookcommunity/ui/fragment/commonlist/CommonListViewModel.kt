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
    var commonListLiveData = MutableLiveData<List<T>>(ArrayList())
    private var currentPageNum = 1

    var isLoadingMore = MutableLiveData(false)
    var isRefreshing = MutableLiveData(false)

    abstract val refreshFunction: (String?, Int?, Int?) -> Single<ResultModel<CommonListPageModel<T>>>

    abstract val searchFunction: ((String?, Int, Int) -> Single<ResultModel<CommonListPageModel<T>>>)

    //刷新列表
    fun refreshList(classify: String?) {
        refreshFunction.invoke(classify, 1, 10)
            .switchThread()
            .catchApiError()
            .doOnSubscribe {
                isRefreshing.postValue(true)
            }.doOnSuccess { filmList ->
                currentPageNum = 1
                commonListPageModelLiveData.postValue(filmList?.data)
                commonListLiveData.postValue(commonListPageModelLiveData.value?.dataList)
            }.doFinally {
                isRefreshing.postValue(false)
            }.bindLife()
    }

    //搜索列表
    fun search(classify: String?) {
        searchFunction.invoke(classify, 1, 10)
            .switchThread()
            .catchApiError()
            .doOnSubscribe {
                isRefreshing.postValue(true)
            }.doOnSuccess { filmList ->
                currentPageNum = 1
                commonListPageModelLiveData.postValue(filmList?.data)
                commonListLiveData.postValue(commonListPageModelLiveData.value?.dataList)
            }.doFinally {
                isRefreshing.postValue(false)
            }.bindLife()
    }

    //加载更多
    fun loadMoreShopList(classify: String?) {
        val targetPageNum = currentPageNum + 1

        refreshFunction.invoke(classify, targetPageNum, 10)
            .switchThread()
            .catchApiError()
            .doOnSubscribe {
                isLoadingMore.postValue(true)
            }.doOnSuccess { commonListPageModel ->
                commonListPageModelLiveData.postValue(commonListPageModel?.data)
                val newList = commonListLiveData.value?.toMutableList()
                commonListPageModel?.data?.dataList?.let { newList?.addAll(it) }
                commonListLiveData.postValue(newList)
                currentPageNum = commonListPageModel?.data?.pageNum ?: 1
            }.doFinally {
                isLoadingMore.postValue(false)
            }.bindLife()
    }
}