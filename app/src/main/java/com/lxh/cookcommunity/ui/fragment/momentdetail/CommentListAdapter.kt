package com.lxh.cookcommunity.ui.fragment.momentdetail

import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemCommentBinding
import com.lxh.cookcommunity.model.api.moments.MomentComment
import com.lxh.cookcommunity.ui.adapter.BaseRecyclerAdapter

class CommentListAdapter(
	private val onReplyClick: (comment: MomentComment) -> Unit,
	list: List<MomentComment>
) : BaseRecyclerAdapter<MomentComment,ItemCommentBinding>(
	R.layout.item_comment,
	null
) {

	init {
		baseList = list
	}

	override fun bindData(binding: ItemCommentBinding, position: Int) {
		val comment = baseList[position]
		binding.comment = comment


	}

}