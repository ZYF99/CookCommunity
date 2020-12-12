package com.lxh.cookcommunity.model.api.baiduidentify

import com.squareup.moshi.Json

data class IdentifyResult(
	@Json(name = "log_id")
	val logId:Long,
	@Json(name = "result_num")
	val resultNum:Int,
	@Json(name = "result")
	val result:MutableList<Thing>
)

data class Thing(
	val hasCalorie:Boolean? = false,
	val name:String? = null,
	val probability:Float? = null
)
