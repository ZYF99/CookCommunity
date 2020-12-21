package com.lxh.cookcommunity.ui.fragment.fooddetail

import android.content.Context
import androidx.core.os.bundleOf
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentFoodDetailBinding
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.studyvideo.jumpToStudyVideo
import com.lxh.cookcommunity.util.toJson

const val KEY_FOOD_DETAIL = "key_food_detail"

class FoodDetailFragment : BaseFragment<FragmentFoodDetailBinding, FoodDetailViewModel>(
    FoodDetailViewModel::class.java, layoutRes = R.layout.fragment_food_detail
) {

    override fun initView() {
        binding.tvStartCook.setOnClickListener {
            context?.jumpToStudyVideo()
        }
    }

    override fun initData() {
        viewModel.fetchBannerList()
    }

    override fun initDataObServer() {
        viewModel.bannerListMutableLiveData.observeNonNull {
            if (it.isNotEmpty())
                binding.bannerFoodPic.setPages(it.toList()) { FoodImageBannerViewHolder() }
        }
    }

}

fun Context.jumpToFoodDetail(food: Food) {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.FoodDetail,
        bundle = bundleOf(Pair(KEY_FOOD_DETAIL, food.toJson()))
    )
    this.startActivity(intent)
}