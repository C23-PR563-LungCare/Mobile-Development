package com.bangkit.lungcare.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.ItemHistoryBinding
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.presentation.home.detail.DetailXrayResultActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class XrayAdapter() : ListAdapter<Xray, XrayAdapter.ListViewHolder>(DIFF_CALLBACK) {

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

                viewMoreBtn.setOnClickListener {
                    val intent =
                        Intent(itemView.context, DetailXrayResultActivity::class.java).apply {
                            putExtra(DetailXrayResultActivity.EXTRA_RESULT_ID, data.id)
                            putExtra(DetailXrayResultActivity.EXTRA_RESULT, data.processResult)
                        }
                    itemView.context.startActivity(intent)
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