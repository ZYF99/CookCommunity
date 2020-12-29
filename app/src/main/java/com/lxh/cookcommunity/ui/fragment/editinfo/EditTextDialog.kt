package com.lxh.cookcommunity.ui.fragment.editinfo


import android.content.Context
import android.text.InputFilter
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.DialogEditTextBinding
import com.lxh.cookcommunity.ui.widget.BottomDialogView

class EditTextDialog(
	context: Context?,
	private val title: String,
	private val text: String,
	private val maxLength: Int,
	val onConfirmClick: (String) -> Unit
) : BottomDialogView<DialogEditTextBinding>(
	context,
	R.layout.dialog_edit_text
) {
	
	override fun initView() {
		setCancelable(true)
		childBinding.title = title
		childBinding.text = text
		childBinding.maxLength = maxLength
		childBinding.editText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
		childBinding.btnConfirm.setOnClickListener {
			dismiss()
			onConfirmClick(childBinding.text ?: text)
		}
	}
	
}