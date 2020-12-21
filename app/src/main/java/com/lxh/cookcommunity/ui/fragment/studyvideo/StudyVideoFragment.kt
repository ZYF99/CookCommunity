package com.lxh.cookcommunity.ui.fragment.studyvideo

import android.content.Context
import com.bumptech.glide.Glide
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentStudyVideoBinding
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard


class StudyVideoFragment : BaseFragment<FragmentStudyVideoBinding, StudyVideoViewModel>(
    StudyVideoViewModel::class.java, layoutRes = R.layout.fragment_study_video
) {


    override fun initView() {
        binding.videoPlayer.apply {
            setUp(
                "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
                JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,
                "嫂子闭眼睛"
            )
            Glide.with(context).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
                .into(thumbImageView)
        }
    }


    override fun initData() {

    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }

}

fun Context.jumpToStudyVideo() {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.StudyVideo
    )
    this.startActivity(intent)
}