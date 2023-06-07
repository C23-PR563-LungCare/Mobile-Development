package com.bangkit.lungcare.presentation.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentRegisterBinding
import com.bangkit.lungcare.domain.model.Register
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        setupAction()
    }

    private fun setupAction() {
        binding.registerBtn.setOnClickListener {
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
    }

    private val observerRegister = Observer<Result<Register>> { result ->
        when (result) {
            is Result.Loading -> {
                binding.progressbar.visibility = View.VISIBLE
            }

            is Result.Error -> {
                binding.progressbar.visibility = View.GONE

                Toast.makeText(requireActivity(), result.error, Toast.LENGTH_SHORT).show()
            }

            is Result.Success -> {
                binding.progressbar.visibility = View.GONE
                showToast(getString(R.string.signup_success_message))
                moveToLogin()
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun moveToLogin() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}