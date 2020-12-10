package com.lxh.cookcommunity.ui.fragment.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import com.lxh.cookcommunity.model.api.login.LoginRequestModel
import com.lxh.cookcommunity.ui.base.BaseViewModel
import com.lxh.cookcommunity.ui.base.Event
import com.lxh.cookcommunity.ui.base.LiveEvent
import org.kodein.di.generic.instance

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val apiService by instance<ApiService>()
    val userAccount = MutableLiveData(SharedPrefModel.userAccount)
    val password = MutableLiveData(SharedPrefModel.password)
    val loginEvent = LiveEvent<Event<Boolean>>()

    fun checkAndLogin() {
        apiService.login(
            LoginRequestModel(
                userAccount.value ?: "",
                password.value ?: ""
            )
        ).doOnApiSuccess {
            SharedPrefModel.hasLogin = true
            SharedPrefModel.userAccount = userAccount.value ?: ""
            SharedPrefModel.password = password.value ?: ""
            //SharedPrefModel.nowUserId = it.data?.id ?: 0
            loginEvent.postValue(Event(true))
        }.bindLife()
    }

}

