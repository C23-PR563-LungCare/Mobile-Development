package com.bangkit.lungcare.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.lungcare.databinding.ItemHistoryBinding
import com.bangkit.lungcare.utils.XrayDiffCallback
import com.bumptech.glide.Glide

class XrayAdapter(private val listDataXray: ArrayList<XrayItem>) :
    RecyclerView.Adapter<XrayAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listDataXray.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listDataXray[position]
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: XrayItem) {
            with(binding) {
                resultTv.text = data.processResult
                thumbnailTv.loadImage(data.gcsLink)
            }
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)

            .into(this)
    }

    fun setListXray(newListDataXray: List<XrayItem>) {
        val diffCallback = XrayDiffCallback(listDataXray, newListDataXray)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listDataXray.clear()
        listDataXray.addAll(newListDataXray)
        diffResult.dispatchUpdatesTo(this)
    }
}