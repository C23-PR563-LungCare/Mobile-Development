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
import androidx.navigation.fragment.findNavController
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}