package com.lxh.cookcommunity.ui.fragment.classify

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentClassifyBinding
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.fooddetail.jumpToFoodDetail
import com.lxh.cookcommunity.ui.fragment.home.FoodRecyclerAdapter

class ClassifyFragment : BaseFragment<FragmentClassifyBinding, ClassifyViewModel>(
    ClassifyViewModel::class.java, layoutRes = R.layout.fragment_classify
) {

    override fun initView() {
        binding.rvChooseTime.apply {
            layoutManager =
                LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            adapter = FoodClassifyChooseRecyclerAdapter(
                listOf(
                    ChooseClassify(name = "早"),
                    ChooseClassify(name = "中"),
                    ChooseClassify(name = "晚")
                )
            )
        }

        binding.rvChooseCuisine.apply {
            layoutManager =
                LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
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
            )
        }

        binding.rvChooseType.apply {
            layoutManager =
                LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            adapter = FoodClassifyChooseRecyclerAdapter(
                listOf(
                    ChooseClassify(name = "面"),
                    ChooseClassify(name = "汤"),
                    ChooseClassify(name = "炒菜")
                )
            )
        }

        binding.rvFood.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = FoodRecyclerAdapter(this@ClassifyFragment) {
                context?.jumpToFoodDetail(it)
            }
        }

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