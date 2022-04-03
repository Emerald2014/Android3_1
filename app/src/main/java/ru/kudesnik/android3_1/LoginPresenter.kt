package ru.kudesnik.android3_1

import java.lang.Thread.sleep


class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null

    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(3000)
            view?.getHandler()?.post {
                view?.hideProgress()
                if (checkCredentials(login, password)) {
                    view?.setSuccess()
                } else {
                    view?.setError("Неверный пароль!!!")
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