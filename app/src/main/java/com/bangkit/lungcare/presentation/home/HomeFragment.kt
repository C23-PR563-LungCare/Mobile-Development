package com.bangkit.lungcare.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentHomeBinding
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import com.bangkit.lungcare.presentation.home.post.PostXrayActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
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

        binding.itemHome.toPostXrayBtn.setOnClickListener {
            val intent = Intent(requireActivity(), PostXrayActivity::class.java)
            startActivity(intent)
        }

        observerToken()
    }

    private fun observerToken() {
        viewModel.getToken().observe(viewLifecycleOwner) { token ->
            Log.d("HomeFragment", "getToken: $token")
            if (token.isEmpty()) {
                moveToLogin()
            } else {
                setupData("Bearer $token")
            }
        }
    }

    private fun setupData(token: String) {
        // for profile
        viewModel.getUserProfile(token)

        viewModel.userProfileResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Error -> {}
                is Result.Success -> {
                    val profileData = result.data

                    binding.apply {
                        profileIv.setImageResource(R.drawable.account_circle)
                        headingNameTv.text =
                            getString(R.string.welcome_message, profileData.username)
                    }
                }
            }
        }

        // for article
    }

    private fun moveToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        const val EXTRA_PREDICTION = "extra_prediction"
    }
}