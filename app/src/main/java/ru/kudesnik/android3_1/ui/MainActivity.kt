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
import ru.kudesnik.android3_1.app
import ru.kudesnik.android3_1.databinding.ActivityMainBinding
import ru.kudesnik.android3_1.ui.login.LoginContract
import ru.kudesnik.android3_1.ui.login.LoginViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var viewModel: LoginContract.ViewModel? = null
    private val handler: Handler by lazy { Handler(Looper.getMainLooper())  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = initViewModel()

        with(binding) {
            signInButton.setOnClickListener {
                viewModel?.onLogin(
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
            settingsFab.setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Переходим во фрагмент Настройки",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel?.shouldShowProgress?.subscribe(handler) { shouldShow ->
            if (shouldShow == true) {
                showProgress()
            } else {
                hideProgress()
            }
        }
        viewModel?.isSuccess?.subscribe(handler)  {
            if (it == true) {
                setSuccess()
            }
        }
        viewModel?.errorText?.subscribe(handler)  {
            it?.let {
                if(viewModel?.isSuccess?.value == false) {
                    setError(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.isSuccess?.unSubscribeAll()
        viewModel?.errorText?.unSubscribeAll()
        viewModel?.shouldShowProgress?.unSubscribeAll()
    }

    private fun initViewModel(): LoginViewModel {
        val viewModel = lastCustomNonConfigurationInstance as? LoginViewModel
        return viewModel ?: LoginViewModel(app.loginUseCase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
    }

    private fun setSuccess() {
        binding.signInButton.isVisible = false
        binding.loginEditTextLayout.isVisible = false
        binding.passwordEditTextLayout.isVisible = false
        binding.forgotPasswordButton.isVisible = false
        binding.signUpButton.isVisible = false
        binding.root.setBackgroundColor(Color.GREEN)
    }

    fun setError(error: String) {
        Toast.makeText(this, "ERROR: $error", Toast.LENGTH_SHORT).show()
    }

    private fun showProgress() {
        binding.signInButton.isEnabled = false
    }

    private fun hideProgress() {
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
        if (!binding.loginEditTextLayout.isVisible) {
            binding.signInButton.isVisible = true
            binding.loginEditTextLayout.isVisible = true
            binding.passwordEditTextLayout.isVisible = true
            binding.forgotPasswordButton.isVisible = true
            binding.signUpButton.isVisible = true
            binding.root.setBackgroundColor(0)
        } else super.onBackPressed()
    }
}