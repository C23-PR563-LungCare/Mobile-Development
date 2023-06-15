package com.bangkit.lungcare.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.lungcare.data.source.remote.response.ArticleItemResponse
import com.bangkit.lungcare.data.source.remote.response.ResponseArticleItem

class ArticleDiffCallback(
    private val oldArticleList: List<ResponseArticleItem>,
    private val newArticleList: List<ResponseArticleItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldArticleList.size

    override fun getNewListSize(): Int = newArticleList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldArticleList[oldItemPosition].newsID == newArticleList[newItemPosition].newsID

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldArticleList[oldItemPosition] == newArticleList[newItemPosition]
}