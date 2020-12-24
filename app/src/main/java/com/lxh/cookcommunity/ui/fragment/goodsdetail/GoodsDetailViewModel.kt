package com.lxh.cookcommunity.ui.fragment.goodsdetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.model.api.goods.Goods
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance

class GoodsDetailViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val goodsMutableLiveData = MutableLiveData<Goods>()

}