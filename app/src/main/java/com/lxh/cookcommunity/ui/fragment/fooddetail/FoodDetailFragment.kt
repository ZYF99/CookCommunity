package com.lxh.cookcommunity.ui.fragment.fooddetail

import android.content.Context
import android.view.View
import androidx.core.os.bundleOf
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentFoodDetailBinding
import com.lxh.cookcommunity.manager.api.base.globalMoshi
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.cookpersonal.jumpToChefPersonal
import com.lxh.cookcommunity.ui.fragment.studyvideo.jumpToStudyVideo
import com.lxh.cookcommunity.util.fromJson
import com.lxh.cookcommunity.util.showToast
import com.lxh.cookcommunity.util.toJson

const val KEY_FOOD_DETAIL = "key_food_detail"

class FoodDetailFragment : BaseFragment<FragmentFoodDetailBinding, FoodDetailViewModel>(
    FoodDetailViewModel::class.java, layoutRes = R.layout.fragment_food_detail
) {

    override fun initView() {
        viewModel.foodMutableLiveData.value = globalMoshi.fromJson(arguments?.getString(KEY_FOOD_DETAIL))

        binding.tvStartCook.setOnClickListener {
            context?.jumpToStudyVideo(viewModel.foodMutableLiveData.value)
        }

        binding.tvPersonal.setOnClickListener {
            context?.jumpToChefPersonal(viewModel.foodMutableLiveData.value?.chefProfile)
        }

        binding.tvCollection.setOnClickListener {
            viewModel.collectFood{
                binding.tvCollection.visibility = View.GONE
                showToast("收藏成功")
            }
        }

    }

    override fun initData() {
        viewModel.checkCollect {
            binding.tvCollection.visibility = if(it)View.GONE else View.VISIBLE
        }
        viewModel.bannerListMutableLiveData.postValue(listOf(viewModel.foodMutableLiveData.value?.image?:""))
    }

    override fun initDataObServer() {
        viewModel.bannerListMutableLiveData.observeNonNull {
            if (it.isNotEmpty())
                binding.bannerFoodPic.setPages(it.toList()) { FoodImageBannerViewHolder() }
        }

        viewModel.foodMutableLiveData.observeNonNull {
            binding.tvCookName.text = "厨师：${it.chefProfile?.name}"
        }

    }
}

    fun Context.jumpToFoodDetail(food: Food?) {
        val intent = ContentActivity.createIntent(
            context = this,
            des = ContentActivity.Destination.FoodDetail,
            bundle = bundleOf(Pair(KEY_FOOD_DETAIL, food.toJson()))
        )
        this.startActivity(intent)
    }