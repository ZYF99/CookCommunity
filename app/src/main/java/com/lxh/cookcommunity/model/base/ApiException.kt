package com.lxh.cookcommunity.model.base

import java.io.IOException

data class ApiException(
    val status: Int?,
    val msg: String?
) : IOException()


data class ServerException(
    val status: Int?,
    val msg: String?
) : IOException()