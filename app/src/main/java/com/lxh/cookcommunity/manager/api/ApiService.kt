package com.lxh.cookcommunity.manager.api

import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.home.Food
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

    /*刷新动态列表*/
    @GET("get")
    fun refreshMomentList(
        @Query("name") name: String?,
        @Query("id") id: Int?,
        @Query("size") size: Int?
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

    /*搜索动态列表*/
    @GET("get")
    fun searchMomentList(
        @Query("name") name: String?,
        @Query("id") id: Int?,
        @Query("size") size: Int?
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

    /*刷新食物列表*/
    @GET("get")
    fun refreshFoodList(
        @Query("name") name: String?,
        @Query("id") id: Int?,
        @Query("size") size: Int?
    ): Single<ResultModel<CommonListPageModel<Food>>>

    /*搜索食物列表*/
    @GET("get")
    fun searchFoodList(
        @Query("name") name: String?,
        @Query("id") id: Int?,
        @Query("size") size: Int?
    ): Single<ResultModel<CommonListPageModel<Food>>>

    /***********************************************动态********************************************/

    /*评论*/
     @GET("commentReply")
    fun pushCommentOrLike(
        @Query("id") id: Long?,
        @Query("type") type: String?,
        @Query("content") content: String?
    ): Single<ResponseBody>

}