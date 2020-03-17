package com.kenilt.skeleton.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.managers.interfaces.IModel

/**
 * Created by thangnguyen on 2/21/19.
 */
abstract class GenericAdapter<V : ViewDataBinding, T : IModel>(context: Context) : BaseRecyclerAdapter<T>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<V> {
        val binding = DataBindingUtil.inflate<V>(LayoutInflater.from(context), getLayoutResourceId(viewType), parent, false)
        val holder = SimpleViewHolder(binding)
        setUpViewHolderAfterCreated(binding, holder)
        return holder
    }

    open fun setUpViewHolderAfterCreated(binding: V, holder: SimpleViewHolder<V>) {
        binding.root.setOnClickListener {
            itemListListener?.onItemClick(it, holder.adapterPosition)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        @Suppress("UNCHECKED_CAST")
        val viewHolder = holder as SimpleViewHolder<V>
        bindViewHolder(viewHolder, position)
    }

    abstract fun getLayoutResourceId(viewType: Int): Int

    abstract fun bindViewHolder(viewHolder: SimpleViewHolder<V>, position: Int)

}
