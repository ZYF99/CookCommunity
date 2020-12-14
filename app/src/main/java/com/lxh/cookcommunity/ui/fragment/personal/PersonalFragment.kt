package com.lxh.cookcommunity.ui.fragment.personal

import androidx.activity.ComponentActivity
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentPersonalBinding
import com.lxh.cookcommunity.model.api.moments.MomentRecyclerAdapter
import com.lxh.cookcommunity.ui.base.BaseFragment

class PersonalFragment : BaseFragment<FragmentPersonalBinding, PersonalViewModel>(
    PersonalViewModel::class.java, layoutRes = R.layout.fragment_personal
) {

    override fun initView() {

        binding.rvMyMoment.adapter = MomentRecyclerAdapter(
            activity as ComponentActivity,
            this,
            onCellClick = {

            },
            onHeaderClick = {

            },
            onLikeClick = { momentContent, i ->

            },
            onTransClick = {

            }
        )

    }

    override fun initDataObServer() {
        viewModel.myRecentMomentMutableLiveDataList.observeNonNull {
            (binding.rvMyMoment.adapter as MomentRecyclerAdapter).replaceData(it)
        }
    }

    override fun initData() {
        viewModel.fetchMyMoments()
    }
}