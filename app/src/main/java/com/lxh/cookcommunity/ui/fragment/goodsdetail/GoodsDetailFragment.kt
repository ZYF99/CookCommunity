package com.lxh.cookcommunity.ui.fragment.goodsdetail

import android.content.Context
import androidx.core.os.bundleOf
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentGoodsDetailBinding
import com.lxh.cookcommunity.manager.api.base.globalMoshi
import com.lxh.cookcommunity.model.api.goods.Goods
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.util.DialogUtil
import com.lxh.cookcommunity.util.fromJson
import com.lxh.cookcommunity.util.toJson

const val KEY_GOODS_DETAIL = "key_goods_detail"

class GoodsDetailFragment : BaseFragment<FragmentGoodsDetailBinding, GoodsDetailViewModel>(
    GoodsDetailViewModel::class.java, layoutRes = R.layout.fragment_goods_detail
) {

    override fun initView() {
        viewModel.goodsMutableLiveData.value =
            globalMoshi.fromJson(arguments?.getString(KEY_GOODS_DETAIL))
        binding.tvBuy.setOnClickListener {
            DialogUtil.showPurchaseDialog(
                requireContext(),
                viewModel.goodsMutableLiveData.value?.price ?: 0f
            )
        }
    }

    override fun initData() {

    }

    override fun initDataObServer() {

        viewModel.goodsMutableLiveData.observeNonNull {
            binding.bannerGoodsPic.setPages(it.images) { GoodsImageBannerViewHolder() }
        }

    }

}

fun Context.jumpToGoodsDetail(goods: Goods) {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.GoodsDetail,
        bundle = bundleOf(Pair(KEY_GOODS_DETAIL, goods.toJson()))
    )
    this.startActivity(intent)
}

