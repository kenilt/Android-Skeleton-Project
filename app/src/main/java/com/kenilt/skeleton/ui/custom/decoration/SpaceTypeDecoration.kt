package com.kenilt.skeleton.ui.custom.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by thangnguyen on 2019-07-19.
 */
class SpaceTypeDecoration(
        context: Context,
        mainSpaceInDp: Float = 0f,
        subSpaceInDp: Float = 0f,
        includeEdge: Boolean = true,
        var typeLookup: TypeLookup? = null
) : SpaceDecoration(context, mainSpaceInDp, subSpaceInDp, includeEdge) {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val shouldApply = typeLookup?.shouldApply(position) ?: true
        if (shouldApply) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            outRect.setEmpty()
        }
    }

    override fun getHeaderCount(parent: RecyclerView): Int {
        return 0
    }

    override fun getFooterCount(parent: RecyclerView): Int {
        return 0
    }
}
