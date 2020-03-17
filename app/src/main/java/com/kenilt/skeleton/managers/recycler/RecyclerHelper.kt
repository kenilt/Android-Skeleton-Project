package com.kenilt.skeleton.managers.recycler

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baoyz.widget.PullRefreshLayout
import com.kenilt.skeleton.extension.value
import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.managers.listeners.EndlessRecyclerOnScrollListener
import com.kenilt.skeleton.ui.base.adapter.BaseRecyclerAdapter
import com.kenilt.skeleton.ui.custom.FooterLoadingView

/**
 * Created by thangnguyen on 3/7/19.
 */
abstract class RecyclerHelper<T : IModel>(
        protected val prlRefreshLayout: PullRefreshLayout,
        protected val rvRecyclerView: RecyclerView): IRecyclerHelper<T> {

    var recyclerPresenter: RecyclerPresenter<T>? = null
        set(value) {
            field = value
            value?.recyclerHelper = this
        }
    var footerView: View? = null
    var adapter: BaseRecyclerAdapter<T>? = null
    var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener? = null

    init {
        initialize()
    }

    private fun initialize() {
        initRefreshLayout()
        initRecyclerView()
    }

    open fun initRefreshLayout() {
        prlRefreshLayout.setOnRefreshListener {
            onRefreshData()
        }
    }

    open fun initRecyclerView() {
        // layout manager
        val layoutManager = createLayoutManager()
        checkLayoutManager(layoutManager)
        rvRecyclerView.layoutManager = layoutManager
        // adapter
        if (adapter == null) {
            adapter = createAdapter()
        }
        rvRecyclerView.adapter = adapter
        // footer
        footerView = createFooterView()
        footerView?.let {
            adapter?.setFooterView(it)
        }
        if (endlessRecyclerOnScrollListener == null) {
            endlessRecyclerOnScrollListener = createEndlessRecyclerScrollListener()
        }
        loadCacheData()
        endlessRecyclerOnScrollListener?.let {
            rvRecyclerView.addOnScrollListener(it)
        }
        updateFooterState()

        setAnimatorForRecyclerView()
        customRecyclerViewSetUp()
    }

    open fun setAnimatorForRecyclerView() {
        // disable item animator
        rvRecyclerView.itemAnimator = null
    }

    open fun customRecyclerViewSetUp() {}

    open fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(rvRecyclerView.context)
    }

    open fun checkLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        if (!(layoutManager is LinearLayoutManager || layoutManager is GridLayoutManager)) {
            throw IllegalArgumentException("Layout manager must be instance of LinearLayoutManager or GridLayoutManager")
        }
    }

    open fun createFooterView(): View {
        val footerLoadingView = FooterLoadingView(rvRecyclerView.context)
        val noItemText = getNoItemText()
        if (noItemText != null) {
            footerLoadingView.setNoItemText(noItemText)
        }
        return footerLoadingView
    }

    open fun getNoItemText(): String? {
        return null
    }

    abstract fun createAdapter(): BaseRecyclerAdapter<T>?

    open fun createEndlessRecyclerScrollListener(): EndlessRecyclerOnScrollListener? {
        val layoutManager = rvRecyclerView.layoutManager ?: return null
        return object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemCount: Int) {
                onLoadMoreData(page, totalItemCount)
            }
        }
    }

    open fun loadCacheData() { }

    open fun onRefreshData() {
        endlessRecyclerOnScrollListener?.reset()
        recyclerPresenter?.onRefreshData()
    }

    open fun onLoadMoreData(page: Int, totalItemsCount: Int) {
        recyclerPresenter?.callApiDataWithType(false)
    }

    override fun setDataListForAdapter(dataModelList: MutableList<T>, isRefresh: Boolean) {
        if (isRefresh) {
            adapter?.dataList = dataModelList
        } else {
            adapter?.appendDataList(dataModelList)
        }
    }

    override fun setRefreshing(isRefreshing: Boolean) {
        prlRefreshLayout.setRefreshing(isRefreshing)
    }

    override fun setEnded(isEnded: Boolean) {
        endlessRecyclerOnScrollListener?.isEnded = isEnded
        updateFooterState()
    }

    open fun updateFooterState() {
        val footerView = footerView ?: return
        if (endlessRecyclerOnScrollListener?.isEnded == true) {
            if (adapter != null && adapter?.isEmptyContent() == false) {
                adapter?.clearFooters()
            } else {
                adapter?.setFooterView(footerView)
                if (footerView is FooterLoadingView) {
                    footerView.setState(FooterLoadingView.FooterState.NO_ITEM)
                }
            }
        } else {
            adapter?.setFooterView(footerView)
            if (footerView is FooterLoadingView) {
                footerView.setShowContent(true)
                footerView.setState(FooterLoadingView.FooterState.LOADING)
            }
        }
    }

    open fun reset() {
        recyclerPresenter?.stopAllRequest()
        recyclerPresenter?.page = 1

        endlessRecyclerOnScrollListener?.reset()
        adapter?.clearDataList()
        rvRecyclerView.post {
            if (endlessRecyclerOnScrollListener?.isScrollFired == false) {
                onRefreshData()
            }
        }
        updateFooterState()
    }

    open fun smoothScrollToTop() {
        val anchorPosition = 20
        if (endlessRecyclerOnScrollListener?.getFirstVisiblePosition().value > anchorPosition) {
            rvRecyclerView.scrollToPosition(anchorPosition)
        }
        rvRecyclerView.smoothScrollToPosition(0)
    }

    open fun smoothScrollToTopAndAction(afterAnimation: Runnable) {
        if (endlessRecyclerOnScrollListener?.getFirstVisiblePosition() == 0) {
            afterAnimation.run()
        } else {
            rvRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (endlessRecyclerOnScrollListener?.getFirstVisiblePosition() == 0) {
                        rvRecyclerView.removeOnScrollListener(this)
                        afterAnimation.run()
                    }
                }
            })
        }
        smoothScrollToTop()
    }

    fun preventJustRecentActionEffectLoad() {
        endlessRecyclerOnScrollListener?.previousTotal = adapter?.itemCount.value
    }

    fun removeItemAtPosition(position: Int) {
        adapter?.removeItem(position)
        updateFooterState()
    }

    fun removeItem(item: T) {
        adapter?.removeItem(item)
        updateFooterState()
    }
}
