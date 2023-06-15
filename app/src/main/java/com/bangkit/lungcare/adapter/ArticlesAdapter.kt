package com.bangkit.lungcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.lungcare.data.source.remote.response.ArticleItemResponse
import com.bangkit.lungcare.data.source.remote.response.ResponseArticleItem
import com.bangkit.lungcare.databinding.ItemArticleBinding
import com.bangkit.lungcare.utils.ArticleDiffCallback
import com.bumptech.glide.Glide

class ArticlesAdapter() :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    private val listArticle = ArrayList<ResponseArticleItem>()

    fun setListArticle(newListArticle: List<ResponseArticleItem>) {
        val diffUtil = ArticleDiffCallback(listArticle, newListArticle)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        listArticle.clear()
        listArticle.addAll(newListArticle)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listArticle.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listArticle[position]
        holder.bind(data)
    }


    inner class ViewHolder(private var binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ResponseArticleItem) {
            binding.apply {
                headlineTv.text = data.title
                categoryArticleTv.text = data.newsCategory
                posterIv.loadImage(data.imageURL)
            }
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this).load(url).into(this)
    }
}