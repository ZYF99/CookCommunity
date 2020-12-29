package com.lxh.cookcommunity.ui.widget

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lxh.cookcommunity.R

abstract class BottomDialogView<B : ViewDataBinding>(
	context: Context?,
	private val resId: Int
) : AlertDialog(context, R.style.MyDialog) {
	
	lateinit var childBinding: B

	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		childBinding = DataBindingUtil.inflate(layoutInflater,resId,null,false)
		setContentView(childBinding.root)
		
		setCancelable(true)
		setCanceledOnTouchOutside(true)
		
		val window = this.window
		window?.setGravity(Gravity.BOTTOM)
		val params = window?.attributes
		params!!.width = WindowManager.LayoutParams.MATCH_PARENT
		params.height = WindowManager.LayoutParams.WRAP_CONTENT
		window.attributes = params
		initView()
	}
	
	abstract fun initView()
	
	
}