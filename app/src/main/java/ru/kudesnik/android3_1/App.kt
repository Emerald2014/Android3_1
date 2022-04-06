package ru.kudesnik.android3_1

import android.app.Application
import android.content.Context
import ru.kudesnik.android3_1.data.MockLoginApiImpl
import ru.kudesnik.android3_1.domain.LoginApi

class App:Application() {
    val api: LoginApi by lazy { MockLoginApiImpl() }

}

val Context.app: App
get() {
    return applicationContext.app
}