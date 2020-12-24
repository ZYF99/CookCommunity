package com.lxh.cookcommunity.ui.fragment.personpersonal

import android.content.Context
import androidx.activity.ComponentActivity
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentPersonPersonalBinding
import com.lxh.cookcommunity.model.api.moments.MomentRecyclerAdapter
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.momentdetail.jumpToMomentDetail

class PersonPersonalFragment : BaseFragment<FragmentPersonPersonalBinding, PersonPersonalViewModel>(
    PersonPersonalViewModel::class.java, layoutRes = R.layout.fragment_person_personal
) {

    override fun initView() {
        binding.rvRecentMoment.adapter = MomentRecyclerAdapter(
            activity as ComponentActivity,
            this,
            onCellClick = {
                context?.jumpToMomentDetail(it)
            },
            onHeaderClick = {

            },
            onLikeClick = { momentContent, i ->

            },
            onCommitClick = {
                context?.jumpToMomentDetail(it)
            }
        )

        binding.ivBack.setOnClickListener {
            activity?.finish()
        }

    }


    override fun initData() {
        viewModel.fetchRecentMoments()
    }

    override fun initDataObServer() {
        viewModel.myRecentMomentMutableLiveDataList.observeNonNull {
            (binding.rvRecentMoment.adapter as MomentRecyclerAdapter).replaceData(it)
        }
    }

}

fun Context.jumpToPersonPersonal() {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.PersonPersonal
    )
    this.startActivity(intent)
}