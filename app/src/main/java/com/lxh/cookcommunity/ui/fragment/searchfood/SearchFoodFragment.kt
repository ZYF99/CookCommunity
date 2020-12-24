package com.lxh.cookcommunity.ui.fragment.searchfood

import android.content.Context
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemFoodBinding
import com.lxh.cookcommunity.model.api.home.Food
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListFragment
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.fooddetail.jumpToFoodDetail
import com.lxh.cookcommunity.ui.fragment.home.FoodRecyclerAdapter

const val KEY_SEARCH_FOOD = "key_search_food"

class SearchFoodFragment : CommonListFragment<Food, SearchFoodViewModel, ItemFoodBinding>(
    classify = "moment",
    itemLayoutRes = R.layout.item_food,
    viewModelClazz = SearchFoodViewModel::class.java,
    canRefresh = true,
    canSearch = true
) {

    override fun initView() {
        super.initView()

    }

    override fun initData() {
        super.initData()

    }

    override fun getCustomRecyclerAdapter(): CommonListRecyclerAdapter<Food, ItemFoodBinding>? {
        return FoodRecyclerAdapter(this) {
            context?.jumpToFoodDetail(it)
        }
    }

    override val onCellClick: ((Food) -> Unit)?
        get() = null

}

fun Context.jumpToSearchFood() {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.SearchFood
    )
    this.startActivity(intent)
}
