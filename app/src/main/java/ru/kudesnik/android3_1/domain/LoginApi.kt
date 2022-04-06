package ru.kudesnik.android3_1.domain

interface LoginApi {
    fun login (login: String, password: String): Boolean
    fun register(login: String, password: String, email: String): Boolean
    fun logout(): Boolean
    fun forgotPassword(login: String): Boolean
}