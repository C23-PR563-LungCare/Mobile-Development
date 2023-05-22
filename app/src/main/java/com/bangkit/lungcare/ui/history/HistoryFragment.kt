package com.bangkit.lungcare.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.data.network.Result
import com.bangkit.lungcare.data.network.response.XrayItem
import com.bangkit.lungcare.databinding.FragmentHistoryBinding
import com.bangkit.lungcare.ui.adapter.XrayAdapter


class HistoryFragment : Fragment() {

    private val historyViewModel by viewModels<HistoryViewModel>()

    private lateinit var adapter: XrayAdapter
    private val listXrayData = ArrayList<XrayItem>()

    private var _historyBinding: FragmentHistoryBinding? = null
    private val historyBinding get() = _historyBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _historyBinding = FragmentHistoryBinding.inflate(inflater, container, false)
        return historyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewData()

        historyViewModel.getHistoryXray()

        historyViewModel.result.observe(viewLifecycleOwner) { result ->
            setXrayHistoryData(result)
        }
    }

    private fun setRecyclerViewData() {
        adapter = XrayAdapter(listXrayData)

        with(historyBinding) {
            rvHistory.setHasFixedSize(true)
            rvHistory.layoutManager = LinearLayoutManager(requireActivity())
            rvHistory.adapter = adapter
        }

    }

    private fun setXrayHistoryData(result: Result<List<XrayItem>>) {
        when (result) {
            is Result.Loading -> {
                historyBinding.progressbar.visibility = View.VISIBLE
            }

            is Result.Success -> {
                historyBinding.progressbar.visibility = View.GONE

                val listXrayData = result.data
                adapter.setListXray(listXrayData)
            }

            is Result.Error -> {}
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _historyBinding = null
    }

    companion object {
        const val EXTRA_USER = "hellow"
    }
}