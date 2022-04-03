package ru.kudesnik.android3_1

import android.os.Handler

class LoginContract {

    interface View {
        fun setSuccess()
        fun setError(error: String)
        fun showProgress()
        fun hideProgress()
        fun getHandler() :Handler
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onCredentialsChanged()
    }
}