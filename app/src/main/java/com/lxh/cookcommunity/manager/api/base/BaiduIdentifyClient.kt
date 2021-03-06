package com.example.rubbishcommunity.manager.base

import android.annotation.SuppressLint
import android.util.Log
import com.lxh.cookcommunity.BuildConfig
import com.lxh.cookcommunity.manager.api.base.globalMoshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.reflect.KClass


/**
 * @author Zhangyf
 * @version 1.0
 * @date 2019/11/20 14：12
 */
class BaiduIdentifyClient(
	private val retrofit: Retrofit
) {
	
	fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)
	fun <S : Any> createService(serviceClass: KClass<S>): S = createService(serviceClass.java)
	
	open class Builder(
		//val apiAuthorizations: MutableMap<String, Interceptor> = LinkedHashMap(),
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
					Log.e("BaiduIdentify","allowAllSSLSocketFactory has error")
					null
				}
			}
		
		//信任证书
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
		
		fun setAllowAllCerTificates():BaiduIdentifyClient.Builder {
			allowAllSSLSocketFactory?.apply {
				okBuilder.sslSocketFactory(first, second)
				okBuilder.hostnameVerifier(HostnameVerifier { _, _ -> true })
			}
			return this
		}
		
		fun build(url: String? = null): BaiduIdentifyClient {
			
			val baseUrl = url ?: BuildConfig.BAIDU_IDENTIFY_URL
			
			adapterBuilder
				.baseUrl(baseUrl)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(MoshiConverterFactory.create(globalMoshi).asLenient())

			val client = okBuilder.addInterceptor { chain ->
				val origin = chain.request()
				val request = origin
					.newBuilder()
					.header("Accept", "application/json;charset=UTF-8")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.method(origin.method, origin.body)
					.build()
				chain.proceed(request)
			}.build()
			
			if (BuildConfig.ALLOW_ALL_CERTIFICATES)
				setAllowAllCerTificates()
			
			val retrofit = adapterBuilder.client(client).build()
			return BaiduIdentifyClient(retrofit)
		}
	}

/*	companion object {
		val defaultClient: ApiClient
			get() = NetClient.Builder<T>().build()
	}*/
}
