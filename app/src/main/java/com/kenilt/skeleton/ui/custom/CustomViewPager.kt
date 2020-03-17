package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by thanh.nguyen on 10/24/2016.
 */

class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    var swipeEnabled: Boolean = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.swipeEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return this.swipeEnabled && super.onInterceptTouchEvent(event)
    }
}
