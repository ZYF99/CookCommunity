package com.lxh.cookcommunity.ui.fragment.personal

import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentPersonalBinding
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.editinfo.jumpToEditInfo
import com.lxh.cookcommunity.ui.fragment.editinfo.userInfoHasChanged
import com.lxh.cookcommunity.ui.fragment.mark.jumpToMark
import com.lxh.cookcommunity.ui.fragment.moment.MomentRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.momentdetail.jumpToMomentDetail
import com.lxh.cookcommunity.util.fullScreen
import com.lxh.cookcommunity.util.setStatusTextColor

class PersonalFragment : BaseFragment<FragmentPersonalBinding, PersonalViewModel>(
    PersonalViewModel::class.java, layoutRes = R.layout.fragment_personal
) {

    override fun initView() {

        binding.rvMyMoment.adapter = MomentRecyclerAdapter(
            activity as ComponentActivity,
            this,
            onCellClick = {
                context?.jumpToMomentDetail(it)
            },
            onHeaderClick = {

            },
            onLikeClick = { momentContent, i ->
                viewModel.like(i, momentContent?.mid)
            },
            onCommitClick = {
                context?.jumpToMomentDetail(it)
            }
        )

        binding.btnSetting.setOnClickListener {
            context?.jumpToEditInfo()
        }

        binding.tvShop.setOnClickListener {
            context?.jumpToMark()
        }

        //上拉加载
        binding.rvMyMoment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    }

    override fun initDataObServer() {

        viewModel.attentionNumMutableLiveData.observeNonNull {
            binding.tvAttention.text = "关注：$it"
        }

        viewModel.fansNumMutableLiveData.observeNonNull {
            binding.tvFans.text = "粉丝：$it"
        }

        viewModel.commonListPageModelLiveData.observeNonNull {
            (binding.rvMyMoment.adapter as MomentRecyclerAdapter).replaceData(it.dataList)
        }

        viewModel.isLoadingMore.observeNonNull {
            (binding.rvMyMoment.adapter as CommonListRecyclerAdapter<*, *>).onLoadMore.postValue(it)
        }
        viewModel.changedMomentMutableLiveData.observeNonNull { changedMomentPair ->
            val newList = viewModel.commonListPageModelLiveData.value?.dataList?.mapIndexedNotNull { index, momentContent ->
                if(index == changedMomentPair.first) changedMomentPair.second
                else momentContent
            }
            viewModel.commonListPageModelLiveData.postValue(
                viewModel.commonListPageModelLiveData.value?.apply {
                    dataList = newList?.toMutableList()
                }
            )
        }
    }

    override fun initData() {
        viewModel.fetchUserProfileAndRecentMoments()
        viewModel.fetchMyFollowNum()
        viewModel.fetchMyFansNum()
    }

    override fun onResume() {
        super.onResume()
        //状态栏字体白色
        activity?.let { setStatusTextColor(false, it) }
        activity?.let { fullScreen(it) }
        if (userInfoHasChanged) {
            viewModel.fetchUserProfileAndRecentMoments()
        }
        if(binding.rvMyMoment.adapter!=null){
            initData()
        }
    }

}