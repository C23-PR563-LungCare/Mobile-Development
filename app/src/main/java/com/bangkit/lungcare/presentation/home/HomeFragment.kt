package com.bangkit.lungcare.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.R
import com.bangkit.lungcare.adapter.ArticleAdapter
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentHomeBinding
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import com.bangkit.lungcare.presentation.home.article.ArticleDetailActivity
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

        observerToken()

        binding.itemHome.toPostXrayBtn.setOnClickListener {
            val intent = Intent(requireActivity(), PostXrayActivity::class.java)
            startActivity(intent)
        }

        adapterArticle = ArticleAdapter { article ->
            val intent = Intent(activity, ArticleDetailActivity::class.java).apply {
                putExtra(ArticleDetailActivity.EXTRA_DATA_ARTICLE, article)
            }
            startActivity(intent)
        }

        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context)
        }

        binding.rvArticle.adapter = adapterArticle
    }

    private fun observerToken() {
        viewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (token.isEmpty()) {
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
                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    val profileData = result.data

                    binding.apply {
                        profileIv.setImageResource(R.drawable.ic_profile)
                        headingNameTv.text =
                            getString(R.string.welcome_message, profileData.username)
                    }
                }

                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
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
                        adapterArticle.submitList(it)
                    }
                }

                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Error -> {
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
}