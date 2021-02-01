package com.lxh.cookcommunity.ui.fragment.home

import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentHomeBinding
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.camerasearch.jumpToCameraSearch
import com.lxh.cookcommunity.ui.fragment.classify.jumpToClassify
import com.lxh.cookcommunity.ui.fragment.fooddetail.jumpToFoodDetail
import com.lxh.cookcommunity.ui.fragment.searchfood.jumpToSearchFood

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    HomeViewModel::class.java, layoutRes = R.layout.fragment_home
) {

    override fun initView() {

        //菜单列表
        binding.rvFood.apply {
            adapter = FoodRecyclerAdapter(this@HomeFragment) {
                context.jumpToFoodDetail(it)
            }
        }

        //轮播图
        binding.banner.setBannerPageClickListener { _, position ->
            if (viewModel.bannerListMutableLiveData.value?.isNotEmpty() == true) {

            }
        }

        //下拉刷新
        binding.refreshLayout.setOnRefreshListener {
            initData()
        }

        //搜索
        binding.tvSearch.setOnClickListener {
            context?.jumpToSearchFood()
        }

        //分类
        binding.tvClassify.setOnClickListener {
            context?.jumpToClassify()
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