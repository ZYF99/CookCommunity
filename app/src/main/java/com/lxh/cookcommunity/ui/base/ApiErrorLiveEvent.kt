package com.lxh.cookcommunity.ui.base

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import com.lxh.cookcommunity.util.ApiUtil
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import com.lxh.cookcommunity.util.DialogUtil
import com.lxh.cookcommunity.util.showToast

class ApiErrorLiveEvent : LiveDataEvent<Throwable>()

class SkipCatchApiException : RuntimeException()

fun <T> Single<T>.catchApiError(liveEvent: ApiErrorLiveEvent): Single<T> =
    doOnError { if (it is Exception && it !is SkipCatchApiException) liveEvent.value = Event(it) }

fun <T> Observable<T>.catchApiError(liveEvent: ApiErrorLiveEvent): Observable<T> =
    doOnError { if (it is Exception && it !is SkipCatchApiException) liveEvent.value = Event(it) }

fun Completable.catchApiError(liveEvent: ApiErrorLiveEvent): Completable =
    doOnError { if (it is Exception && it !is SkipCatchApiException) liveEvent.value = Event(it) }

fun ApiErrorLiveEvent.handleEvent(context: Context, owner: LifecycleOwner) {
    observe(owner) { event ->
        event.handleIfNot { error -> showApiErrorDialog(context, error) }
    }
}

fun showApiErrorDialog(context: Context, error: Throwable) {
    val errorMsg = ApiUtil.parseErrorMsg(context, error)
    showToast(errorMsg)
/*    AlertDialog.Builder(context)
        .setTitle(errorMsg.first)
        .setMessage(
            HtmlCompat.fromHtml(
                ApiUtil.parseErrorMsg(context, error).second,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        )
        .setPositiveButton("ok") { _, _ ->

        }
        .show()*/
}

fun showApiErrorDialogSingle(
    context: Context,
    error: Throwable,
    cancelable: Boolean = false
): Single<DialogUtil.DialogEvent> {
    val errorMsg = ApiUtil.parseErrorMsg(context, error)
    return DialogUtil.showDialogSingle(
        context,
        "",
        errorMsg,
        "ok",
        positiveButton = DialogUtil.BUTTON_TYPE_OK,
        negativeButton = DialogUtil.BUTTON_TYPE_NONE,
        cancelable = cancelable
    )
}