package com.bangkit.lungcare.presentation.home.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.bangkit.lungcare.databinding.ActivityArticleDetailBinding
import com.bangkit.lungcare.domain.model.article.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {

    private val viewModel by viewModels<ArticleDetailViewModel>()

    private val binding: ActivityArticleDetailBinding by lazy {
        ActivityArticleDetailBinding.inflate(layoutInflater)
    }

    private lateinit var articleDetail: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        articleDetail = intent.getParcelableExtra<Article>(EXTRA_DATA_ARTICLE) as Article

        supportActionBar?.title = articleDetail.title
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(articleDetail.newsUrl.toString())

        viewModel.setArticleData(articleDetail)

    }

    companion object {
        const val EXTRA_DATA_ARTICLE = "extra_data_article"
    }
}