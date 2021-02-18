package com.lxh.cookcommunity.manager.api

import com.lxh.cookcommunity.manager.api.base.ResultModel
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import com.lxh.cookcommunity.model.api.*
import com.lxh.cookcommunity.model.api.commonlist.CommonListPageModel
import com.lxh.cookcommunity.model.api.editinfo.EditInfoRequestModel
import com.lxh.cookcommunity.model.api.goods.Goods
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.model.api.login.LoginRequestModel
import com.lxh.cookcommunity.model.api.login.RegisterRequestModel
import com.lxh.cookcommunity.model.api.moments.CommentMomentRequestModel
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.model.api.moments.ReleaseMomentRequestModel
import com.lxh.cookcommunity.model.api.studyvideo.StudyVideoModel
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

    /*刷新首页食物列表*/
    @GET("api/dishes/recommend")
    fun refreshRecommendDished(
        @Query("pageNo") pageNo: Int? = 1,
        @Query("pageSize") pageSize: Int? = 1000
    ): Single<ResultModel<CommonListPageModel<Food>>>

    /*刷新食物列表 无用！*/
    @GET("get")
    fun refreshFoodList(
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<Food>>>

    /*分类筛选*/
    @GET("api/dishes/filter")
    fun filterFood(
        @Query("pageNo") pageNo: Int?=1,
        @Query("pageSize") pageSize: Int? = 1000,
        @Query("group") group: String? = null,
        @Query("type") type: String? = null,
        @Query("time") time: String? = null
    ): Single<ResultModel<CommonListPageModel<Food>>>

    /*搜索食物列表*/
    @GET("api/dishes/search")
    fun searchFoodList(
        @Query("dishName") dishName: String?,
        @Query("pageNo") pageNo: Int? = 1,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<Food>>>

    /*拉取食物教程*/
    @GET("api/dishes/course")
    fun fetchFoodStudyVideo(
        @Query("courseId") courseId: Long?
    ): Single<ResultModel<StudyVideoModel>>

    /*收藏 食物\人\厨师 */
    @POST("api/collect")
    fun collect(
        @Body collectRequestModel: CollectRequestModel? = null
    ): Single<ResponseBody>

    /*查询是否已收藏 食物\人\厨师*/
    @POST("api/collect/check")
    fun checkCollect(
        @Body collectFoodRequestModel: CollectRequestModel? = null
    ): Single<ResultModel<CheckCollectResultModel>>

    /*拉取厨子粉丝数*/
    @GET("api/collect/chef")
    fun fetchChefFans(
        @Query("chefId")chefId:Long?=null
    ): Single<ResultModel<ChefFansModel>>

    /*拉取自己的粉丝数*/
    @GET("api/collect/count?type=FOLLOW")
    fun fetchMineFansNum(): Single<ResultModel<UserFansModel>>

    /*拉取我关注的人数*/
    @GET("api/collect/count?type=ATTENTION")
    fun fetchMyAttentionNum(): Single<ResultModel<UserFansModel>>

    /*拉取某用户的粉丝数 TODO*/
    @GET("todo")
    fun fetchUserFansNum(): Single<ResultModel<UserFansModel>>

    /***********************************************动态********************************************/

    /*刷新动态列表*/
    @GET("api/moments")
    fun refreshMomentList(
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

    /*根据uid刷新动态列表*/
    @GET("api/moments/poster")
    fun refreshMomentList(
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10,
        @Query("uid") uid: Long? = null
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

    /*发布动态*/
    @POST("api/moments/publish")
    fun releaseMoment(
        @Body releaseMomentRequestModel: ReleaseMomentRequestModel
    ): Single<ResponseBody>

    /*NONE 搜索动态列表*/
    @GET("get")
    fun searchMomentList(
        @Query("name") name: String?,
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int? = 10
    ): Single<ResultModel<CommonListPageModel<MomentContent>>>

    /*评论/点赞*/
    @POST("api/moments/comment")
    fun pushCommentOrLike(
        @Body commentMomentRequestModel: CommentMomentRequestModel
    ): Single<ResultModel<MomentContent>>

    /*拉取单个动态*/
    @GET("api/moments/one")
    fun fetchMomentDetail(
        @Query("mid") mid: Long? = null
    ): Single<ResultModel<MomentContent>>

    /**********************************************商品********************************************/

    /*刷新商品列表*/
    @GET("api/goods")
    fun refreshGoodsList(
        @Query("type") type:String?,
        @Query("pageNo") pageNo: Int?=null,
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