package com.bangkit.lungcare.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private val navHostController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupNavView()
    }

    private fun setupNavView() {
        binding?.navView?.setupWithNavController(navHostController)

        navHostController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.postXrayFragment -> {
                    binding?.navView?.visibility = View.GONE
                }

                R.id.cameraFragment -> {
                    binding?.navView?.visibility = View.GONE
                }

                R.id.detailXrayFragment -> {
                    binding?.navView?.visibility = View.GONE
                }

                else -> {
                    binding?.navView?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}