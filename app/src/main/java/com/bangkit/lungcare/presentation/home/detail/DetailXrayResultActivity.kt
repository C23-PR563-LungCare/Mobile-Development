package com.bangkit.lungcare.presentation.home.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.adapter.ArticleDetailXrayAdapter
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.ActivityDetailXrayResultBinding
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import com.bangkit.lungcare.utils.DateFormatter
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.util.TimeZone

@AndroidEntryPoint
class DetailXrayResultActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailXrayResultViewModel>()

    private val binding: ActivityDetailXrayResultBinding by lazy {
        ActivityDetailXrayResultBinding.inflate(layoutInflater)
    }

    private lateinit var adapterArticleDetailXrayAdapter: ArticleDetailXrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observerToken()

        adapterArticleDetailXrayAdapter = ArticleDetailXrayAdapter()

        binding.rvArticle.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailXrayResultActivity)
            adapter = adapterArticleDetailXrayAdapter
        }
    }

    private fun observerToken() {
        viewModel.getToken().observe(this) { token ->
            Log.d(TAG, "getToken: $token")
            if (token.isEmpty()) {
                moveToLogin()
            } else {
                setupResult("Bearer $token")
            }
        }
    }

    private fun setupResult(token: String) {
        // for result xray
        val resultId = intent.getStringExtra(EXTRA_RESULT_ID)
        resultId?.let {
            viewModel.getResultXrayPrediction(token, it)
        }

        viewModel.resultXray.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
                }

                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    val detailData = result.data
                    populateData(detailData)
                }
            }
        }

        // for relate article
        val result = intent.getStringExtra(EXTRA_RESULT)
        result?.let { viewModel.getRelateArticle(token, it) }

        viewModel.resultArticle.observe(this) { response ->
            when (response) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
                }

                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    val data = response.data
                    adapterArticleDetailXrayAdapter.submitList(data)
                }
            }
        }

    }

    private fun populateData(detailData: Xray) {
        binding.apply {
            dateResultTv.text = DateFormatter.formatData(detailData.date, TimeZone.getDefault().id)
            outputPredictionTv.text = detailData.processResult
            Glide.with(this@DetailXrayResultActivity).load(detailData.gscLink).into(xrayIv)
        }
    }

    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "DetailXrayResult"
        const val EXTRA_RESULT_ID = "extra_result_id"
        const val EXTRA_RESULT = "extra_result"
    }
}