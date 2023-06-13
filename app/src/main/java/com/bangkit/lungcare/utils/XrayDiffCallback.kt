package com.bangkit.lungcare.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.lungcare.domain.model.xray.Xray

class XrayDiffCallback(
    private val oldXrayList: List<Xray>,
    private val newXrayList: List<Xray>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldXrayList.size

    override fun getNewListSize(): Int = newXrayList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldXrayList[oldItemPosition].id == newXrayList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldXrayList[oldItemPosition] == newXrayList[newItemPosition]
}