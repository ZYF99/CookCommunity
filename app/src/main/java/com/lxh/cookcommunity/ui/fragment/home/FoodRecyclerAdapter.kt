package com.lxh.cookcommunity.ui.fragment.home

import androidx.recyclerview.widget.RecyclerView
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemFoodBinding
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.adapter.BaseRecyclerAdapter

class FoodRecyclerAdapter(
    onClick: (Food) -> Unit
) : BaseRecyclerAdapter<Food, ItemFoodBinding>(
    R.layout.item_food,
    onClick
) {

    override fun bindData(binding: ItemFoodBinding, position: Int) {
        val food = baseList[position]
        binding.food = food
    }
}