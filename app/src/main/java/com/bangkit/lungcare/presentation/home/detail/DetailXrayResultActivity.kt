package com.bangkit.lungcare.presentation.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.lungcare.adapter.ArticleDetailAdapter
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.ActivityDetailXrayResultBinding
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.presentation.home.HomeFragment
import com.bangkit.lungcare.utils.DateFormatter
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.util.TimeZone

@AndroidEntryPoint
class DetailXrayResultActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailXrayResultViewModel>()

    private lateinit var binding: ActivityDetailXrayResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailXrayResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setupRelateArticle()
    }

    private fun setupRelateArticle() {
        val articleAdapter = ArticleDetailAdapter()

        binding.rvArticle.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailXrayResultActivity)
            adapter = articleAdapter
        }

        populatedArticle(articleAdapter)
    }

    private fun populatedArticle(adapter: ArticleDetailAdapter) {
        viewModel.articleResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
                }

                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    val articleData = result.data
                    adapter.submitList(articleData)
                }
            }
        }
    }

    private fun setupData() {
        val resultId = intent.getStringExtra(EXTRA_RESULT_ID)
        resultId?.run(viewModel::setResultId)

        viewModel.detailResultXray.observe(this) { result ->
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
    }

    private fun populateData(detailData: Xray) {
        with(binding) {
            dateResultTv.text = DateFormatter.formatData(detailData.date, TimeZone.getDefault().id)
            outputPredictionTv.text = detailData.processResult
            Glide.with(this@DetailXrayResultActivity).load(detailData.gscLink).into(xrayIv)
        }

        val homeFragment = HomeFragment()
        val bundle = Bundle()
        bundle.putString(HomeFragment.EXTRA_PREDICTION, detailData.processResult)

        homeFragment.arguments = bundle
    }

    companion object {
        const val EXTRA_RESULT_ID = "extra_result_id"
    }
}