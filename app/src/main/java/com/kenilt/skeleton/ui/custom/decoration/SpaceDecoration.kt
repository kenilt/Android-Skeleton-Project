package com.kenilt.skeleton.ui.custom.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.ui.base.paged.BasePagedAdapter
import com.kenilt.skeleton.utils.Utils


/**
 * Created by thangnguyen on 2019-07-19.
 */
open class SpaceDecoration(
        context: Context,
        // space between 2 rows
        mainSpaceInDp: Float = 0f,
        // space from left side to item, or item to right side
        subSpaceInDp: Float = 0f,
        // space from top to first row, or last row to bottom
        private var includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    private var mainSpace: Int = Utils.convertDpToPx(context, mainSpaceInDp)
    private var subSpace: Int = Utils.convertDpToPx(context, subSpaceInDp)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val afterPos = position - getHeaderCount(parent)
        val lastPosition = state.itemCount - getFooterCount(parent) - 1
        if (afterPos >= 0 && position <= lastPosition) {
            if (getOrientation(parent) == LinearLayout.VERTICAL) {
                if (includeEdge) {
                    if (afterPos == 0) {
                        outRect.top = mainSpace
                    } else {
                        outRect.top = 0
                    }
                    outRect.bottom = mainSpace
                } else {
                    if (position == lastPosition) {
                        outRect.bottom = 0
                    } else {
                        outRect.bottom = mainSpace
                    }
                }
                outRect.left = subSpace
                outRect.right = subSpace
            } else {
                if (includeEdge) {
                    if (afterPos == 0) {
                        outRect.left = mainSpace
                    } else {
                        outRect.left = 0
                    }
                    outRect.right = mainSpace
                } else {
                    if (position == lastPosition) {
                        outRect.right = 0
                    } else {
                        outRect.right = mainSpace
                    }
                }
                outRect.top = subSpace
                outRect.bottom = subSpace
            }
        }
    }

    open fun getOrientation(parent: RecyclerView): Int {
        return (parent.layoutManager as? LinearLayoutManager)?.orientation ?: LinearLayout.VERTICAL
    }

    open fun getHeaderCount(parent: RecyclerView): Int {
        return (parent.adapter as? BasePagedAdapter<*>)?.getHeaderCount() ?: 0
    }

    open fun getFooterCount(parent: RecyclerView): Int {
        return (parent.adapter as? BasePagedAdapter<*>)?.getFooterCount() ?: 0
    }
}
