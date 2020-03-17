package com.kenilt.skeleton.ui.custom.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.ui.base.paged.BasePagedAdapter
import com.kenilt.skeleton.utils.Utils

/**
 * Created by thangnguyen on 2019-07-23.
 */
open class GridSpacingDecoration(
        context: Context,
        mainSpaceInDp: Float = 0f,
        subSpaceInDp: Float = 0f,
        var includeMainEdge: Boolean = true,
        var includeSubEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    var mainSpace: Int = Utils.convertDpToPx(context, mainSpaceInDp)
    var subSpace: Int = Utils.convertDpToPx(context, subSpaceInDp)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val afterPos = position - getHeaderCount(parent)
        if (afterPos >= 0 && position < state.itemCount - getFooterCount(parent)) {
            val spanCount = getSpanCount(parent)
            val column = afterPos % spanCount // item column
            if (getOrientation(parent) == LinearLayout.VERTICAL) {
                if (includeMainEdge) {
                    if (afterPos < spanCount) { // top edge
                        outRect.top = mainSpace
                    }
                    outRect.bottom = mainSpace // item bottom
                } else {
                    if (afterPos >= spanCount) {
                        outRect.top = mainSpace // item top
                    }
                }
                if (includeSubEdge) {
                    outRect.left = subSpace - column * subSpace / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * subSpace / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                } else {
                    outRect.left = column * subSpace / spanCount // column * ((1f / spanCount) * spacing)
                    outRect.right = subSpace - (column + 1) * subSpace / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                }
            } else {
                if (includeMainEdge) {
                    if (afterPos < spanCount) { // top edge
                        outRect.left = mainSpace
                    }
                    outRect.right = mainSpace // item bottom
                } else {
                    if (afterPos >= spanCount) {
                        outRect.left = mainSpace // item top
                    }
                }
                if (includeSubEdge) {
                    outRect.top = subSpace - column * subSpace / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.bottom = (column + 1) * subSpace / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                } else {
                    outRect.top = column * subSpace / spanCount // column * ((1f / spanCount) * spacing)
                    outRect.bottom = subSpace - (column + 1) * subSpace / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                }
            }
        }
    }

    open fun getOrientation(parent: RecyclerView): Int {
        return (parent.layoutManager as? LinearLayoutManager)?.orientation ?: LinearLayout.VERTICAL
    }

    open fun getSpanCount(parent: RecyclerView): Int {
        val gridLayoutManager = parent.layoutManager as? GridLayoutManager
        return gridLayoutManager?.spanCount ?: 1
    }

    open fun getHeaderCount(parent: RecyclerView): Int {
        return (parent.adapter as? BasePagedAdapter<*>)?.getHeaderCount() ?: 0
    }

    open fun getFooterCount(parent: RecyclerView): Int {
        return (parent.adapter as? BasePagedAdapter<*>)?.getFooterCount() ?: 0
    }

    @Suppress("unused")
    fun isLastItem(parent: RecyclerView, view: View, state: RecyclerView.State): Boolean {
        val spanCount = getSpanCount(parent)
        val itemCount = state.itemCount
        val mod = itemCount % spanCount
        val lastItemCount = if (mod > 0) mod else spanCount
        return parent.getChildAdapterPosition(view) >= itemCount - lastItemCount
    }
}
