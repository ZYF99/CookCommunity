package com.lxh.cookcommunity.manager.api.base

import com.lxh.cookcommunity.model.base.ApiException
import com.lxh.cookcommunity.model.base.ServerException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class NetErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val responseBody = response.body
        val contentLength = responseBody!!.contentLength()
        val source = responseBody.source()

        try {
            source.request(Long.MAX_VALUE)
        } catch (e: IOException) {
            return response
        }

        val charset = responseBody.contentType()?.charset(Charset.forName("UTF-8"))
            ?: Charset.forName("UTF-8")

        if (contentLength != 0L) {
            val responseStr = source.buffer.clone().readString(charset)
            try {
                val jsonObject = JSONObject(responseStr)
                val statusCode = jsonObject.getInt("status")
                val errorMsg = jsonObject.getString("msg")
                if (statusCode != 200) {
                    throw ApiException(statusCode, errorMsg)
                }
            } catch (e: JSONException) {
                val jsonObject = JSONObject(responseStr)
                //解析不成 status:"",msg:"",data:"" 说明是服务器报错
                val statusCode = jsonObject.getInt("status")
                val errorMsg = jsonObject.getString("message")
                throw ServerException(statusCode,errorMsg)
            }
        }
        return response
    }
}
