package ru.kudesnik.android3_1.data

import android.os.Handler
import ru.kudesnik.android3_1.domain.LoginApi
import ru.kudesnik.android3_1.domain.LoginUseCase

class LoginUseCaseImpl(private val api: LoginApi, private val uiHandler: Handler) : LoginUseCase {
    override fun login(login: String, password: String, callback: (Boolean) -> Unit) {
        Thread {
            val result = api.login(login, password)
            uiHandler.post {
                callback(result)

            }
        }.start()
    }
}