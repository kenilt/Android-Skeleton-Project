package com.kenilt.skeleton.ui.base.paged

import androidx.recyclerview.widget.DiffUtil
import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.model.common.HeaderPair

/**
 * Created by thangnguyen on 2019-08-16.
 */
class HeaderDiffCallback(var newList: List<HeaderPair>, var oldList: List<HeaderPair>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPack = oldList[oldItemPosition]
        val newPack = newList[newItemPosition]
        if (oldPack.layoutId != newPack.layoutId) return false
        if (oldPack.data is IModel && newPack.data is IModel) return oldPack.data.getStableId() == newPack.data.getStableId()
        return oldPack.data == newPack.data
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPack = oldList[oldItemPosition]
        val newPack = newList[newItemPosition]
        return oldPack.layoutId == newPack.layoutId && oldPack.data == newPack.data
    }
}
