package com.kenilt.skeleton.ui.base.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.managers.listeners.ItemListClickListener


/**
 * Created by thangnguyen on 3/14/18.
 */
abstract class BaseRecyclerAdapter<T : IModel> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    companion object {
        const val VIEW_TYPE_HEADER_VIEW = Integer.MIN_VALUE + 1
        const val VIEW_TYPE_FOOTER_VIEW = Integer.MIN_VALUE
        const val VIEW_TYPE_DEFAULT = 0
    }

    var footerViews: ArrayList<View> = ArrayList()
    open var dataList: MutableList<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var context: Context
    var itemListListener: ItemListClickListener? = null

    constructor(context: Context) {
        this.context = context
    }

    constructor(context: Context, dataList: MutableList<T>) : this(context) {
        this.dataList = dataList
    }

    override fun getItemCount(): Int {
        return getHeaderCount() + dataList.size + getFooterCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < getHeaderCount()) {
            VIEW_TYPE_HEADER_VIEW
        } else {
            val afterPos = getPosAfterHeader(position)
            if (afterPos < dataList.size) {
                VIEW_TYPE_DEFAULT
            } else {
                VIEW_TYPE_FOOTER_VIEW
            }
        }
    }

    open fun getItem(position: Int): T? {
        return if (position < getHeaderCount()) {
            null
        } else {
            val afterPos = getPosAfterHeader(position)
            if (afterPos >= 0 && afterPos < dataList.size) {
                dataList[afterPos]
            } else null
        }
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        item?.let {
            return item.getStableId()
        }
        val footerPos = position - getHeaderCount() - dataList.size
        if (footerPos >= 0 && footerPos < footerViews.size) {
            return footerViews[footerPos].hashCode().toLong()
        }
        return position.toLong()
    }

    open fun getHeaderCount(): Int {
        return 0
    }

    protected fun getFooterCount(): Int {
        return footerViews.size
    }

    protected fun getPosAfterHeader(position: Int): Int {
        return position - getHeaderCount()
    }

    fun isEmptyContent(): Boolean {
        return dataList.size == 0
    }

    fun setFooterView(footerView: View) {
        val insertPosition = itemCount
        footerViews.clear()
        footerViews.add(footerView)
        notifyItemInserted(insertPosition)
    }

    fun clearFooters() {
        val footerSize = footerViews.size
        val startRemovePos = itemCount - footerSize
        footerViews.clear()
        notifyItemRangeRemoved(startRemovePos, footerSize)
    }

    open fun appendDataList(dataList: MutableList<T>?) {
        if (dataList != null && dataList.size > 0) {
            val startPos = this.dataList.size
            val length = dataList.size
            this.dataList.addAll(dataList)

            notifyItemRangeChanged(startPos, length)
        }
    }

    fun clearDataList() {
        this.dataList.clear()

        notifyDataSetChanged()
    }

    fun updateDataList(newList: List<T>) {
        val diffResult = DiffUtil.calculateDiff(BaseDiffCallback(newList, this.dataList))
        this.dataList.clear()
        this.dataList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateItemAtPosition(itemData: T?, position: Int) {
        itemData?.let {
            val afterPos = getPosAfterHeader(position)
            if (afterPos >= 0 && afterPos < dataList.size) {
                dataList[afterPos] = itemData

                notifyItemChanged(position)
            }
        }
    }

    fun removeItem(position: Int) {
        val afterPos = getPosAfterHeader(position)
        if (afterPos >= 0 && afterPos < dataList.size) {
            dataList.removeAt(position)

            // Notice:
            // Need to change position in view data biding before change to notifyItemRemoved()
            notifyDataSetChanged()
        }
    }

    fun removeItem(itemData: T?) {
        if (itemData != null) {
            dataList.remove(itemData)

            // Notice:
            // Need to change position in view data biding before change to notifyItemRemoved()
            notifyDataSetChanged()
        }
    }
}
