package ru.kudesnik.android3_1.ui.login

import android.os.Handler
import android.os.Looper
import ru.kudesnik.android3_1.domain.LoginApi
import ru.kudesnik.android3_1.data.MockLoginApiImpl
import java.lang.Thread.sleep


class LoginPresenter(private val api: LoginApi) : LoginContract.Presenter {
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

    override fun onLogin(login: String, password: String) {
        view?.showProgress()

        loginUsecase.login(login, password, /* { ... } */)
        Thread {
            val success = api.login(login, password)
            uiHandler.post {
                view?.hideProgress()
                if (success) {
                    view?.setSuccess()
                    currentResult = true
                    errorText = ""
                } else {
                    view?.setError(errorPassword)
                    currentResult = false
                    errorText = errorPassword
                }
            }
        }.start()
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password

    }

    override fun onCredentialsChanged() {
        TODO("Not yet implemented")
    }


}