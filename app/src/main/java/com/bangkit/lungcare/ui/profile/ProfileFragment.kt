package com.bangkit.lungcare.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _profileBinding: FragmentProfileBinding? = null
    private val profileBinding get() = _profileBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return profileBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _profileBinding = null
    }
}