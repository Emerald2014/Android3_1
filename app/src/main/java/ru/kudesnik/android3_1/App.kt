package ru.kudesnik.android3_1

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import ru.kudesnik.android3_1.data.LoginUseCaseImpl
import ru.kudesnik.android3_1.data.MockLoginApiImpl
import ru.kudesnik.android3_1.domain.LoginApi
import ru.kudesnik.android3_1.domain.LoginUseCase

class App : Application() {
    private val loginApi: LoginApi by lazy { MockLoginApiImpl() }
    val loginUseCase: LoginUseCase by lazy {
        LoginUseCaseImpl(
            app.loginApi,
            Handler(Looper.getMainLooper())
        )
    }
}

val Context.app: App
    get() {
        return applicationContext.app
    }