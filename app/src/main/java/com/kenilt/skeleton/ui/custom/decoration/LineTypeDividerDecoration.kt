package com.kenilt.skeleton.ui.custom.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.R

/**
 * Created by thangnguyen on 2019-07-19.
 */
class LineTypeDividerDecoration(
        context: Context,
        colorRestId: Int = R.color.color_gallery,
        heightInDp: Float = 1f,
        leftInDp: Float = 0f,
        rightInDp: Float = 0f,
        private var typeLineLookup: TypeLookup? = null
): LineDividerDecoration(context, colorRestId, heightInDp, leftInDp, rightInDp) {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val shouldApply = typeLineLookup?.shouldApply(position) ?: true
        if (shouldApply) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    override fun drawLine(canvas: Canvas, parent: RecyclerView, child: View, left: Float, right: Float) {
        val position = (child.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        val shouldApply = typeLineLookup?.shouldApply(position) ?: true
        if (shouldApply) {
            super.drawLine(canvas, parent, child, left, right)
        }
    }

    override fun getHeaderCount(parent: RecyclerView): Int {
        return 0
    }

    override fun getFooterCount(parent: RecyclerView): Int {
        return 0
    }
}
