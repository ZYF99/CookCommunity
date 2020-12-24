package com.lxh.cookcommunity.ui.fragment.commonlist

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentCommonListBinding
import com.lxh.cookcommunity.ui.base.BaseFragment
import java.util.*

const val KEY_CLASSIFY = "key_classify"

abstract class CommonListFragment<Bean, VM : CommonListViewModel<Bean>, ItemBinding : ViewDataBinding?>(
    val classify: String,
    private val itemLayoutRes: Int,
    viewModelClazz: Class<VM>,
    val canRefresh: Boolean = true,
    val canSearch: Boolean = false
) : BaseFragment<FragmentCommonListBinding, VM>(
    viewModelClazz,
    R.layout.fragment_common_list
) {
    private var isFirstInit = true
    private var recyclerAdapter: CommonListRecyclerAdapter<Bean, ItemBinding>? = null

    abstract val onCellClick: ((Bean) -> Unit)?

    //列表布局
    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    //用于本类内部获取列表适配器
    private fun getRecyclerAdapter(): CommonListRecyclerAdapter<Bean, ItemBinding>? {
        if (recyclerAdapter != null) {
            return recyclerAdapter
        }
        recyclerAdapter = getCustomRecyclerAdapter()
        return recyclerAdapter
    }

    //抛向外部复写自定义的列表适配器
    open fun getCustomRecyclerAdapter(): CommonListRecyclerAdapter<Bean, ItemBinding>? {
        recyclerAdapter = CommonListRecyclerAdapter(
            this,
            itemLayoutRes,
            true,
            ArrayList<Bean>(),
            onCellClick = onCellClick
        )
        return recyclerAdapter
    }

    override fun initDataObServer() {
        super.initDataObServer()
        viewModel.isRefreshing.observeNonNull {
            binding.refreshLayout.isRefreshing = it
        }

        viewModel.isLoadingMore.observeNonNull {
            (binding.rvList.adapter as CommonListRecyclerAdapter<*, *>).onLoadMore.postValue(it)
        }

        viewModel.commonListPageModelLiveData.observeNonNull {
            getRecyclerAdapter()?.replaceData(it.dataList)
            binding.refreshLayout.isRefreshing = false
        }

        viewModel.commonListLiveData.observeNonNull {
            getRecyclerAdapter()?.replaceData(it)
            binding.refreshLayout.isRefreshing = false
        }
    }

    override fun initView() {
        if (canSearch) binding.etSearch.visibility =
            View.VISIBLE else binding.etSearch.visibility = View.GONE
        binding.refreshLayout.isEnabled = canRefresh
        binding.etSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(binding.etSearch.text.toString())
            }
            true
        }


        binding.rvList.layoutManager = getLayoutManager()
        binding.rvList.adapter = getRecyclerAdapter()

        //下拉刷新监听
        binding.refreshLayout.setOnRefreshListener { viewModel.refreshList(classify) }

        //上拉加载
        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                if (viewModel.commonListPageModelLiveData.value != null) {
                    if (!recyclerView.canScrollVertically(1)) {
                        if (!viewModel.isLoadingMore.value!! && viewModel.commonListPageModelLiveData.value?.pages ?: 0 > 1
                        ) {
                            viewModel.loadMoreShopList(classify)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        if (isFirstInit) {
            viewModel.refreshList(classify)
            isFirstInit = false
        }
    }


}