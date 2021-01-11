package com.lxh.cookcommunity.manager.api.base

import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            //.header("Connection", "close")
            .header("X-Token", SharedPrefModel.getUserModel().token?:"")
            .build()
        return chain.proceed(request)
    }

}