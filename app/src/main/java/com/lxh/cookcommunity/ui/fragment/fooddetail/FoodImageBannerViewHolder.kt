package com.lxh.cookcommunity.ui.fragment.fooddetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemFoodBannerBinding
import com.lxh.cookcommunity.databinding.ItemImageBannerBinding
import com.lxh.cookcommunity.model.api.home.Food
import com.zhouwei.mzbanner.holder.MZViewHolder

class FoodImageBannerViewHolder : MZViewHolder<String> {

    private lateinit var photographyBinding: ItemImageBannerBinding

    override fun createView(context: Context): View {
        photographyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_image_banner,
            null,
            false
        )

        return photographyBinding.root
    }

    override fun onBind(p0: Context?, p1: Int, data: String?) {
        // 数据绑定
        photographyBinding.url = data
    }
}
