package com.lxh.cookcommunity.ui.fragment.personal

import androidx.activity.ComponentActivity
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentPersonalBinding
import com.lxh.cookcommunity.model.api.moments.MomentRecyclerAdapter
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.editinfo.jumpToEditInfo
import com.lxh.cookcommunity.ui.fragment.mark.jumpToMark
import com.lxh.cookcommunity.ui.fragment.momentdetail.jumpToMomentDetail

class PersonalFragment : BaseFragment<FragmentPersonalBinding, PersonalViewModel>(
    PersonalViewModel::class.java, layoutRes = R.layout.fragment_personal
) {

    override fun initView() {

        binding.rvMyMoment.adapter = MomentRecyclerAdapter(
            activity as ComponentActivity,
            this,
            onCellClick = {
                context?.jumpToMomentDetail(it)
            },
            onHeaderClick = {

            },
            onLikeClick = { momentContent, i ->

            },
            onCommitClick = {
                context?.jumpToMomentDetail(it)
            }
        )

        binding.btnSetting.setOnClickListener {
            context?.jumpToEditInfo()
        }

        binding.tvShop.setOnClickListener {
            context?.jumpToMark()
        }

    }

    override fun initDataObServer() {
        viewModel.myRecentMomentMutableLiveDataList.observeNonNull {
            (binding.rvMyMoment.adapter as MomentRecyclerAdapter).replaceData(it)
        }
    }

    override fun initData() {
        viewModel.fetchUserProfile()
        viewModel.fetchMyMoments()
    }
}