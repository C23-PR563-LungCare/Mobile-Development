package com.bangkit.lungcare.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.FragmentLoginBinding
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.data.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkCredential().observe(viewLifecycleOwner) { isLogin ->
            if (isLogin) {
                moveToMain()
            }
        }

        binding.apply {

            loginBtn.setOnClickListener {
                setupLogin()
            }

            goToSignupTv.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }

            forgotPasswordTv.setOnClickListener {
                showToast(getString(R.string.coming_soon))
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

        viewModel.loginResult.observe(viewLifecycleOwner, observerLogin)
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

                val token = result.data.token
                token?.let {
                    viewModel.saveCredential(it)
                }
                moveToMain()
            }
        }
    }

    private fun moveToMain() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHome())
    }

    private fun showToast(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}