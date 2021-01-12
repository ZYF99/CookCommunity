package com.lxh.cookcommunity.manager.api

import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import com.lxh.cookcommunity.model.api.UploadFileResultModel
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.editinfo.EditInfoRequestModel
import com.lxh.cookcommunity.model.api.goods.Goods
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.model.api.login.LoginRequestModel
import com.lxh.cookcommunity.model.api.login.RegisterRequestModel
import com.lxh.cookcommunity.model.api.UserProfileModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    /*************************************************账户******************************************/
    /*登录*/
    @POST("api/account/login")
    fun login(
        @Body loginRequestModel: LoginRequestModel
    ): Single<ResultModel<UserProfileModel>>

    /*注册*/
    @POST("api/account/register")
    fun register(
        @Body registerRequestModel: RegisterRequestModel
    ): Single<ResultModel<UserProfileModel>>

    /*拉取个人信息*/
    @GET("api/account")
    fun fetchUserProfile(
        @Query("uid") uid: Long? = SharedPrefModel.nowUserId
    ): Single<ResultModel<UserProfileModel>>

    /*修改个人信息*/
    @PUT("api/account")
    fun editUserProfile(
        @Body editInfoRequestModel: EditInfoRequestModel
    ): Single<ResponseBody>

    /*************************************************食物******************************************/

    /*刷新食物列表*/
    @GET("get")
    fun refreshFoodList(
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<Food>>>

    /*搜索食物列表*/
    @GET("get")
    fun searchFoodList(
        @Query("name") name: String?,
        @Query("id") id: Int?,
        @Query("size") size: Int?
    ): Single<ResultModel<CommonListPageModel<Food>>>

    /***********************************************动态********************************************/

    /*刷新动态列表*/
    @GET("api/moments")
    fun refreshMomentList(
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

    /*搜索动态列表*/
    @GET("get")
    fun searchMomentList(
        @Query("name") name: String?,
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

    /*评论*/
    @GET("commentReply")
    fun pushCommentOrLike(
        @Query("id") id: Long?,
        @Query("type") type: String?,
        @Query("content") content: String?
    ): Single<ResponseBody>

    /**********************************************商品********************************************/

    /*刷新商品列表*/
    @GET("get")
    fun refreshGoodsList(
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<Goods>>>

    /*搜索商品列表*/
    @GET("get")
    fun searchGoodsList(
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<Goods>>>

    /**********************************************工具********************************************/

    /*上传图片*/
    @Multipart
    @POST("tools/upload")
    fun uploadFile(
        @Part file: MultipartBody.Part?
    ): Single<ResultModel<UploadFileResultModel>>
}