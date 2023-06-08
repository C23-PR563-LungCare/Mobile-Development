package com.bangkit.lungcare.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.lungcare.data.source.remote.response.XrayItemResponse

class XrayDiffCallback(
    private val oldXrayList: List<XrayItemResponse>,
    private val newXrayList: List<XrayItemResponse>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldXrayList.size

    override fun getNewListSize(): Int = newXrayList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldXrayList[oldItemPosition].gcsLink == newXrayList[newItemPosition].gcsLink


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldXrayList[oldItemPosition] == newXrayList[newItemPosition]

}