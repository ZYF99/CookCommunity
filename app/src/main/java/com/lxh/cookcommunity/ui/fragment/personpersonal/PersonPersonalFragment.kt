package com.lxh.cookcommunity.ui.fragment.personpersonal

import android.content.Context
import android.view.View
import androidx.activity.ComponentActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentPersonPersonalBinding
import com.lxh.cookcommunity.manager.api.base.globalMoshi
import com.lxh.cookcommunity.model.api.UserProfileModel
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.moment.MomentRecyclerAdapter
import com.lxh.cookcommunity.ui.fragment.momentdetail.jumpToMomentDetail
import com.lxh.cookcommunity.util.fromJson
import com.lxh.cookcommunity.util.fullScreen
import com.lxh.cookcommunity.util.toJson

class PersonPersonalFragment : BaseFragment<FragmentPersonPersonalBinding, PersonPersonalViewModel>(
    PersonPersonalViewModel::class.java, layoutRes = R.layout.fragment_person_personal
) {

    override fun initView() {
        viewModel.personProfileLiveData.value =
            globalMoshi.fromJson(arguments?.getString("personProfile"))

        binding.rvRecentMoment.adapter = MomentRecyclerAdapter(
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

    }


    override fun initData() {
        viewModel.fetchRecentMoments()
    }

    override fun initDataObServer() {

        viewModel.commonListPageModelLiveData.observeNonNull {
            (binding.rvRecentMoment.adapter as MomentRecyclerAdapter).replaceData(it.dataList)
        }

        viewModel.isLoadingMore.observeNonNull {
            (binding.rvRecentMoment.adapter as CommonListRecyclerAdapter<*, *>).onLoadMore.postValue(
                it
            )
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

    override fun onResume() {
        super.onResume()
        activity?.let { fullScreen(it) }
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        activity?.window?.decorView?.systemUiVisibility = View.VISIBLE
    }

}

fun Context.jumpToPersonPersonal(personProfile: UserProfileModel? = null) {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.PersonPersonal,
        bundle = bundleOf(Pair("personProfile", personProfile.toJson()))
    )
    this.startActivity(intent)
}