package com.lxh.cookcommunity.ui.fragment.home

import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentHomeBinding
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.camerasearch.jumpToCameraSearch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    HomeViewModel::class.java, layoutRes = R.layout.fragment_home
) {

    override fun initView() {

        //菜单列表
        binding.rvFood.apply {
            adapter = FoodRecyclerAdapter {

            }
        }

        //轮播图
        binding.banner.setBannerPageClickListener { _, position ->
            if (viewModel.bannerListMutableLiveData.value?.isNotEmpty() == true) {

            }
        }

        //扫描
        binding.tvScan.setOnClickListener {
            context?.jumpToCameraSearch()
        }


    }

    override fun initData() {
        viewModel.fetchBannerList()
        viewModel.fetchFoodList()
    }

    override fun initDataObServer() {

        viewModel.foodListMutableLiveData.observeNonNull {
            (binding.rvFood.adapter as FoodRecyclerAdapter).replaceData(it)
        }

        viewModel.bannerListMutableLiveData.observeNonNull {
            if (it.isNotEmpty())
                binding.banner.setPages(it.toList()) { FoodBannerViewHolder() }
        }

    }

}