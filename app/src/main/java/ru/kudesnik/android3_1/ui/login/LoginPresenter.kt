package ru.kudesnik.android3_1.ui.login

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import ru.kudesnik.android3_1.domain.LoginApi
import ru.kudesnik.android3_1.data.MockLoginApiImpl
import ru.kudesnik.android3_1.domain.LoginUseCase
import java.lang.Thread.sleep


class LoginPresenter(private val loginUseCase: LoginUseCase) : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var currentResult: Boolean = false
    private var errorText: String = ""
    private var errorPassword = "Неверный пароль!!!"

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        if (currentResult) {
            view.setSuccess()
        }
    }

    @SuppressLint("WrongThread")
    override fun onLogin(login: String, password: String) {
        view?.showProgress()

        loginUseCase.login(login, password) { result ->
            view?.hideProgress()
            if (result) {
                view?.setSuccess()
                currentResult = true
                errorText = ""
            } else {
                view?.setError(errorPassword)
                currentResult = false
                errorText = errorPassword
            }
        }
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password
    }
}