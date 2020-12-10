package com.lxh.cookcommunity.ui.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.util.DisplayMetrics
import com.lxh.cookcommunity.R


/**
 * 弹窗视图
 */
abstract class BasePopWindow(
    val context: Context,
    private val isFull: Boolean = false
) : PopupWindow(context) {

    init {
        initialize()
    }

    //得到弹窗中的控件
    abstract fun getView(inflater: LayoutInflater): View

    private fun initialize() {
        val inflater = LayoutInflater.from(context)
        val view = getView(inflater)
        contentView = view
        initWindow()
    }

    open fun initWindow() {
        //val d = context.resources.displayMetrics
        if (isFull) {
            this.width = ViewGroup.LayoutParams.MATCH_PARENT
            this.height = context.resources.displayMetrics.heightPixels
        } else {
            this.width = ViewGroup.LayoutParams.MATCH_PARENT
            this.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        this.isFocusable = true
        this.isOutsideTouchable = true
        this.update()
        //实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(0x00000000)
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw)
        //backgroundAlpha(context as Activity, 0.5f)//0.0-1.0
        this.setOnDismissListener { }
    }

    //设置添加屏幕的背景透明度
    private fun backgroundAlpha(context: Activity, bgAlpha: Float) {
        val lp = context.window.attributes
        lp.alpha = bgAlpha
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        context.window.attributes = lp
    }

    @SuppressLint("NewApi")
    fun showAbove(view: View) {
        //弹窗位置设置
        animationStyle = R.style.pop_animation
        val outMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getRealMetrics(outMetrics)
        val screenWidthPixel = outMetrics.widthPixels
        val screenHeightPixel = outMetrics.heightPixels
        //有偏差
        showAtLocation(view, Gravity.CENTER, 0,48)
    }

    fun showAtBottom(view: View) {
        //弹窗位置设置
        animationStyle = R.style.pop_animation
        showAtLocation(view, Gravity.BOTTOM, view.width, view.height)//有偏差
        //showAsDropDown(view, abs((view.width - width) / 2),  -abs((view.height - height)));
    }

    fun showAtCenter(view: View) {
        //弹窗位置设置
        animationStyle = R.style.pop_animation
        showAtLocation(view, Gravity.CENTER, 10, 110)//有偏差
    }

}
