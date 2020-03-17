package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.extension.value

/**
 * Created by thangnguyen on 10/16/17.
 */

class EmptySupportRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var emptyView: View? = null
        set(value) {
            field = value
            checkIfEmpty()
        }
    var shouldCheckEmpty: Boolean = true

    private val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    internal fun checkIfEmpty() {
        if (shouldCheckEmpty && emptyView != null && adapter != null) {
            val emptyViewVisible = adapter?.itemCount.value == 0
            emptyView?.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)

        checkIfEmpty()
    }
}
