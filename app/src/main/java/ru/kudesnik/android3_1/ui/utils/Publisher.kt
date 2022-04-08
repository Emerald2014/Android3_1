package ru.kudesnik.android3_1.ui.utils

class Publisher<T> {
    private var subscriber: Subscriber<T>? = null
    fun subscribe(subscriber: Subscriber<T>) {
        this.subscriber = subscriber
    }

    fun post(value: T) {
        subscriber?.post(value)
    }
}

interface Subscriber<T> {
    fun post(value: T)
}