package com.lxh.cookcommunity.ui.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemFoodBannerBinding
import com.lxh.cookcommunity.model.api.home.Food
import com.zhouwei.mzbanner.holder.MZViewHolder

class FoodBannerViewHolder : MZViewHolder<Food> {

    private lateinit var photographyBinding: ItemFoodBannerBinding

    override fun createView(context: Context): View {
        photographyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_food_banner,
            null,
            false
        )

        return photographyBinding.root
    }

    override fun onBind(p0: Context?, p1: Int, data: Food?) {
        // 数据绑定
        photographyBinding.food = data
    }
}
