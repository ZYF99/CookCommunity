package com.lxh.cookcommunity.ui.fragment.mark

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemGoodsBinding
import com.lxh.cookcommunity.model.api.goods.Goods
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListFragment
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.goodsdetail.jumpToGoodsDetail

class MarkListFragment(val type: String) :
    CommonListFragment<Goods, MarkListViewModel, ItemGoodsBinding>(
        classify = type,
        itemLayoutRes = R.layout.item_goods,
        viewModelClazz = MarkListViewModel::class.java,
        canRefresh = true,
        canSearch = false
    ) {

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(requireContext(), 2)
    }

    override fun getCustomRecyclerAdapter(): CommonListRecyclerAdapter<Goods, ItemGoodsBinding>? {
        return super.getCustomRecyclerAdapter()
    }

    override fun initView() {
        super.initView()
        viewModel.type = type
    }

    override fun initData() {
        super.initData()

    }

    override val onCellClick: ((Goods) -> Unit)? = {
        context?.jumpToGoodsDetail(it)
    }

}