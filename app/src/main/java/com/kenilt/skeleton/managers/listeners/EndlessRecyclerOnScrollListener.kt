package com.kenilt.skeleton.managers.listeners

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by thangnguyen on 3/13/18.
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position before isLoading more.
    var visibleThreshold = 8
    var previousTotal = 0 // The total number of items in the dataset after the last load
    var isLoading = true // True if we are still waiting for the last set of data to load.
    var isEnded = false
    var isScrollFired = false
    private var currentPage = 1

    private val layoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: RecyclerView.LayoutManager) {
        this.layoutManager = layoutManager
    }

    constructor(layoutManager: RecyclerView.LayoutManager, startPage: Int): this(layoutManager) {
        this.currentPage = startPage
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        isScrollFired = true
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = getFirstVisiblePosition()

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }
        if (!isEnded && !isLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            // Do something
            currentPage++

            onLoadMore(currentPage, totalItemCount)

            isLoading = true
        }
    }

    fun getFirstVisiblePosition(): Int {
        if (layoutManager is LinearLayoutManager) {
            return layoutManager.findFirstVisibleItemPosition()
        } else if (layoutManager is GridLayoutManager) {
            return layoutManager.findFirstVisibleItemPosition()
        }
        return 0
    }

    fun reset() {
        isScrollFired = false
        previousTotal = 0
        isLoading = true
        isEnded = false
        currentPage = 1
    }

    abstract fun onLoadMore(page: Int, totalItemCount: Int)
}
