package com.lxh.cookcommunity.model.api

import com.squareup.moshi.Json

data class SimpleProfileResp(
	@Json(name = "uid")
	val openId: String? = null,
	val name: String? = null,
	val avatar: String? = null,
	val signature: String? = null,
	val country: String? = null,
	val province: String? = null,
	val city: String? = null,
	val age: Int? = null
){
	companion object{
		fun getDefault() = SimpleProfileResp(
			"fdmbhngsdbjsk",
			"dsfhg",
			"https://profile.csdnimg.cn/3/D/7/3_agsdfgdfhdf",
			"似懂非懂时间，返回;ewf",
			"中国",
			"四川",
			"成都",
			20
		)
	}
}