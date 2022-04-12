package ru.kudesnik.android3_1.data

import ru.kudesnik.android3_1.domain.LoginApi

class MockLoginApiImpl : LoginApi {
    override fun login(login: String, password: String): Boolean {
        return login == password
    }

    override fun register(login: String, password: String, email: String): Boolean {
        return login.isNotEmpty()
    }

    override fun logout(): Boolean {
        return true
    }

    override fun forgotPassword(login: String): Boolean {
        return false
    }
}