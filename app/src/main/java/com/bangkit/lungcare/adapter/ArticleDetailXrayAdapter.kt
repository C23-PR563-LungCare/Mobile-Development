package com.bangkit.lungcare.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.lungcare.databinding.ItemArticleDetailXrayBinding
import com.bangkit.lungcare.domain.model.article.Article
import com.bumptech.glide.Glide

class ArticleDetailXrayAdapter :
    ListAdapter<Article, ArticleDetailXrayAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            ItemArticleDetailXrayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemArticleDetailXrayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Article) {
            Log.d("ArticleDetail", "bind: $data")
            binding.apply {
                posterIv.loadImage(data.imageUrl)
                headlineTv.text = data.title
            }
        }

    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context).load(url).into(this)
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