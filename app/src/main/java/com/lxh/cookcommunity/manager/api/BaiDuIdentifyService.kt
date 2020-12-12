package com.lxh.cookcommunity.manager.api

import com.lxh.cookcommunity.BuildConfig
import com.lxh.cookcommunity.model.api.baiduidentify.BaiduIdentifyToken
import com.lxh.cookcommunity.model.api.baiduidentify.IdentifyResult
import io.reactivex.Single
import retrofit2.http.*

/**
 * @author Zhangyf
 * @version 1.0
 * @date 2019/10/20 14：12
 */

//百度识别API
interface BaiDuIdentifyService {
	
	//获取Token
	@POST("oauth/2.0/token?grant_type=client_credentials&client_id=${BuildConfig.BAIDU_API_KEY2}&client_secret=${BuildConfig.BAIDU_SECRET_KEY2}")
	fun getToken(): Single<BaiduIdentifyToken>
	
	//获取图片物体名称
	@FormUrlEncoded
	@POST("rest/2.0/image-classify/v2/dish")
	fun getIdentifyThing(
		@Query("access_token")access_token:String,
		@Field("image")imageStr:String
	): Single<IdentifyResult>
	
}