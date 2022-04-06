package ru.kudesnik.android3_1.data

import ru.kudesnik.android3_1.domain.LoginUseCase

class LoginUseCaseImpl : LoginUseCase {
    override fun login(login: String, password: String, callback: (Boolean) -> Unit) {
        Thread {
            val result = api.login(login, password)
            uiHandler.post {
                callback(result)

            }
        }.start()
    }
}