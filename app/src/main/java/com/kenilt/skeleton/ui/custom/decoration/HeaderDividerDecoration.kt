package com.kenilt.skeleton.ui.custom.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.ui.base.paged.BasePagedAdapter
import com.kenilt.skeleton.utils.Utils

class HeaderDividerDecoration(
        var lookup: SpaceLookup
) : RecyclerView.ItemDecoration() {

    private val density = Utils.density

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        if (position < getHeaderCount(parent)) {
            outRect.top = (lookup.getTopSpace(position) * density).toInt()
            outRect.bottom = (lookup.getBottomSpace(position) * density).toInt()
            outRect.left = (lookup.getLeftSpace(position) * density).toInt()
            outRect.right = (lookup.getRightSpace(position) * density).toInt()
        }
    }

    fun getHeaderCount(parent: RecyclerView): Int {
        return (parent.adapter as? BasePagedAdapter<*>)?.getHeaderCount() ?: 0
    }

    interface SpaceLookup {
        fun getTopSpace(position: Int): Int { return 0 }
        fun getBottomSpace(position: Int): Int { return 0 }
        fun getLeftSpace(position: Int): Int { return 0 }
        fun getRightSpace(position: Int): Int { return 0 }
    }
}
