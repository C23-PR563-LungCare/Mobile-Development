package com.bangkit.lungcare.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

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

    override fun onDestroy() {
        super.onDestroy()
        _historyBinding = null
    }


}