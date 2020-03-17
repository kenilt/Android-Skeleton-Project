package com.kenilt.skeleton.extension

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by thangnguyen on 2019-07-12.
 * https://stackoverflow.com/a/53986874/2347872
 */
fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
    val smoothScroller = object: LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int {
            return snapMode
        }

        override fun getHorizontalSnapPreference(): Int {
            return snapMode
        }
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}

fun RecyclerView.smoothSnapToTopIfNeeded() {
    if (getFirstVisiblePosition() == 0) {
        smoothSnapToPosition(0)
    }
}

fun RecyclerView.getFirstVisiblePosition(): Int {
    val layoutManager = layoutManager
    if (layoutManager is LinearLayoutManager) {
        return layoutManager.findFirstVisibleItemPosition()
    } else if (layoutManager is GridLayoutManager) {
        return layoutManager.findFirstVisibleItemPosition()
    }
    return 0
}

fun RecyclerView.registerScrollTopWhenInsertTop() {
    val recyclerView = this
    this.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0 && recyclerView.getFirstVisiblePosition() == 0) {
                recyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    })
}

fun RecyclerView.smoothAnchorScrollToTop() {
    if (getFirstVisiblePosition() > 20) {
        scrollToPosition(20)
    }
    smoothScrollToPosition(0)
}
