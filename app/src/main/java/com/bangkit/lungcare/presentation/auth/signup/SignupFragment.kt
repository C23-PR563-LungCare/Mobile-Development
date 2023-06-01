package com.bangkit.lungcare.presentation.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentSignupBinding
import com.bangkit.lungcare.domain.model.Signup
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupFragment : Fragment() {

    private val signupViewModel by viewModels<SignupViewModel>()

    private var _signupBinding: FragmentSignupBinding? = null
    private val signupBinding get() = _signupBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _signupBinding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        return signupBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupViewModel.signupResult.observe(viewLifecycleOwner) { result ->
            observerSignup(result)
        }

        setupAction()
    }

    private fun setupAction() {

        signupBinding.signInTv.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        signupBinding.signupBtn.setOnClickListener {

            val username = signupBinding.nameEdt.text.toString().trim()
            val email = signupBinding.nameEdt.text.toString()
            val password = signupBinding.passwordEdt.text.toString().trim()

            when {
                username.isEmpty() -> {
                    signupBinding.nameEdtLayout.error = getString(R.string.err_name_field)
                }

                email.isEmpty() -> {
                    signupBinding.emailEdtLayout.error = getString(R.string.err_email_field)
                }

                password.isEmpty() -> {
                    signupBinding.passwordEdtLayout.error = getString(R.string.err_password_field)
                }

                else -> {
                    signupViewModel.signup(username, email, password)
                }
            }
        }
    }

    private fun observerSignup(result: Result<Signup>) {
        when (result) {
            is Result.Success -> {
                signupBinding.progressbar.visibility = View.GONE

                Toast.makeText(requireActivity(), result.data.message, Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }

            is Result.Loading -> {
                signupBinding.progressbar.visibility = View.VISIBLE
            }

            is Result.Error -> {
                signupBinding.progressbar.visibility = View.GONE

                Toast.makeText(requireActivity(), result.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _signupBinding = null
    }
}