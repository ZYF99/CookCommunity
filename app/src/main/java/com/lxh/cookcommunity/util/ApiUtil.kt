package com.lxh.cookcommunity.util

import android.content.Context
import android.os.NetworkOnMainThreadException
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.manager.api.base.ServerError
import com.lxh.cookcommunity.model.base.ApiException
import com.lxh.cookcommunity.model.base.ServerException
import retrofit2.HttpException
import java.io.IOException

object ApiUtil {

    fun parseErrorMsg(context: Context, e: Throwable): String {
            return when (e) {
            is ApiException -> e.msg?:"API接口业务异常"
            is ServerError -> e.msg
            is HttpException -> context.getString(R.string.error_dialog_msg_http_error)
            is IOException -> context.getString(R.string.api_error_network_error_msg)
            is NetworkOnMainThreadException -> context.getString(R.string.api_error_network_error_msg)
            else -> context.getString(R.string.error_dialog_msg_error)
        }
    }

    /*private fun parseMessageWithApiMessage(e: ApiException): String {
        return when (e.msg) {
            "NO_LOGIN_USER" -> "登陆失败，请检查账号和密码。"
            else -> e.msg
        }
    }*/

}

