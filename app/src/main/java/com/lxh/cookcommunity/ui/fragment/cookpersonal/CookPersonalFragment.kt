package com.lxh.cookcommunity.ui.fragment.cookpersonal

import android.content.Context
import android.view.View
import androidx.core.os.bundleOf
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentCookPersonalBinding
import com.lxh.cookcommunity.manager.api.base.globalMoshi
import com.lxh.cookcommunity.model.api.cook.Chef
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.util.DialogUtil
import com.lxh.cookcommunity.util.fromJson
import com.lxh.cookcommunity.util.toJson

const val KEY_COOK_PERSONAL = "key_cook_personal"

/**
 * 初始个人主页
 * */
class CookPersonalFragment : BaseFragment<FragmentCookPersonalBinding, CookPersonalViewModel>(
    CookPersonalViewModel::class.java, layoutRes = R.layout.fragment_cook_personal
) {

    override fun initView() {
        viewModel.cookMutableLiveData.value = globalMoshi.fromJson(arguments?.getString(KEY_COOK_PERSONAL))
        binding.tvFollow.setOnClickListener {
            viewModel.follow {
                binding.tvFollow.visibility = View.GONE
                initData()
            }
        }
    }


    override fun initData() {
        viewModel.fetchChefFans()
        viewModel.fetchChefCourse()
        viewModel.checkIfFollow {
            binding.tvFollow.visibility = if (it) View.GONE else View.VISIBLE
        }
    }

    override fun initDataObServer() {
        viewModel.cookMutableLiveData.observeNonNull {
            binding.rvCourse.adapter = CourseListAdapter(it.courseList) { course ->
                DialogUtil.showPurchaseDialog(requireContext(), course.price ?: 0f)
            }
        }
        viewModel.fansNumMutableLiveData.observeNonNull {
            binding.tvFansNum.text = "粉丝数：$it"
        }
    }

}

fun Context.jumpToChefPersonal(chef: Chef?) {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.CookPersonal,
        bundle = bundleOf(Pair(KEY_COOK_PERSONAL, chef.toJson()))
    )
    this.startActivity(intent)
}

