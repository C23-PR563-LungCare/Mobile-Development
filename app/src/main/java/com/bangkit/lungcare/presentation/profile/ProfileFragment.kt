package com.bangkit.lungcare.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.FragmentProfileBinding
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel>()

    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            profileSettingBtn.setOnClickListener {
                showToast(getString(R.string.feature_not_available))
            }

            languageBtn.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            termOfServiceBtn.setOnClickListener {
                showToast(getString(R.string.feature_not_available))
            }

            logoutBtn.setOnClickListener {
                viewModel.logout()
                moveToLogin()
            }
        }
    }

    private fun moveToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}