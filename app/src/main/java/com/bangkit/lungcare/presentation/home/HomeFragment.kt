package com.bangkit.lungcare.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.adapter.ArticleAdapter
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentHomeBinding
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var category: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemHome.toPostXrayBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToPostXrayFragment())
        }

        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        val articleAdapter = ArticleAdapter()

        binding.rvArticles.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = articleAdapter
        }

        populatedData(articleAdapter)
    }

    private fun populatedData(articleAdapter: ArticleAdapter) {
        viewModel.articleResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Error -> {

                }

                is Result.Success -> {
                    val articleData = result.data
                    articleAdapter.submitList(articleData)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}