package com.lxh.cookcommunity.ui.base

import android.util.Log
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


interface BindLife {
    val compositeDisposable: CompositeDisposable

    fun Disposable.bindLife() = addDisposable(this)

    fun Single<*>.bindLife() = subscribe({ }, { Log.e(it.message, "Single error") }).bindLife()

    fun Observable<*>.bindLife() =
        subscribe({ }, { Log.e(it.message, "Observable error") }).bindLife()

    fun Completable.bindLife() =
        subscribe({ }, { Log.e(it.message, "Completable error") }).bindLife()

    fun Maybe<*>.bindLife() = subscribe({}, { Log.e(it.message, "Maybe error") }).bindLife()

    fun Flowable<*>.bindLife() = subscribe({ }, { Log.e(it.message, "Flowable  error") }).bindLife()

    fun addDisposable(disposable: Disposable): Disposable {
        compositeDisposable.add(disposable)
        return disposable
    }

    fun removeDisposable(disposable: Disposable?) {
        if (disposable != null)
            compositeDisposable.remove(disposable)
    }

    fun destroyDisposable() = compositeDisposable.clear()
}