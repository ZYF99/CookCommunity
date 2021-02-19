package com.lxh.cookcommunity.ui.fragment.home

import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentHomeBinding
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.camerasearch.jumpToCameraSearch
import com.lxh.cookcommunity.ui.fragment.classify.jumpToClassify
import com.lxh.cookcommunity.ui.fragment.cookpersonal.jumpToChefPersonal
import com.lxh.cookcommunity.ui.fragment.fooddetail.jumpToFoodDetail
import com.lxh.cookcommunity.ui.fragment.goodsdetail.jumpToGoodsDetail
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
            val clickBannerItem = viewModel.bannerListMutableLiveData.value?.get(position)
            when (clickBannerItem?.type) {
                "shop" -> {
                    viewModel.fetchGoodDetail(clickBannerItem.jump?.toLongOrNull()){
                        //跳转到商品详情界面
                        context?.jumpToGoodsDetail(it)
                    }
                }
                "food" -> {
                    //跳转到菜品详情界面
                    viewModel.fetchFoodDetail(clickBannerItem.jump?.toLongOrNull()){
                        //跳转到商品详情界面
                        context?.jumpToFoodDetail(it)
                    }
                }
                "chef" -> {
                    viewModel.fetchChefDetail(clickBannerItem.jump?.toLongOrNull()){
                        //跳转到厨师个人界面
                        context?.jumpToChefPersonal(it)
                    }
                }
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

        viewModel.isRefreshingMutableLiveData.observeNonNull {
            binding.refreshLayout.isRefreshing = it
        }

        viewModel.foodListMutableLiveData.observeNonNull {
            (binding.rvFood.adapter as FoodRecyclerAdapter).replaceData(it)
        }

        viewModel.bannerListMutableLiveData.observeNonNull {
            if (it.isNotEmpty())
                binding.banner.setPages(it.toList()) { FoodBannerViewHolder() }
        }

    }

}