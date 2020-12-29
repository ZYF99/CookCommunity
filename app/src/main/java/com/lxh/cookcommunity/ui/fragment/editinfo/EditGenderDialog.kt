package com.lxh.cookcommunity.ui.fragment.editinfo


import android.content.Context
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.DialogEditGenderBinding
import com.lxh.cookcommunity.ui.widget.BottomDialogView

class EditGenderDialog(
	context: Context?,
	private val oldGender: String,
	val onConfirmClick: (String) -> Unit
) : BottomDialogView<DialogEditGenderBinding>(
	context,
	R.layout.dialog_edit_gender
) {
	
	var gender: String = oldGender
	
	override fun initView() {
		setCancelable(true)
		childBinding.gender = gender
		val genderList = mutableListOf("男","女")
		childBinding.wheelGender.run {
			setData(genderList)
			setSelected(gender)
			setOnSelectListener {
				onConfirmClick(it)
				dismiss()
			}
		}
		
	}
	
}