package com.bangkit.lungcare.utils

import androidx.recyclerview.widget.DiffUtil

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