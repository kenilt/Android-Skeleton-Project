package com.kenilt.skeleton.ui.custom.decoration

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by thangnguyen on 1/22/19.
 */
class StaggeredSpaceDecoration(
        context: Context,
        mainSpaceInDp: Float = 0f,
        subSpaceInDp: Float = 0f,
        includeMainEdge: Boolean = true,
        includeSubEdge: Boolean = true
): GridSpacingDecoration(context, mainSpaceInDp, subSpaceInDp, includeMainEdge, includeSubEdge) {

    override fun getOrientation(parent: RecyclerView): Int {
        return (parent.layoutManager as? StaggeredGridLayoutManager)?.orientation
                ?: super.getOrientation(parent)
    }

    override fun getSpanCount(parent: RecyclerView): Int {
        return (parent.layoutManager as? StaggeredGridLayoutManager)?.spanCount ?: 1
    }
}
