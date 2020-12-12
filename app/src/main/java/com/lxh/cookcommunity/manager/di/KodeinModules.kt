package com.lxh.cookcommunity.manager.di

import com.example.rubbishcommunity.manager.base.BaiduIdentifyClient
import com.lxh.cookcommunity.BuildConfig
import com.lxh.cookcommunity.manager.api.ApiService
import com.lxh.cookcommunity.manager.api.BaiDuIdentifyService
import com.lxh.cookcommunity.manager.api.base.ApiClient
import com.lxh.cookcommunity.manager.api.base.HeaderInterceptor
import com.lxh.cookcommunity.manager.api.base.NetErrorInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.util.concurrent.TimeUnit

val apiModule = Kodein.Module("api") {
    bind<ApiClient>() with singleton { provideApiClient() }
    bind<ApiService>() with singleton { instance<ApiClient>().createService(ApiService::class.java) }
    //百度识别
    bind<BaiduIdentifyClient>() with singleton { provideBaiduIdentifyClient() }

    bind<BaiDuIdentifyService>() with singleton {
        instance<BaiduIdentifyClient>().createService(
            BaiDuIdentifyService::class.java
        )
    }
}

fun provideApiClient(): ApiClient {
    val client = ApiClient.Builder()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    client.okBuilder
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(NetErrorInterceptor())
/*        .addInterceptor(AddCookiesInterceptor())
        .addInterceptor(ReceivedCookiesInterceptor())*/
        .readTimeout(30, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }
    return client.build(baseUrl = BuildConfig.BASE_URL)
}

fun provideCommonApiClient(): ApiClient {
    val client = ApiClient.Builder()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    client.okBuilder
/*        .addInterceptor(AddCookiesInterceptor())
        .addInterceptor(ReceivedCookiesInterceptor())*/
        .readTimeout(30, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }

    return client.build(baseUrl = BuildConfig.BASE_URL)
}

/*成都本地局域网开发时使用*/
fun provideLocalApiClient(): ApiClient {
    val client = ApiClient.Builder()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    client.okBuilder
/*        .addInterceptor(AddCookiesInterceptor())
        .addInterceptor(ReceivedCookiesInterceptor())*/
        .readTimeout(30, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }

    return client.build(baseUrl = "http://192.168.59.117:8705/")
}

fun provideBaiduIdentifyClient(): BaiduIdentifyClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = BaiduIdentifyClient.Builder()
    client.okBuilder
        .readTimeout(30, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }
    return client.build()
}