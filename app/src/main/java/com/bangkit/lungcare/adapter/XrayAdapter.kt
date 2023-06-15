package com.bangkit.lungcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.lungcare.databinding.ItemHistoryBinding
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bumptech.glide.Glide

class XrayAdapter : ListAdapter<Xray, XrayAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val xray = getItem(position)
        holder.bind(xray)
    }

    inner class ListViewHolder(private var binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Xray) {
            binding.apply {
                thumbnailTv.loadImage(data.gscLink)
                outputTv.text = data.processResult
            }
        }

    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context).load(url).into(this)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Xray>() {
            override fun areItemsTheSame(
                oldItem: Xray,
                newItem: Xray
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Xray,
                newItem: Xray
            ): Boolean =
                oldItem == newItem

        }
    }
}