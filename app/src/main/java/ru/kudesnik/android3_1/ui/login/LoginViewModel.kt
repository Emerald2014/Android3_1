package ru.kudesnik.android3_1.ui.login

import ru.kudesnik.android3_1.domain.LoginUseCase
import ru.kudesnik.android3_1.ui.utils.Publisher


class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : LoginContract.ViewModel {
    override val shouldShowProgress: Publisher<Boolean> = Publisher()
    override val isSuccess: Publisher<Boolean> = Publisher()
    override val errorText: Publisher<String> = Publisher(true)
    private var errorPassword = "Неверный пароль!!!"

    override fun onLogin(login: String, password: String) {
        shouldShowProgress.post(true)

        loginUseCase.login(login, password) { result ->
            shouldShowProgress.post(false)
            if (result) {
                isSuccess.post(true)
                errorText.post("")
            } else {
                isSuccess.post(false)
                errorText.post(errorPassword)
            }
        }
    }
}