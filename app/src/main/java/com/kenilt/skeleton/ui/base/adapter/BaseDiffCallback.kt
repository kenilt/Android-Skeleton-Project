package com.kenilt.skeleton.ui.base.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kenilt.skeleton.managers.interfaces.IModel

/**
 * Created by thangnguyen on 1/27/19.
 */
class BaseDiffCallback<T: IModel>(private val newList: List<T>, private val oldList: List<T>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getStableId() == newList[newItemPosition].getStableId()
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
