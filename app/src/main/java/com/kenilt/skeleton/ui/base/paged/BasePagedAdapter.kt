package com.kenilt.skeleton.ui.base.paged

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.R
import com.kenilt.skeleton.databinding.*
import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.managers.listeners.ItemListClickListener
import com.kenilt.skeleton.model.common.HeaderPair
import com.kenilt.skeleton.model.vo.NetworkState
import com.kenilt.skeleton.model.vo.ResourceStatus
import com.kenilt.skeleton.ui.base.adapter.BaseViewHolder

/**
 * Created by thangnguyen on 2019-07-01.
 */
abstract class BasePagedAdapter<T: IModel>(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mDiffer: AsyncPagedListDiffer<T>

    protected val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    private var headerPairs: MutableList<HeaderPair> = ArrayList()
    var itemListListener: ItemListClickListener? = null
    var onRetryListener: View.OnClickListener? = null
    var networkState: NetworkState? = null
        set(value) {
            if (field != value) {
                field = value
                notifyFooterChange()
            }
        }
    var headerNetworkState: NetworkState? = null
        set(value) {
            if (field != value) {
                field = value
                notifyFooterChange()
            }
        }
    private var isEmptyHeaderList = false
        set(value) {
            if (field != value) {
                field = value
                notifyFooterChange()
            }
        }
    private var isEmptyPagedList = false
        set(value) {
            if (field != value) {
                field = value
                notifyFooterChange()
            }
        }

    init {
        mDiffer = AsyncPagedListDiffer(object : ListUpdateCallback {
            fun offsetPosition(position: Int): Int {
                return position + getHeaderCount()
            }

            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeInserted(offsetPosition(position), count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(offsetPosition(position), count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(offsetPosition(fromPosition), offsetPosition(toPosition))
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {
                notifyItemRangeChanged(offsetPosition(position), count, payload)
            }
        }, AsyncDifferConfig.Builder(BaseItemDiffCallback<T>()).build())
    }

    val currentList: PagedList<T>?
        get() = mDiffer.currentList

    override fun getItemViewType(position: Int): Int {
        when {
            position >= 0 && position < headerPairs.size -> return headerPairs[position].layoutId
            position >= itemCount - getFooterCount() -> {
                val networkState = networkState
                if (networkState != null) {
                    if (networkState.status == ResourceStatus.LOADING || headerNetworkState?.status == ResourceStatus.LOADING) {
                        return R.layout.item_view_loading
                    }
                    if (networkState.status == ResourceStatus.ERROR) {
                        return R.layout.item_view_error_occurred
                    }
                    if (networkState.status == ResourceStatus.EXCEPTION) {
                        return R.layout.item_view_no_network
                    }
                    if (networkState.status == ResourceStatus.SUCCESS && isEmptyContent()) {
                        return R.layout.item_view_no_item
                    }
                }
                return R.layout.item_view_stub
            }
            else -> return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_view_no_network -> {
                val binding = DataBindingUtil.inflate<ItemViewNoNetworkBinding>(inflater,
                        R.layout.item_view_no_network, parent, false)
                binding.onRetryListener = View.OnClickListener { onRetryListener?.onClick(binding.itemButtonRetry) }
                BaseViewHolder(binding.root)
            }
            R.layout.item_view_error_occurred -> {
                val binding = DataBindingUtil.inflate<ItemViewErrorOccurredBinding>(inflater,
                        R.layout.item_view_error_occurred, parent, false)
                binding.onRetryListener = View.OnClickListener { onRetryListener?.onClick(binding.itemButtonRetry) }
                BaseViewHolder(binding.root)
            }
            R.layout.item_view_no_item -> {
                val binding = DataBindingUtil.inflate<ItemViewNoItemBinding>(inflater,
                        R.layout.item_view_no_item, parent, false)
                BaseViewHolder(binding.root)
            }
            R.layout.item_view_loading -> {
                val binding = DataBindingUtil.inflate<ItemViewLoadingBinding>(inflater,
                        R.layout.item_view_loading, parent, false)
                BaseViewHolder(binding.root)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemViewStubBinding>(inflater,
                        R.layout.item_view_stub, parent, false)
                BaseViewHolder(binding.root)
            }
        }
    }

    override fun getItemCount(): Int {
        return getHeaderCount() + mDiffer.itemCount + getFooterCount()
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return item?.getStableId() ?: position.toLong()
    }

    fun getItem(position: Int): T? {
        try {
            val pos = position - getHeaderCount()
            if (pos >= 0) {
                return mDiffer.getItem(pos)
            }
        } catch (exception: Exception) {}
        return null
    }

    open fun getHeaderCount(): Int {
        return headerPairs.size
    }

    fun getFooterCount(): Int {
        return 1
    }

    fun submitHeaderList(headerList: List<HeaderPair>?) {
        val newList = headerList ?: emptyList()
        isEmptyHeaderList = newList.isEmpty()
        val diffResult = DiffUtil.calculateDiff(HeaderDiffCallback(newList, this.headerPairs))
        this.headerPairs.clear()
        this.headerPairs.addAll(newList.map { it.copy() })
        diffResult.dispatchUpdatesTo(this)
    }

    fun submitList(pagedList: PagedList<T>?) {
        isEmptyPagedList = pagedList?.isEmpty() == true
        mDiffer.submitList(pagedList)
    }

    fun findHeaderDataByLayoutId(layoutId: Int): Any? {
        return headerPairs.find { it.layoutId == layoutId }?.data
    }

    fun getHeaderDataByPosition(position: Int): Any? {
        return headerPairs.getOrNull(position)?.data
    }

    private fun isEmptyContent(): Boolean {
        return mDiffer.itemCount + getHeaderCount() == 0
    }

    private fun notifyFooterChange() {
        notifyItemChanged(itemCount - 1)
    }
}
