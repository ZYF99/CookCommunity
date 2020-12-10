package com.lxh.cookcommunity.ui.fragment.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance

class RegisterViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val accountMutableLiveData = MutableLiveData<String>()
    val passwordMutableLiveData = MutableLiveData<String>()

    //注册
    fun register() {

    }


}