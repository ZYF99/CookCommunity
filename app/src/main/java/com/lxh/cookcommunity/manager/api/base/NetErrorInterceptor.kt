package com.lxh.cookcommunity.manager.api.base

import okhttp3.Interceptor
import okhttp3.Response

class NetErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code in 400..503)
            throw ServerError(
                response.code,
                response.body?.string() ?: ""
            )
        return response
    }

}

data class ServerError(val code: Int, val msg: String) : RuntimeException()