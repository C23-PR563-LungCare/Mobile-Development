package com.bangkit.lungcare.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.adapter.XrayAdapter
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentHistoryBinding
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel by viewModels<HistoryViewModel>()

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        val xrayAdapter = XrayAdapter()

        binding.rvHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = xrayAdapter
        }

        populatedData(xrayAdapter)
    }

    private fun populatedData(xrayAdapter: XrayAdapter) {
        viewModel.getAllXray()

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

                    val xrayData = result.data
                    xrayAdapter.submitList(xrayData)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}