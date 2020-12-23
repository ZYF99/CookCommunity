package com.lxh.cookcommunity.ui.fragment.cookpersonal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.model.api.cook.Cook
import com.lxh.cookcommunity.ui.base.BaseViewModel


class CookPersonalViewModel(application: Application) : BaseViewModel(application) {

    //厨师个人信息
    val cookMutableLiveData = MutableLiveData<Cook>()


}