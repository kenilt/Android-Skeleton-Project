package com.kenilt.skeleton.ui.custom.decoration

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.ui.base.adapter.BaseRecyclerAdapter

class GridListSpacingDecoration(
        context: Context,
        mainSpaceInDp: Float = 0f,
        subSpaceInDp: Float = 0f,
        includeMainEdge: Boolean = true,
        includeSubEdge: Boolean = true
): GridSpacingDecoration(context, mainSpaceInDp, subSpaceInDp, includeMainEdge, includeSubEdge) {

    override fun getHeaderCount(parent: RecyclerView): Int {
        return (parent.adapter as? BaseRecyclerAdapter<*>)?.getHeaderCount() ?: 0
    }

    override fun getFooterCount(parent: RecyclerView): Int {
        return 1
    }
}
