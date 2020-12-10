package com.lxh.cookcommunity.model.api.moments

import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemMomentGridImageBinding
import com.lxh.cookcommunity.ui.adapter.BaseRecyclerAdapter

class MomentsListGridImageAdapter(
    val list: List<String>,
    val onCellClick: (Int) -> Unit
) : BaseRecyclerAdapter<String, ItemMomentGridImageBinding>(
    layoutRes = R.layout.item_moment_grid_image,
    onCellClick = {

    },
    list = list
) {
    override fun bindData(binding: ItemMomentGridImageBinding, position: Int) {
        binding.imgUrl = baseList[position]
        binding.root.setOnClickListener {
            onCellClick(position)
        }
    }
}



	







