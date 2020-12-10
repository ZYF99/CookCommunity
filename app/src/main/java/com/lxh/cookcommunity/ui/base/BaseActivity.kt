package com.lxh.cookcommunity.ui.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein


abstract class BaseActivity : AppCompatActivity(), KodeinAware, BindLife {
    override val compositeDisposable = CompositeDisposable()
    override val kodein by closestKodein()
    private var currentThrottleTime = 0L

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    /**
     *  startActivity / Navigation with Throttle by default
     */

    fun startActivity(intent: Intent?, throttleFirst: Boolean) {
        if (throttleFirst) startActivity(intent)
        else super.startActivity(intent)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        if (System.currentTimeMillis() - currentThrottleTime < 1000) return
        currentThrottleTime = System.currentTimeMillis()
        super.startActivityForResult(intent, requestCode, options)
    }

    //ext
    protected fun <T> LiveData<T>.observe(observer: (T?) -> Unit) where T : Any =
        observe(this@BaseActivity, Observer<T> { v -> observer(v) })

    protected fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            if (it != null) {
                observer(it)
            }
        })
    }


}
