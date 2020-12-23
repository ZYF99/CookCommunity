package com.lxh.cookcommunity.ui.fragment.studyvideo

import android.content.Context
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentStudyVideoBinding
import com.lxh.cookcommunity.manager.api.base.globalMoshi
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.fooddetail.KEY_FOOD_DETAIL
import com.lxh.cookcommunity.util.fromJson
import com.lxh.cookcommunity.util.toJson
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

class StudyVideoFragment : BaseFragment<FragmentStudyVideoBinding, StudyVideoViewModel>(
    StudyVideoViewModel::class.java, layoutRes = R.layout.fragment_study_video
) {

    //整个菜的信息
    var food: Food? = null

    override fun initView() {

        //整个菜的所有信息，包含所有步骤
        val foodJson = arguments?.getString(KEY_FOOD_DETAIL)
        if (foodJson?.isNotEmpty() == true) food = globalMoshi.fromJson(foodJson)

        //当前页面序号置为0
        viewModel.currentPageMutableLiveData.value = 0

    }

    override fun initData() {

    }


    override fun initDataObServer() {
        viewModel.currentPageMutableLiveData.observeNonNull { currentPage ->

            binding.tvStep.text = "第${currentPage + 1}步"

            //当前页面的视频信息
            viewModel.foodVideoMutableLiveData.value = food?.foodVideoList?.get(currentPage)

            if (((food?.foodVideoList?.size ?: 0) - 1) == currentPage) {
                //是最后一页了
                binding.tvNextStep.apply {
                    text = "学习完成"
                    setOnClickListener {
                        activity?.finish()
                    }
                }
            } else {
                //不是最后一页
                binding.tvNextStep.apply {
                    text = "开始下一步"
                    setOnClickListener {
                        viewModel.currentPageMutableLiveData.value =
                            (viewModel.currentPageMutableLiveData.value ?: 0) + 1
                    }
                }
            }

        }

        viewModel.foodVideoMutableLiveData.observeNonNull {
            binding.videoPlayer.apply {
                setUp(
                    viewModel.foodVideoMutableLiveData.value?.videoUrl,
                    JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,
                    viewModel.foodVideoMutableLiveData.value?.name
                )
                Glide.with(context)
                    .load(viewModel.foodVideoMutableLiveData.value?.thumbnailUrl)
                    .into(thumbImageView)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }

}

fun Context.jumpToStudyVideo(food: Food?) {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.StudyVideo,
        bundle = bundleOf(
            Pair(KEY_FOOD_DETAIL, food.toJson())
        )
    )
    this.startActivity(intent)
}