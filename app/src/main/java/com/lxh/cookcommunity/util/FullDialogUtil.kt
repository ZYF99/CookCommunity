package com.lxh.cookcommunity.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.LayoutCenterDialogBinding
import com.lxh.cookcommunity.databinding.LayoutFullDialogBinding
import com.lxh.cookcommunity.ui.widget.FullScreenDialog

fun <B : ViewDataBinding> Context.showFullDialog(
    dialogBinding: B,
    doOnShown: (() -> Unit)? = null,
    doOnDismiss: (() -> Unit)? = null
): FullScreenDialog {

    val dialog = FullScreenDialog(this, dialogBinding)

    dialogBinding.root.findViewById<ImageView>(R.id.iv_full_close)?.setOnClickListener {
        doOnDismiss?.invoke()
        dialog.dismiss()
        (dialogBinding.root.parent as ViewGroup).removeView(dialogBinding.root)
    }
    try {
        dialog.show()
        doOnShown?.invoke()
    }catch (e:Throwable){
        e.printStackTrace()
    }
    return dialog
}

fun getFullDialogBinding(context: Context): LayoutFullDialogBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_full_dialog,
        null,
        false
    )
}

fun <B : ViewDataBinding> Context.showCenterDialog(
    dialogBinding: B,
    doOnShown: (() -> Unit)? = null,
    doOnDismiss: (() -> Unit)? = null
): FullScreenDialog {

    val dialog = FullScreenDialog(this, dialogBinding)

    dialogBinding.root.findViewById<ImageView>(R.id.iv_center_close)?.setOnClickListener {
        dialog.dismiss()
        doOnDismiss?.invoke()
    }
    try {
        dialog.show()
        doOnShown?.invoke()
    }catch (e:Throwable){
        e.printStackTrace()
    }
    return dialog
}

fun getCenterDialogBinding(context: Context): LayoutCenterDialogBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_center_dialog,
        null,
        false
    )
}