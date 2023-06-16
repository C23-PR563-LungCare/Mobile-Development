package com.bangkit.lungcare.presentation.home.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.adapter.RelateArticleAdapter
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.ActivityDetailXrayResultBinding
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import com.bangkit.lungcare.presentation.home.article.ArticleDetailActivity
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

    private lateinit var adapterArticle: RelateArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observerToken()

        adapterArticle = RelateArticleAdapter { article ->
            val intent = Intent(this, ArticleDetailActivity::class.java).apply {
                putExtra(ArticleDetailActivity.EXTRA_DATA_ARTICLE, article)
            }
            startActivity(intent)
        }

        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(this@DetailXrayResultActivity)
            adapter = adapterArticle
        }
    }

    private fun observerToken() {
        viewModel.getToken().observe(this) { token ->
            if (token.isEmpty()) {
                moveToLogin()
            } else {
                setupResultXray("Bearer $token")
                setupRelateArticle("Bearer $token")
            }
        }
    }

    private fun setupResultXray(token: String) {
        val resultId = intent.getStringExtra(EXTRA_RESULT_ID)
        resultId?.let {
            viewModel.getResultXrayPrediction(token, it)
        }

        viewModel.xrayResult.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    val detailData = result.data
                    populatedData(detailData)
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

    private fun populatedData(detailData: Xray) {
        with(binding) {
            dateResultTv.text =
                DateFormatter.formatData(detailData.date, TimeZone.getDefault().id)
            outputPredictionTv.text = detailData.processResult
            Glide.with(this@DetailXrayResultActivity).load(detailData.gscLink)
                .into(xrayIv)
        }
    }

    private fun setupRelateArticle(token: String) {
        val resultCategory = intent.getStringExtra(EXTRA_RESULT)
        resultCategory?.let { viewModel.getRelateArticle(token, it) }

        viewModel.articleResult.observe(this) { result ->
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
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    companion object {
        const val EXTRA_RESULT_ID = "extra_result_id"
        const val EXTRA_RESULT = "extra_result"
    }
}