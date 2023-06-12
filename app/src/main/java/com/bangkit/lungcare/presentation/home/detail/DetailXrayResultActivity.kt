package com.bangkit.lungcare.presentation.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.ActivityDetailXrayResultBinding
import com.bangkit.lungcare.domain.model.xray.Xray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailXrayResultActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailXrayResultViewModel>()

    private lateinit var binding: ActivityDetailXrayResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailXrayResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
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
                    val detailData = result.data
                    // populateDetail(detailData)
                }
            }
        }
    }

    private fun populateDetail(detailData: List<Xray>) {
        with(binding) {
            // outputPredictionTv.text =
        }
    }

    companion object {
        const val EXTRA_RESULT_ID = "extra_result_id"
    }
}