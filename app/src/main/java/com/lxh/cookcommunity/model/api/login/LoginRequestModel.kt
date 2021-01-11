package com.lxh.cookcommunity.model.api.login

data class LoginRequestModel(
    val account: String? = null,
    val password: String? = null
)