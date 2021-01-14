package com.lxh.cookcommunity.ui.fragment.mark

import android.content.Context
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentMarkBinding
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.adapter.CommonPagerAdapter
import com.lxh.cookcommunity.ui.base.BaseFragment

class MarkFragment : BaseFragment<FragmentMarkBinding, MarkViewModel>(
    MarkViewModel::class.java, layoutRes = R.layout.fragment_mark
) {


    override fun initView() {
        val list = listOf(
            MarkListFragment("eat"),
            MarkListFragment("tableware")
        )

        binding.vpMark.adapter = CommonPagerAdapter(
            childFragmentManager,
            list,
            listOf("食品", "餐具")
        )

        binding.tlVp.setupWithViewPager(binding.vpMark)

    }

    override fun initData() {

    }
}

fun Context.jumpToMark() {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.Mark
    )
    this.startActivity(intent)
}