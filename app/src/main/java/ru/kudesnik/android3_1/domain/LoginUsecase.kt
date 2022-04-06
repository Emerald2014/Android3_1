package ru.kudesnik.android3_1.domain

interface LoginUseCase  {
    fun login(login: String, password: String, callback: (Boolean) -> Unit)
}