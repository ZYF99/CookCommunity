package com.lxh.cookcommunity.ui.fragment.moment

import androidx.activity.ComponentActivity
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemMomentBinding
import com.lxh.cookcommunity.model.api.moments.MomentContent
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListFragment
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.momentdetail.jumpToMomentDetail
import com.lxh.cookcommunity.ui.fragment.personpersonal.jumpToPersonPersonal
import com.lxh.cookcommunity.ui.fragment.releasemoment.hasReleaseMoment

class MomentFragment : CommonListFragment<MomentContent, MomentViewModel, ItemMomentBinding>(
    classify = "moment",
    itemLayoutRes = R.layout.item_moment,
    viewModelClazz = MomentViewModel::class.java,
    canRefresh = true,
    canSearch = false
) {

    override fun getCustomRecyclerAdapter(): CommonListRecyclerAdapter<MomentContent, ItemMomentBinding>? {
        return MomentRecyclerAdapter(
            activity as ComponentActivity,
            this,
            onCellClick = {
                context?.jumpToMomentDetail(it)
            },
            onHeaderClick = {
                context?.jumpToPersonPersonal()
            },
            onLikeClick = { momentContent, i ->

            },
            onCommitClick = {
                context?.jumpToMomentDetail(it)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        if(binding.rvList.adapter!=null && hasReleaseMoment){
            viewModel.refreshList()
            hasReleaseMoment = false
        }
    }

    override val onCellClick: ((MomentContent) -> Unit)?
        get() = null
}