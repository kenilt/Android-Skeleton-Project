package com.kenilt.skeleton.ui.base.paged

import androidx.recyclerview.widget.DiffUtil
import com.kenilt.skeleton.managers.interfaces.IModel

/**
 * Created by thangnguyen on 2019-07-01.
 */
class BaseItemDiffCallback<T: IModel>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getStableId() == newItem.getStableId()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.equals(newItem)
    }
}
