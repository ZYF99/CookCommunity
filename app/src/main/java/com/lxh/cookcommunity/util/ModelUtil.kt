package com.lxh.cookcommunity.util

import com.lxh.cookcommunity.manager.api.base.globalMoshi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

inline fun <reified T> String?.fromJson(moshi: Moshi = globalMoshi): T? =
    this?.let { ModelUtil.fromJson(this, T::class.java, moshi = moshi) }

inline fun <reified T> T?.toJson(moshi: Moshi = globalMoshi): String =
    ModelUtil.toJson(this, T::class.java, moshi = moshi)

inline fun <reified T> Moshi.fromJson(json: String?): T? =
    json?.let { ModelUtil.fromJson(json, T::class.java, moshi = this) }

inline fun <reified T> Moshi.toJson(t: T?): String =
    ModelUtil.toJson(t, T::class.java, moshi = this)

inline fun <reified T> List<T>.listToJson(): String =
    ModelUtil.listToJson(this, T::class.java)

inline fun <reified T> String.jsonToList(): List<T>? =
    ModelUtil.jsonToList(this, T::class.java)

object ModelUtil {

    inline fun <reified S, reified T> copyModel(source: S): T? {
        return fromJson(
            toJson(
                any = source,
                classOfT = S::class.java
            ), T::class.java
        )
    }

    fun <T> toJson(any: T?, classOfT: Class<T>, moshi: Moshi = globalMoshi): String {
        return moshi.adapter(classOfT).toJson(any)
    }

    fun <T> fromJson(json: String, classOfT: Class<T>, moshi: Moshi = globalMoshi): T? {
        return moshi.adapter(classOfT).lenient().fromJson(json)
    }

    fun <T> fromJson(json: String, typeOfT: Type, moshi: Moshi = globalMoshi): T? {
        return moshi.adapter<T>(typeOfT).fromJson(json)
    }

    fun <T> listToJson(list: List<T>?, classOfT: Class<T>, moshi: Moshi = globalMoshi): String {
        val type = Types.newParameterizedType(List::class.java, classOfT)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
        return adapter.toJson(list)
    }

    fun <T> jsonToList(json: String, classOfT: Class<T>, moshi: Moshi = globalMoshi): List<T>? {
        val type = Types.newParameterizedType(List::class.java, classOfT)
        val adapter = moshi.adapter<List<T>>(type)
        return adapter.fromJson(json)
    }
}
