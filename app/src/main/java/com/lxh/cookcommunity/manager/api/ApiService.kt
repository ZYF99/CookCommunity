package com.lxh.cookcommunity.manager.api

import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.login.LoginRequestModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    /*登录*/
    @POST("user/login")
    fun login(
        @Body loginRequestModel: LoginRequestModel
    ): Single<ResponseBody>

    /*刷新通用列表*/
    @GET("get")
    fun refreshCommonList(
        @Query("name") name: String?,
        @Query("id") id: Int?,
        @Query("size") size: Int?
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

    /*刷新通用列表*/
    @GET("get")
    fun searchCommonList(
        @Query("name") name: String?,
        @Query("id") id: Int?,
        @Query("size") size: Int?
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

}