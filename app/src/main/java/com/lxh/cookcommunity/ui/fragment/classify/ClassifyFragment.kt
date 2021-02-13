package com.lxh.cookcommunity.ui.fragment.classify

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentClassifyBinding
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.fooddetail.jumpToFoodDetail
import com.lxh.cookcommunity.ui.fragment.home.FoodRecyclerAdapter
import kotlin.math.abs


class ClassifyFragment : BaseFragment<FragmentClassifyBinding, ClassifyViewModel>(
    ClassifyViewModel::class.java, layoutRes = R.layout.fragment_classify
) {

    private var state: CollapsingToolbarLayoutState? = null

    private enum class CollapsingToolbarLayoutState {
        EXPANDED, COLLAPSED, INTERNEDIATE
    }

    override fun initView() {
        binding.rvChooseTime.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            adapter = FoodClassifyChooseRecyclerAdapter(
                listOf(
                    ChooseClassify(name = "早"),
                    ChooseClassify(name = "中"),
                    ChooseClassify(name = "晚")
                )
            ){
                viewModel.timeMutableLiveData.value = it
                viewModel.fetchFoodList()
            }
        }

        binding.rvChooseCuisine.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            adapter = FoodClassifyChooseRecyclerAdapter(
                listOf(
                    ChooseClassify(name = "川"),
                    ChooseClassify(name = "鲁"),
                    ChooseClassify(name = "粤"),
                    ChooseClassify(name = "苏"),
                    ChooseClassify(name = "浙"),
                    ChooseClassify(name = "闽"),
                    ChooseClassify(name = "湘"),
                    ChooseClassify(name = "徽")
                )
            ){
                viewModel.groupMutableLiveData.value = it
                viewModel.fetchFoodList()
            }
        }

        binding.rvChooseType.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            adapter = FoodClassifyChooseRecyclerAdapter(
                listOf(
                    ChooseClassify(name = "面"),
                    ChooseClassify(name = "汤"),
                    ChooseClassify(name = "炒菜")
                )
            ){
                viewModel.typeMutableLiveData.value = it
                viewModel.fetchFoodList()
            }
        }

        binding.rvFood.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = FoodRecyclerAdapter(this@ClassifyFragment) {
                context?.jumpToFoodDetail(it)
            }
        }

        binding.appbar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                if (state !== CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED //修改状态标记为展开
                    binding.clChoose.visibility = View.VISIBLE
                }
            } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                if (state !== CollapsingToolbarLayoutState.COLLAPSED) {
                    binding.clChoose.visibility = View.INVISIBLE //隐藏播放按钮
                    state = CollapsingToolbarLayoutState.COLLAPSED //修改状态标记为折叠
                }
            } else {
                if (state !== CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (state === CollapsingToolbarLayoutState.COLLAPSED) {
                        //由折叠变为中间状态时隐藏播放按钮
                        binding.clChoose.visibility = View.INVISIBLE
                    }
                    state = CollapsingToolbarLayoutState.INTERNEDIATE //修改状态标记为中间
                }
            }
        })

    }

    override fun initData() {
        viewModel.fetchFoodList()
    }

    override fun initDataObServer() {
        viewModel.foodListMutableLiveData.observeNonNull {
            (binding.rvFood.adapter as FoodRecyclerAdapter).replaceData(it)
        }
    }

}

fun Context.jumpToClassify() {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.Classify
    )
    this.startActivity(intent)
}