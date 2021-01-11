package com.lxh.cookcommunity.ui.fragment.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import com.lxh.cookcommunity.model.api.login.LoginRequestModel
import com.lxh.cookcommunity.model.api.login.RegisterRequestModel
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.lxh.cookcommunity.ui.base.Event
import com.lxh.cookcommunity.ui.base.LiveEvent
import org.kodein.di.generic.instance

class RegisterViewModel(application: Application) : BaseViewModel(application) {

    val apiService by instance<ApiService>()
    val nameMutableLiveData = MutableLiveData<String>()
    val accountMutableLiveData = MutableLiveData<String>()
    val passwordMutableLiveData = MutableLiveData<String>()
    val loginEvent = LiveEvent<Event<Boolean>>()

    //注册
    fun register() {
        apiService.register(
            RegisterRequestModel(
                name = nameMutableLiveData.value,
                account = accountMutableLiveData.value,
                password = passwordMutableLiveData.value
            )
        ).flatMap {
            apiService.login(
                LoginRequestModel(
                    account = it.data?.account,
                    password = passwordMutableLiveData.value
                )
            )
        }.doOnApiSuccess {
            SharedPrefModel.hasLogin = true
            SharedPrefModel.userAccount = accountMutableLiveData.value ?: ""
            SharedPrefModel.password = passwordMutableLiveData.value ?: ""
            SharedPrefModel.nowUserId = it.data?.uid?:0
            SharedPrefModel.setUserModel {
                token = it.data?.token
            }
            loginEvent.postValue(Event(true))
        }
    }


}