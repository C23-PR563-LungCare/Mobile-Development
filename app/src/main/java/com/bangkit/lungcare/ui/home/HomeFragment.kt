package com.bangkit.lungcare.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _homeBinding: FragmentHomeBinding? = null

    private val homeBinding get() = _homeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        homeBinding.itemHome.toPostXrayBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_home_to_postXrayActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _homeBinding = null
    }
}