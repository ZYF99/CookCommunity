package com.lxh.cookcommunity.manager.api.base

import android.annotation.SuppressLint
import com.lxh.cookcommunity.BuildConfig
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*
import kotlin.reflect.KClass

class ApiClient constructor(val retrofit: Retrofit, val okHttpClient: OkHttpClient) {

    fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)

    fun <S : Any> createService(serviceClass: KClass<S>): S = createService(serviceClass.java)

    class Builder(
        val apiAuthorizations: MutableMap<String, Interceptor> = linkedMapOf(),
        val okBuilder: OkHttpClient.Builder = OkHttpClient.Builder(),
        val adapterBuilder: Retrofit.Builder = Retrofit.Builder()
    ) {

        private val allowAllSSLSocketFactory: Pair<SSLSocketFactory, X509TrustManager>?
            get() {
                return try {
                    val sslContext = SSLContext.getInstance("TLS")
                    val trustManager = trustManagerAllowAllCerts
                    sslContext.init(
                        null,
                        arrayOf<TrustManager>(trustManager),
                        SecureRandom()
                    )

                    sslContext.socketFactory to trustManager
                } catch (e: Exception) {
                    null
                }
            }

        /**
         * 证书信任
         */
        private val trustManagerAllowAllCerts: X509TrustManager
            get() = object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>, authType: String
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>, authType: String
                ) {
                }
            }

        fun setAllowAllCerTificates(): Builder {
            allowAllSSLSocketFactory?.apply {
                okBuilder.sslSocketFactory(first, second)
                okBuilder.hostnameVerifier(HostnameVerifier { _, _ -> true })
            }

            return this
        }

        fun build(baseUrl: String): ApiClient {
            okBuilder.cookieJar(
                JavaNetCookieJar(
                    CookieManager(
                        null,
                        CookiePolicy.ACCEPT_ORIGINAL_SERVER
                    )
                )
            )

            adapterBuilder
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(globalMoshi).asLenient())

            if (BuildConfig.ALLOW_ALL_CERTIFICATES)
                setAllowAllCerTificates()

            val client = okBuilder.build()
            val retrofit = adapterBuilder.client(client).build()
            return ApiClient(retrofit, client)
        }
    }

    companion object {
        val defaultClient: ApiClient
            get() = Builder().build("")
    }
}
