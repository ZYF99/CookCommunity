package com.lxh.cookcommunity.ui.base

import androidx.lifecycle.MutableLiveData

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    fun handleIfNot(handle: (T) -> Unit) {
        handleIfNotWhen(true, handle)
    }

    fun handleIfNotWhen(condition: Boolean, handle: (T) -> Unit) {
        if (!condition) return

        if (!hasBeenHandled) {
            hasBeenHandled = true
            handle(content)
        }
    }
}

open class LiveEvent<E : Event<*>> : MutableLiveData<E>()

open class LiveDataEvent<E> : MutableLiveData<Event<E>>() {

    fun setEventValue(value: E) {
        super.setValue(Event(value))
    }

    fun postEventValue(value: E) {
        super.postValue(Event(value))
    }
}

/**
 *  LiveDataEvent without payload
 */

open class LiveDataEmptyEvent : LiveDataEvent<Unit>() {
    fun sendEvent() {
        postEventValue(Unit)
    }
}
