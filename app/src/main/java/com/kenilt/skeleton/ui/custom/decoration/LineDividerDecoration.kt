package com.kenilt.skeleton.ui.custom.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kenilt.skeleton.R
import com.kenilt.skeleton.ui.base.paged.BasePagedAdapter
import com.kenilt.skeleton.utils.Utils


/**
 * Created by thangnguyen on 2019-07-16.
 */
open class LineDividerDecoration(
        context: Context,
        // color of line
        colorRestId: Int = R.color.color_gallery,
        // height of line
        heightInDp: Float = 1f,
        // left margin
        leftInDp: Float = 0f,
        // right margin
        rightInDp: Float = 0f
) : RecyclerView.ItemDecoration() {

    private val bounds = Rect()
    private val paint = Paint().apply { style = Paint.Style.FILL }
    private var height: Int = 0
    private var marginLeft: Int = 0
    private var marginRight: Int = 0

    init {
        paint.color = ContextCompat.getColor(context, colorRestId)
        height = Utils.convertDpToPx(context, heightInDp)
        marginLeft = Utils.convertDpToPx(context, leftInDp)
        marginRight = Utils.convertDpToPx(context, rightInDp)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        // hide the divider for the last child
        if (position < getHeaderCount(parent) || position >= state.itemCount - getFooterCount(parent)) {
            outRect.set(0, 0, 0, 0)
            return
        }
        outRect.set(0, 0, 0, height)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) {
            return
        }
        drawVertical(canvas, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft + marginLeft
            right = parent.width - (parent.paddingRight + marginRight)
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = marginLeft
            right = parent.width - marginRight
        }

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            drawLine(canvas, parent, child, left.toFloat(), right.toFloat())
        }
        canvas.restore()
    }

    open fun drawLine(canvas: Canvas, parent: RecyclerView, child: View, left: Float, right: Float) {
        parent.getDecoratedBoundsWithMargins(child, bounds)
        val bottom = bounds.bottom + child.translationY
        val top = bottom - height
        canvas.drawRect(left, top, right, bottom, paint)
    }

    open fun getHeaderCount(parent: RecyclerView): Int {
        return (parent.adapter as? BasePagedAdapter<*>)?.getHeaderCount() ?: 0
    }

    open fun getFooterCount(parent: RecyclerView): Int {
        return (parent.adapter as? BasePagedAdapter<*>)?.getFooterCount() ?: 0
    }
}
