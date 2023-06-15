package com.bangkit.lungcare.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.lungcare.domain.model.article.Article

class ArticleDiffCallback(
    private val oldArticleList: List<Article>,
    private val newArticleList: List<Article>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldArticleList.size

    override fun getNewListSize(): Int = newArticleList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldArticleList[oldItemPosition].newsId == newArticleList[newItemPosition].newsId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldArticleList[oldItemPosition] == newArticleList[newItemPosition]
}