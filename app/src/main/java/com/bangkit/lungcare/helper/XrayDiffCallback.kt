package com.bangkit.lungcare.helper

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.lungcare.data.network.response.XrayItem

class XrayDiffCallback(
    private val oldXrayList: List<XrayItem>,
    private val newXrayList: List<XrayItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldXrayList.size

    override fun getNewListSize(): Int = newXrayList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldXrayList[oldItemPosition].gcsLink == newXrayList[newItemPosition].gcsLink


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldXrayList[oldItemPosition] == newXrayList[newItemPosition]

}