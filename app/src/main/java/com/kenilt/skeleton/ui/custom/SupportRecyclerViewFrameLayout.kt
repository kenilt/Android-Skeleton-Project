package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Kenilt Nguyen on 8/7/18.
 */
class SupportRecyclerViewFrameLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var mTarget: RecyclerView? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        ensureTarget()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        ensureTarget()
    }

    private fun ensureTarget() {
        if (mTarget != null)
            return
        if (childCount > 0) {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child is RecyclerView)
                    mTarget = child
            }
        }
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return if (mTarget == null) {
            super.canScrollVertically(direction)
        } else {
            mTarget?.canScrollVertically(direction) ?: false
        }
    }
}
