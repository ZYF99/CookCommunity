package com.lxh.cookcommunity.ui.fragment.classify

import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemChooseClassifyBinding
import com.lxh.cookcommunity.ui.adapter.BaseRecyclerAdapter

class FoodClassifyChooseRecyclerAdapter(
    list: List<ChooseClassify>?,
    val onCellClick:(String?)->Unit
) : BaseRecyclerAdapter<ChooseClassify, ItemChooseClassifyBinding>(
    R.layout.item_choose_classify,
    null
) {

    var selectedClassify:String? = null

    init {
        baseList = list?: emptyList()
    }

    override fun bindData(binding: ItemChooseClassifyBinding, position: Int) {
        val classify = baseList[position]
        binding.tvName.text = classify.name
        binding.tvName.isSelected = selectedClassify == classify.name
        binding.root.setOnClickListener {
            if(selectedClassify != classify.name){
                selectedClassify = classify.name
                notifyDataSetChanged()
            }else{
                selectedClassify = null
                notifyDataSetChanged()
            }
            onCellClick(selectedClassify)
        }
    }

}

data class ChooseClassify(
    val id:String? = null,
    val name:String? = null,
    val isSelected:Boolean = false
)