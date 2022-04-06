package ru.kudesnik.android3_1.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.kudesnik.android3_1.App
import ru.kudesnik.android3_1.app
import ru.kudesnik.android3_1.data.LoginUseCaseImpl
import ru.kudesnik.android3_1.databinding.ActivityMainBinding
import ru.kudesnik.android3_1.domain.LoginUseCase
import ru.kudesnik.android3_1.ui.login.LoginContract
import ru.kudesnik.android3_1.ui.login.LoginPresenter


class MainActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = initPresenter()
        presenter?.onAttach(this)
        with(binding) {
            signInButton.setOnClickListener {
                presenter?.onLogin(
                    loginEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            signUpButton.setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Переходим во фрагмент Регистрация",
                    Toast.LENGTH_SHORT
                ).show()
            }
            forgotPasswordButton.setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Переходим во фрагмент Напомнить пароль",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initPresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter(app.loginUseCase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    override fun setSuccess() {
        binding.signInButton.isVisible = false
        binding.loginEditText.isVisible = false
        binding.passwordEditText.isVisible = false
        binding.forgotPasswordButton.isVisible = false
        binding.signUpButton.isVisible = false
        binding.root.setBackgroundColor(Color.GREEN)
    }

    override fun setError(error: String) {
        Toast.makeText(this, "ERROR: $error", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        binding.signInButton.isEnabled = false
    }

    override fun hideProgress() {
        binding.signInButton.isEnabled = true
        hideKeyboard(this)
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onBackPressed() {
        if (!binding.loginEditText.isVisible) {
            binding.signInButton.isVisible = true
            binding.loginEditText.isVisible = true
            binding.passwordEditText.isVisible = true
            binding.forgotPasswordButton.isVisible = true
            binding.signUpButton.isVisible = true
            binding.root.setBackgroundColor(0)
        } else super.onBackPressed()
    }
}