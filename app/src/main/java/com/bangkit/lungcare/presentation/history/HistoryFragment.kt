package com.bangkit.lungcare.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.adapter.XrayAdapter
import com.bangkit.lungcare.adapter.XrayHistoryAdapter
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentHistoryBinding
import com.bangkit.lungcare.domain.model.xray.Xray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel by viewModels<HistoryViewModel>()

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterXray: XrayHistoryAdapter
    private val listXrayData = ArrayList<Xray>()

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

        adapterXray = XrayHistoryAdapter(listXrayData)

        binding.rvHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }

        binding.rvHistory.adapter = adapterXray

        viewModel.getAllXray().observe(viewLifecycleOwner) { result ->
            setXrayData(result)
        }

    }

    private fun setXrayData(result: Result<List<Xray>>) {
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
                adapterXray.setListXray(xrayData)
            }
        }
    }

//    private fun populatedData(xrayAdapter: XrayHistoryAdapter) {
//        viewModel.getAllXray().observe(viewLifecycleOwner) { result ->
//            when (result) {
//                is Result.Loading -> {
//                    binding.progressbar.visibility = View.VISIBLE
//                }
//
//                is Result.Error -> {
//                    binding.progressbar.visibility = View.GONE
//                }
//
//                is Result.Success -> {
//                    binding.progressbar.visibility = View.GONE
//
//                    val xrayData = result.data
//                    xrayAdapter.setListXray(xrayData)
//                }
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}