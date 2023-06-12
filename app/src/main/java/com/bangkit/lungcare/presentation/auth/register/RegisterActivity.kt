package com.bangkit.lungcare.presentation.auth.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.ActivityRegisterBinding
import com.bangkit.lungcare.domain.model.user.Register
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel>()

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            registerBtn.setOnClickListener {
                setupRegister()
            }

            goToLoginTv.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }

        observableFormRegister()
    }

    @SuppressLint("CheckResult")
    private fun observableFormRegister() {
        val emailStream = RxTextView.textChanges(binding.emailEdt).skipInitialValue()
            .map { email -> !Patterns.EMAIL_ADDRESS.matcher(email).matches() }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.passwordEdt).skipInitialValue()
            .map { password -> password.length in 1..7 }
        passwordStream.subscribe {
            showPasswordMinimalAlert(it)
        }

    }

    private fun setupRegister() {
        val username = binding.nameEdt.text.toString()
        val email = binding.emailEdt.text.toString()
        val password = binding.passwordEdt.text.toString()

        when {
            username.isEmpty() -> {
                binding.nameEdtLayout.error = getString(R.string.err_name_field)
            }

            email.isEmpty() -> {
                binding.emailEdtLayout.error = getString(R.string.err_email_field)
            }

            password.isEmpty() -> {
                binding.passwordEdtLayout.error = getString(R.string.err_password_field)
            }

            else -> {
                viewModel.register(username, email, password)
            }
        }

        viewModel.registerResult.observe(this, observerRegister)
    }

    private val observerRegister = Observer<Result<Register>> { result ->
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
                showToast(getString(R.string.signup_success_message))
                moveToLogin()
            }
        }
    }
    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.emailEdtLayout.error =
            if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.passwordEdtLayout.error =
            if (isNotValid) getString(R.string.password_not_valid) else null
    }

    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}