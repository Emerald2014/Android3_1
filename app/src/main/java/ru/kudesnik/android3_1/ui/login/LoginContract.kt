package ru.kudesnik.android3_1.ui.login

import androidx.annotation.MainThread
import ru.kudesnik.android3_1.ui.utils.Publisher

class LoginContract {

    interface View {
        @MainThread
        fun setSuccess()

        @MainThread
        fun setError(error: String)

        @MainThread
        fun showProgress()

        @MainThread
        fun hideProgress()
    }

    interface Presenter {
        @MainThread
        fun onAttach(view: View)

        @MainThread
        fun onLogin(login: String, password: String)
    }

    interface ViewModel {
        val shouldShowProgress: Publisher<Boolean>
        val isSuccess: Publisher<Boolean>
        val errorText: Publisher<String>

        @MainThread
        fun onLogin(login: String, password: String)
    }
}