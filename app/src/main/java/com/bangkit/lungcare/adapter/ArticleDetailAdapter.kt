package com.bangkit.lungcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.lungcare.databinding.ItemArticleBinding
import com.bangkit.lungcare.domain.model.article.Article
import com.bumptech.glide.Glide

class ArticleDetailAdapter() :
    ListAdapter<Article, ArticleDetailAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class MyViewHolder(private var binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Article) {
            binding.apply {
                headlineTv.text = data.title
                categoryArticleTv.text = data.newsCategory
                posterIv.loadImage(data.imageUrl)
            }
        }

    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this).load(url).into(this)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.newsId == newItem.newsId

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}