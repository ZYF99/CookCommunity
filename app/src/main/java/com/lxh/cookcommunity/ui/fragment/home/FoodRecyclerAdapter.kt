package com.lxh.cookcommunity.ui.fragment.home

import androidx.lifecycle.LifecycleOwner
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemFoodBinding
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter

class FoodRecyclerAdapter(
    lifecycleOwner: LifecycleOwner?,
    val onClick: (Food) -> Unit
) : CommonListRecyclerAdapter<Food, ItemFoodBinding>(
    lifecycleOwner,
    R.layout.item_food,
    true,
    emptyList()
) {
    override fun bindData(binding: ItemFoodBinding, position: Int) {
        val food = baseList[position]
        binding.food = food
        binding.root.setOnClickListener { onClick(food) }
    }
}