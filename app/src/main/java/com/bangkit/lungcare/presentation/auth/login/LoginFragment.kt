package com.bangkit.lungcare.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentLoginBinding
import com.bangkit.lungcare.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()

    private var _loginBinding: FragmentLoginBinding? = null
    private val loginBinding get() = _loginBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        observerLogin()
    }

    private fun setupAction() {

//        loginBinding.signupTv.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
//        }

        loginBinding.loginBtn.setOnClickListener {
            val email = loginBinding.emailEdt.text.toString()
            val password = loginBinding.passwordEdt.text.toString()

            when {
                email.isEmpty() -> {
                    loginBinding.emailEdtLayout.error = getString(R.string.err_email_field)
                }

                password.isEmpty() -> {
                    loginBinding.passwordEdtLayout.error = getString(R.string.err_password_field)
                }

                else -> {
                    loginViewModel.login(email, password)
                }
            }
        }
    }

    private fun observerLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                loginViewModel.loginResult.collect { result ->
                    when (result) {
                        is Result.Success -> {
                            loginBinding.progressbar.visibility = View.GONE

                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }

                        is Result.Loading -> {
                            loginBinding.progressbar.visibility = View.VISIBLE
                        }

                        is Result.Error -> {
                            loginBinding.progressbar.visibility = View.GONE

                            Toast.makeText(requireActivity(), result.error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _loginBinding = null
    }

}