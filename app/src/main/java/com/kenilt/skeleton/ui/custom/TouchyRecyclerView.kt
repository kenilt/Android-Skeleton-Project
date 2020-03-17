package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by thangnguyen on 3/11/19.
 */
class TouchyRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    var onOutsideItemClick: OnOutsideItemClick? = null
    private val gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }
    })

    interface OnOutsideItemClick {
        fun onOutsideClick()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        // The findChildViewUnder() method returns null if the touch event
        // occurs outside of a child View.
        // Change the MotionEvent action as needed. Here we use ACTION_DOWN
        // as a simple, naive indication of a click.
        if (gestureDetector.onTouchEvent(event) && findChildViewUnder(event.x, event.y) == null) {
            onOutsideItemClick?.onOutsideClick()
        }
        return super.dispatchTouchEvent(event)
    }
}
