package com.lxh.cookcommunity.model.api.baiduidentify


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class BaiduIdentifyToken(
	@Json(name = "refresh_token")
	val refreshToken: String,
	@Json(name = "expires_in")
	val expiresIn: Int,
	@Json(name = "session_key")
	val sessionKey: String,
	@Json(name = "access_token")
	val accessToken: String,
	@Json(name = "scope")
	val scope: String,
	@Json(name = "session_secret")
	val sessionSecret: String
)