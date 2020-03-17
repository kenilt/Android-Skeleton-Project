package com.kenilt.skeleton.utils

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

/**
 * Created by neal on 2/17/17.
 */

object ViewUtil {

    fun setSize(view: View, width: Int, height: Int) {
        val layoutParams = view.layoutParams
        layoutParams.height = height
        layoutParams.width = width
        view.layoutParams = layoutParams
    }

    fun setHeight(view: View, height: Int) {
        val layoutParams = view.layoutParams
        layoutParams.height = height
        view.layoutParams = layoutParams
    }

    fun setWidth(view: View, width: Int) {
        val layoutParams = view.layoutParams
        layoutParams.width = width
        view.layoutParams = layoutParams
    }

    fun goneViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.GONE
        }
    }

    fun visibleViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    fun invisibleViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.INVISIBLE
        }
    }

    fun getRectView(view: View): Rect {
        val rect = Rect()
        view.getGlobalVisibleRect(rect)
        return rect
    }

    fun getYPosition(view: View): Int {
        val rect = getRectView(view)
        return rect.top
    }

    fun applyChangeOpacityOnClick(vararg views: View) {
        val onTouchListener = View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.alpha = 0.5f
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.alpha = 1f
                }
            }
            false
        }
        for (view in views) {
            view.setOnTouchListener(onTouchListener)
        }
    }
}
