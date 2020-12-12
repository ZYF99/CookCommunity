package com.lxh.cookcommunity.ui.fragment.camerasearch

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lxh.cookcommunity.manager.api.BaiDuIdentifyService
import com.lxh.cookcommunity.ui.base.BaseViewModel
import org.kodein.di.generic.instance


class CameraSearchViewModel(application: Application) : BaseViewModel(application) {
    val thingString = MutableLiveData<String>()

    private val baiduIdentifyService by instance<BaiDuIdentifyService>()

    //识别图片物体
    fun identifyThingName(imgArrayStr: String) {
        baiduIdentifyService.getToken()
            .flatMap {
                baiduIdentifyService.getIdentifyThing(
                    it.accessToken,
                    imgArrayStr
                )
            }.doOnApiSuccess {
                it.result.sortByDescending { thing ->
                    thing.probability
                }
                thingString.postValue(it.result[0].name)
            }
    }


}