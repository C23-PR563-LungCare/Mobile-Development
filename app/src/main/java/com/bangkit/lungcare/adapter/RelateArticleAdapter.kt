package com.bangkit.lungcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.ItemRelateArticleBinding
import com.bangkit.lungcare.domain.model.article.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RelateArticleAdapter(private val onItemClick: (Article) -> Unit) :
    ListAdapter<Article, RelateArticleAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        ItemRelateArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ListViewHolder(private var binding: ItemRelateArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Article) {
            binding.apply {
                headlineTv.text = data.title
                categoryArticleTv.text = data.newsCategory
                posterIv.loadImage(data.imageUrl)

                itemView.setOnClickListener {
                    onItemClick(data)
                }
            }
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context).load(url).apply(
            RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
        ).into(this)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean =
                oldItem.newsId == newItem.newsId

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean =
                oldItem == newItem
        }
    }
}