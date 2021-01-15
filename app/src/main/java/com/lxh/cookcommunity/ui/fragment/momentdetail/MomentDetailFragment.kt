package com.lxh.cookcommunity.ui.fragment.momentdetail

import android.content.Context
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentMomentDetailBinding
import com.lxh.cookcommunity.manager.api.base.globalMoshi
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.moment.MomentsListGridImageAdapter
import com.lxh.cookcommunity.ui.fragment.moment.getMomentsPictureLayoutManager
import com.lxh.cookcommunity.ui.fragment.personpersonal.jumpToPersonPersonal
import com.lxh.cookcommunity.util.*

const val KEY_MOMENT_DETAIL = "key_moment_detail"

class MomentDetailFragment : BaseFragment<FragmentMomentDetailBinding, MomentDetailViewModel>(
    MomentDetailViewModel::class.java, layoutRes = R.layout.fragment_moment_detail
) {

    //发送信息的index
    private var replyCommentId: Long? = null

    //弹出键盘及输入框
    private fun showInputDialog() {
        //显示输入框，隐藏下方按钮
        binding.cardBtn.visibility = View.GONE
        binding.linComment.root.visibility = View.VISIBLE
        //弹出键盘
        requireActivity().openSoftKeyBoard(binding.linComment.editComment)

    }

    //隐藏键盘及输入框
    private fun hideInputDialog() {
        //显示输入框，隐藏下方按钮
        binding.cardBtn.visibility = View.VISIBLE
        binding.linComment.root.visibility = View.GONE
        //隐藏键盘
        requireActivity().hideSoftKeyBoard(binding.linComment.editComment)
    }

    override fun initView() {

        viewModel.momentMutableLiveData.observeNonNull {

            //照片列表
            binding.rvImg.run {
                layoutManager = getMomentsPictureLayoutManager(context, it.images?.size ?: 1)
                adapter = MomentsListGridImageAdapter(it.images ?: emptyList()) { position ->
                    showGallery(
                        context,
                        it.images,
                        position
                    )
                }
            }

            (binding.rvImg.adapter as MomentsListGridImageAdapter).replaceData(it.images)

            (binding.commentRec.adapter as CommentListAdapter).replaceData(
                it.realCommentList ?: emptyList()
            )

            binding.ivAvatar.setOnClickListener {
                context?.jumpToPersonPersonal(viewModel.momentMutableLiveData.value?.profile)
            }

        }


        //整体滑动监听
        binding.nestedscroll.setOnScrollChangeListener { _: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
            //滚动时隐藏输入
            hideInputDialog()
        }

        //评论列表
        binding.commentRec.run {
            val commentList = viewModel.momentMutableLiveData.value?.realCommentList ?: emptyList()
            layoutManager = LinearLayoutManager(context)
            adapter = CommentListAdapter(
                { comment ->
                    showInputDialog()
                    replyCommentId = comment.cid
                },
                commentList
            )
        }

        //收起评论按钮
        binding.linComment.tvClose.setOnClickListener {

            hideInputDialog()
        }

        //评论按钮
        binding.btnComment.setOnClickListener {
            showInputDialog()
        }

        //点赞按钮
        binding.btnLike.setOnClickListener {
            viewModel.like()
        }

        //发送按钮 回复原文
        binding.linComment.btnSend.setOnClickListener {
            if (viewModel.inputCommentMutableLiveData.value?.isNotEmpty() == true) {
                viewModel.inputCommentMutableLiveData.postValue("")
                viewModel.pushComment()
                requireActivity().hideSoftKeyBoard()
            } else showToast("回复不能为空")
        }
    }

    override fun initData() {
        viewModel.momentMutableLiveData.value =
            globalMoshi.fromJson(
                arguments?.getString(
                    KEY_MOMENT_DETAIL
                )
            )

        viewModel.fetchMomentDetail()

    }
}

fun Context.jumpToMomentDetail(moment: MomentContent) {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.MomentDetail,
        bundle = bundleOf(Pair(KEY_MOMENT_DETAIL, moment.toJson()))
    )
    this.startActivity(intent)
}