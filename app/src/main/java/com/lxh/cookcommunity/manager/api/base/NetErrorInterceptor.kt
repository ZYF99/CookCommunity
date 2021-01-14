package com.lxh.cookcommunity.manager.api.base

import com.lxh.cookcommunity.model.base.ApiException
import com.lxh.cookcommunity.util.fromJson
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.nio.charset.Charset

class NetErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code in 401..503)
            throw ServerError(
                response.code,
                response.body?.string() ?: ""
            )
        val responseBody = response.body
        val charset = responseBody?.contentType()?.charset(Charset.forName("UTF-8"))
            ?: Charset.forName("UTF-8")
        val str = responseBody?.source()?.buffer?.clone()?.readString(charset)

        val jsonObject = JSONObject(str)
        val meta = jsonObject.getJSONObject("meta")
        val code = meta.getInt("code")
        val msg = meta.getString("msg")

        when (code) {
            in 1002..2000 -> throw ApiException(
                status = code,
                msg = msg
            )
        }
        return response
    }

}

data class ServerError(val code: Int, val msg: String) : RuntimeException()