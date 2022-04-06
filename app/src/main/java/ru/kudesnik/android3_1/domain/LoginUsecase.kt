package ru.kudesnik.android3_1.domain

import androidx.annotation.MainThread

interface LoginUseCase {
    fun login(login: String, password: String, @MainThread callback: (Boolean) -> Unit)
}