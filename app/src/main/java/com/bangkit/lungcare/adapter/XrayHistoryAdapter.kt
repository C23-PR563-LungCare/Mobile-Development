package com.bangkit.lungcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.lungcare.databinding.ItemHistoryBinding
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.utils.DateFormatter
import com.bangkit.lungcare.utils.XrayDiffCallback
import com.bumptech.glide.Glide
import java.util.TimeZone

class XrayHistoryAdapter(private val listXray: ArrayList<Xray>) :
    RecyclerView.Adapter<XrayHistoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listXray.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listXray[position]
        holder.bind(data)
    }

    inner class MyViewHolder(private var binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Xray) {
            binding.apply {
                dateTv.text = DateFormatter.formatData(data.date, TimeZone.getDefault().id)
                thumbnailTv.loadImage(data.gscLink)
                resultTv.text = data.processResult
            }
        }
    }

    fun setListXray(newListXray: List<Xray>) {
        val diffCallback = XrayDiffCallback(listXray, newListXray)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listXray.clear()
        listXray.addAll(newListXray)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context).load(url).into(this)
    }
}