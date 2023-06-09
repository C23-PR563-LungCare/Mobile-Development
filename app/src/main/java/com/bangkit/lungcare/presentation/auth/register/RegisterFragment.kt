package com.bangkit.lungcare.presentation.auth.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentRegisterBinding
import com.bangkit.lungcare.domain.model.Register
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel by viewModels<RegisterViewModel>()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            registerBtn.setOnClickListener {
                setupRegister()
            }

            goToLoginTv.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
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

        viewModel.registerResult.observe(viewLifecycleOwner, observerRegister)
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
        binding.emailEdtLayout.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.passwordEdtLayout.error =
            if (isNotValid) getString(R.string.password_not_valid) else null
    }

    private fun moveToLogin() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun showToast(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}