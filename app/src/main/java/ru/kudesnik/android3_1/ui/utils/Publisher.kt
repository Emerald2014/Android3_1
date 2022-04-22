package ru.kudesnik.android3_1.ui.utils

import android.os.Handler

//private typealias Subscriber<T> = Pair<Handler, (T?) -> Unit>

class Publisher<T>(private val isSingle: Boolean = false) {
    private val subscribers: MutableSet<Subscriber<T?>> = mutableSetOf()
    var value: T? = null
        private set
    private var hasFirstValue = false

    fun subscribe(handler: Handler, callback: (T?) -> Unit) {
       val subscriber = Subscriber(handler, callback)
        subscribers.add(subscriber)
        if (hasFirstValue) {
                subscriber.invoke(value)
                    }
    }

    fun unSubscribeAll() {
        subscribers.clear()
    }

    fun post(value: T) {
        if (!isSingle) {
            hasFirstValue = true
            this.value = value
        }
        subscribers.forEach { it.invoke(value) }


    }
}

private class Subscriber<T>( val handler: Handler, val callback: (T?) -> Unit) {
    fun invoke(value:T?) {
        handler.post{
            callback.invoke(value)
        }
    }
}
