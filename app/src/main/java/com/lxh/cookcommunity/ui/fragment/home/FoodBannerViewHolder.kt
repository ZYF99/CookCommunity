package com.lxh.cookcommunity.ui.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemFoodBannerBinding
import com.lxh.cookcommunity.model.api.home.BannerModel
import com.zhouwei.mzbanner.holder.MZViewHolder

class FoodBannerViewHolder : MZViewHolder<BannerModel> {

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

    override fun onBind(p0: Context?, p1: Int, data: BannerModel?) {
        // 数据绑定
        photographyBinding.foodBannerModel = data
    }
}
