package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView

/**
 * Created by neal on 5/31/17.
 */

class ScrollViewExtension @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    var scrollViewListener: ScrollViewListener? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (scrollViewListener != null) {
            scrollViewListener!!.onScrollChanged(this, l, t, oldl, oldt)
        }
    }
}
