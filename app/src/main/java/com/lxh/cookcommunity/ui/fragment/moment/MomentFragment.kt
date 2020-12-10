package com.lxh.cookcommunity.ui.fragment.moment

import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemMomentBinding
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.model.api.moments.MomentRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListFragment
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter

class MomentFragment : CommonListFragment<MomentContent, MomentViewModel, ItemMomentBinding>(
    classify = "moment",
    itemLayoutRes = R.layout.item_moment,
    viewModelClazz = MomentViewModel::class.java,
    canRefresh = true,
    canSearch = false
) {

    override fun getCustomRecyclerAdapter(): CommonListRecyclerAdapter<MomentContent, ItemMomentBinding>? {
        return MomentRecyclerAdapter(
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

    override fun initView() {
        super.initView()

    }


    override fun initData() {
        super.initData()

    }
}