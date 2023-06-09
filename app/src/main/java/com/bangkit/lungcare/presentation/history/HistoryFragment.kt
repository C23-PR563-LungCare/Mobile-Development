package com.bangkit.lungcare.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.R
import com.bangkit.lungcare.adapter.XrayAdapter
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentHistoryBinding
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel by viewModels<HistoryViewModel>()

    private val binding: FragmentHistoryBinding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private lateinit var adapterXray: XrayAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterXray = XrayAdapter()

        binding.rvHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adapterXray
        }

        observerToken()
    }

    private fun observerToken() {
        viewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (token.isEmpty()) {
                moveToLogin()
            } else {
                setupListDataXray("Bearer $token")
            }
        }
    }

    private fun setupListDataXray(token: String) {
        viewModel.getAllXray(token)
        viewModel.xrayResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
                }

                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE

                    if (result.data.isEmpty()) {
                        with(binding) {
                            emptyDataTv.visibility = View.VISIBLE
                            emptyDataTv.text = getString(R.string.no_data_message)
                            rvHistory.visibility = View.GONE
                        }
                    } else {
                        binding.rvHistory.visibility = View.VISIBLE
                        result.data.let {
                            adapterXray.submitList(it)
                        }
                    }

                }
            }
        }
    }

    private fun moveToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}