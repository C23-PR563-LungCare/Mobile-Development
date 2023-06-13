package com.bangkit.lungcare.presentation.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.ActivityLoginBinding
import com.bangkit.lungcare.domain.model.user.Login
import com.bangkit.lungcare.presentation.MainActivity
import com.bangkit.lungcare.presentation.auth.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.checkCredential().observe(this) { isLogin ->
            if (isLogin) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finish()
            }
        }

        binding.apply {
            loginBtn.setOnClickListener {
                setupLogin()
            }

            goToSignupTv.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            forgotPasswordTv.setOnClickListener {
                showToast(getString(R.string.feature_not_available))
            }
        }

    }

    private fun setupLogin() {
        val email = binding.emailEdt.text.toString()
        val password = binding.passwordEdt.text.toString()

        when {
            email.isEmpty() -> {
                binding.emailEdtLayout.error = getString(R.string.err_email_field)
            }

            password.isEmpty() -> {
                binding.passwordEdtLayout.error = getString(R.string.err_password_field)
            }

            else -> {
                viewModel.login(email, password)
            }
        }

        viewModel.loginResult.observe(this, observerLogin)
    }

    private val observerLogin = Observer<Result<Login>>
    { result ->
        when (result) {
            is Result.Loading -> {
                binding.progressbar.visibility = View.VISIBLE
            }

            is Result.Error -> {
                binding.progressbar.visibility = View.GONE

                showToast(result.error)
            }

            is Result.Success -> {
                binding.progressbar.visibility = View.GONE

                val tokenResult = result.data.token
                tokenResult?.let {
                    viewModel.saveCredential(it)
                }
                moveToMain()
            }
        }
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}