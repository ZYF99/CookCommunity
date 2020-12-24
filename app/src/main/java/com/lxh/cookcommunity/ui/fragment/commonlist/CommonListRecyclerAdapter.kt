package com.lxh.cookcommunity.ui.fragment.commonlist

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.lxh.cookcommunity.BR
import com.lxh.cookcommunity.ui.adapter.LoadMoreRecyclerAdapter
import java.util.*

open class CommonListRecyclerAdapter<T, B : ViewDataBinding?>(
    lifecycleOwner: LifecycleOwner?,
    layoutRes: Int,
    hasLoadMore: Boolean?,
    baseList: List<T>?,
    private val onCellClick: ((T) -> Unit)? = null
) : LoadMoreRecyclerAdapter<T, B>(
    lifecycleOwner,
    layoutRes,
    hasLoadMore,
    baseList
) {
    override fun bindData(binding: B, position: Int) {
        binding?.setVariable(BR.model, baseList[position])
        binding?.root?.setOnClickListener {
            onCellClick?.invoke(baseList[position])
        }
    }
}