package com.lxh.cookcommunity.manager.api.base

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            //.header("Connection", "close")
            //.header("Authentication", SharedPrefModel.getUserModel().authentication?:"")
            .build()
        return chain.proceed(request)
    }

}