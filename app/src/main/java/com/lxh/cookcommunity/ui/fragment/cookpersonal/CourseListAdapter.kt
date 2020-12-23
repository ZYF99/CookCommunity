package com.lxh.cookcommunity.ui.fragment.cookpersonal

import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemCourseBinding
import com.lxh.cookcommunity.model.api.cook.Course
import com.lxh.cookcommunity.ui.adapter.BaseRecyclerAdapter

class CourseListAdapter(
	list: List<Course>?
) : BaseRecyclerAdapter<Course,ItemCourseBinding>(
	R.layout.item_course,
	null
) {

	init {
		baseList = list?: emptyList()
	}

	override fun bindData(binding: ItemCourseBinding, position: Int) {
		val course = baseList[position]
		binding.course = course

	}

}