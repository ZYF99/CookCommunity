package com.lxh.cookcommunity

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.di.apiModule
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import io.reactivex.plugins.RxJavaPlugins
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.instance

open class MainApplication : Application(), KodeinAware {

    val apiService by instance<ApiService>()

    companion object {
        var instance: MainApplication? = null
    }

    override val kodein by Kodein.lazy {
        import(androidXModule(this@MainApplication))
        import(apiModule)
        /* bindings */
    }

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        Kotpref.gson = Gson()
        if (instance == null) instance = this
        SharedPrefModel.cacheDir = externalCacheDir?.path ?: ""
        RxJavaPlugins.setErrorHandler {
            Log.d("RxThrowable", it.message.toString())
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}