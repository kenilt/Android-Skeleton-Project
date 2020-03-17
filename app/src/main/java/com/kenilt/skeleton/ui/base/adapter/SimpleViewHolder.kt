package com.kenilt.skeleton.ui.base.adapter

import androidx.databinding.ViewDataBinding
import com.kenilt.skeleton.managers.listeners.ItemListClickListener

open class SimpleViewHolder<V : ViewDataBinding>(val binding: V) : BaseViewHolder(binding.root) {

    constructor(binding: V, clickProvide: () -> ItemListClickListener?): this(binding) {
        binding.root.setOnClickListener {
            clickProvide()?.onItemClick(it, adapterPosition)
        }
    }
}
