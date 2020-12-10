package com.lxh.cookcommunity.ui.base

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.util.DialogUtil

data class DialogEventData(
    @StringRes val title: Int = R.string.dialog_title,
    @StringRes val msg: Int = R.string.dialog_msg,
    @StringRes val positiveButtonText: Int = R.string.dialog_ok,
    @StringRes val negativeButtonText: Int = R.string.dialog_cancel,
    @DialogUtil.ButtonType val positiveButton: Long = DialogUtil.BUTTON_TYPE_OK,
    @DialogUtil.ButtonType val negativeButton: Long = DialogUtil.BUTTON_TYPE_CANCEL
)

class DialogLiveEvent : LiveDataEvent<DialogEventData>()

fun DialogLiveEvent.handleEvent(context: Context, owner: LifecycleOwner) {
    observe(owner) { event ->
        event.handleIfNot { content ->
            DialogUtil.showDialogSingle(
                context,
                content.title,
                content.msg,
                content.positiveButtonText,
                content.negativeButtonText,
                content.positiveButton,
                content.negativeButton
            )
        }
    }
}