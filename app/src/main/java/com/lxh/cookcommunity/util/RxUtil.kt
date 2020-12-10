package com.zgxwxy.tuputech.util

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.switchThread(
    subscribeOn: Scheduler = Schedulers.io(),
    observeOn: Scheduler = AndroidSchedulers.mainThread()
): Single<T> = compose(SingleTransformer { it.subscribeOn(subscribeOn).observeOn(observeOn) })

fun <T> Maybe<T>.switchThread(
    subscribeOn: Scheduler = Schedulers.io(),
    observeOn: Scheduler = AndroidSchedulers.mainThread()
): Maybe<T> = compose(MaybeTransformer { it.subscribeOn(subscribeOn).observeOn(observeOn) })

fun <T> Flowable<T>.switchThread(
    subscribeOn: Scheduler = Schedulers.io(),
    observeOn: Scheduler = AndroidSchedulers.mainThread()
): Flowable<T> = compose(FlowableTransformer { it.subscribeOn(subscribeOn).observeOn(observeOn) })

fun <T> Observable<T>.switchThread(
    subscribeOn: Scheduler = Schedulers.io(),
    observeOn: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> =
    compose(ObservableTransformer { it.subscribeOn(subscribeOn).observeOn(observeOn) })

fun Completable.switchThread(
    subscribeOn: Scheduler = Schedulers.io(),
    observeOn: Scheduler = AndroidSchedulers.mainThread()
): Completable =
    compose(CompletableTransformer { it.subscribeOn(subscribeOn).observeOn(observeOn) })

fun Completable.skipError() = this.onErrorResumeNext { Completable.never() }

fun <T> Single<T>.skipError() = onErrorResumeNext { Single.never() }