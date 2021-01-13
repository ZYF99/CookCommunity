package com.lxh.cookcommunity.ui.fragment.personpersonal

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentPersonPersonalBinding
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.moment.MomentRecyclerAdapter
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

        //上拉加载
        binding.rvRecentMoment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                if (viewModel.commonListPageModelLiveData.value != null) {
                    if (!recyclerView.canScrollVertically(1)) {
                        if (!viewModel.isLoadingMore.value!!
                            && viewModel.commonListPageModelLiveData.value?.pages ?: 0 > 1
                            && viewModel.commonListPageModelLiveData.value?.pageNum ?: 1 < viewModel.commonListPageModelLiveData.value?.pages ?: 1
                        ) {
                            viewModel.loadMore()
                        }
                    }
                }
            }
        })

        binding.ivBack.setOnClickListener {
            activity?.finish()
        }

    }


    override fun initData() {
        viewModel.fetchRecentMoments()
    }

    override fun initDataObServer() {

        viewModel.commonListPageModelLiveData.observeNonNull {
            (binding.rvRecentMoment.adapter as MomentRecyclerAdapter).replaceData(it.dataList)
        }

        viewModel.isLoadingMore.observeNonNull {
            (binding.rvRecentMoment.adapter as CommonListRecyclerAdapter<*, *>).onLoadMore.postValue(it)
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