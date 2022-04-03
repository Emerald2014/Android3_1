package ru.kudesnik.android3_1

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
import ru.kudesnik.android3_1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = LoginPresenter()
        presenter?.onAttach(this)

        binding.loginButton.setOnClickListener {
            presenter?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }

    override fun setSuccess() {
        binding.loginButton.isVisible = false
        binding.loginEditText.isVisible = false
        binding.passwordEditText.isVisible = false
        binding.root.setBackgroundColor(Color.GREEN)

    }

    override fun setError(error: String) {
        Toast.makeText(this, "ERROR: $error", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        binding.loginButton.isEnabled = false
    }

    override fun hideProgress() {
        binding.loginButton.isEnabled = true
        hideKeyboard(this)
    }

    override fun getHandler(): Handler {
        return Handler(Looper.getMainLooper())
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
}