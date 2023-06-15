package com.bangkit.lungcare.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.R
import com.bangkit.lungcare.adapter.ArticleAdapter
import com.bangkit.lungcare.adapter.ArticlesAdapter
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

    private lateinit var adapterArticle: ArticleAdapter

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

        adapterArticle = ArticleAdapter()

        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }

        binding.rvArticle.adapter = adapterArticle

        observerToken()
    }

    private fun observerToken() {
        viewModel.getToken().observe(viewLifecycleOwner) { token ->
            Log.d(TAG, "getToken: $token")
            if (token == "") {
                moveToLogin()
            } else {
                setupDataProfile("Bearer $token")
                setupDataArticle("Bearer $token")
            }
        }
    }

    private fun setupDataProfile(token: String) {
        viewModel.getUserProfile(token)
        viewModel.userProfileResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
                }

                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    val profileData = result.data

                    binding.apply {
                        profileIv.setImageResource(R.drawable.account_circle)
                        headingNameTv.text =
                            getString(R.string.welcome_message, profileData.username)
                    }
                }
            }
        }
    }

    private fun setupDataArticle(token: String) {
        viewModel.getAllArticle(token, category = "Normal")
        viewModel.articleResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    binding.rvArticle.visibility = View.VISIBLE
                    result.data.let {
                        Log.d("Cek Data", "getArticle: $it")
                        adapterArticle.submitList(it)
                    }
                }

                is Result.Loading -> {
                    Log.d("Cek Loading", "iniLoading")
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    Log.d("Cek Error", "iniError: ${result.error}")
                    binding.progressbar.visibility = View.GONE
                }
            }
        }
    }

    private fun moveToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}