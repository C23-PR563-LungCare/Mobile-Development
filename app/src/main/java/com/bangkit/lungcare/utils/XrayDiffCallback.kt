package com.bangkit.lungcare.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.lungcare.data.remote.response.XrayResponseItem

class XrayDiffCallback(
    private val oldXrayList: List<XrayResponseItem>,
    private val newXrayList: List<XrayResponseItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldXrayList.size

    override fun getNewListSize(): Int = newXrayList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldXrayList[oldItemPosition].gcsLink == newXrayList[newItemPosition].gcsLink


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldXrayList[oldItemPosition] == newXrayList[newItemPosition]

}