package com.kenilt.skeleton.ui.helper

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.baoyz.widget.PullRefreshLayout
import com.kenilt.skeleton.extension.registerScrollTopWhenInsertTop
import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.ui.base.paged.BasePagedAdapter
import com.kenilt.skeleton.ui.base.paged.BasePagedViewModel
import com.kenilt.skeleton.ui.custom.decoration.SpaceDecoration
import com.kenilt.skeleton.ui.helper.PagedSetUp.DecorationType.*

/**
 * Created by thangnguyen on 2019-07-05.
 */
class PagedSetUp<T: IModel>(private val owner: LifecycleOwner, private val viewModel: BasePagedViewModel<T>) {
    private lateinit var refreshLayout: PullRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BasePagedAdapter<T>
    private var decorations = ArrayList<RecyclerView.ItemDecoration>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var changeAnimation: Boolean = true

    fun refreshLayout(refreshLayout: PullRefreshLayout) = apply { this.refreshLayout = refreshLayout }

    fun recyclerView(recyclerView: RecyclerView) = apply { this.recyclerView = recyclerView }

    fun adapter(adapter: BasePagedAdapter<T>) = apply { this.adapter = adapter }

    fun adapter(creator: () -> BasePagedAdapter<T>) = apply { this.adapter = creator.invoke() }

    fun layoutManager(layoutManager: RecyclerView.LayoutManager) = apply { this.layoutManager = layoutManager }

    fun decoration(decorationType: DecorationType) = apply {
        val context = owner as? Context ?: recyclerView.context ?: refreshLayout.context
        when (decorationType) {
            SPACE_NORMAL -> decorations.add(SpaceDecoration(context, 8f))
            AROUND_SPACE_NORMAL -> decorations.add(SpaceDecoration(context, 8f, 8f))
            NONE -> { /* do nothing */ }
        }
    }

    fun decoration(decoration: RecyclerView.ItemDecoration) = apply { this.decorations.add(decoration) }

    fun changeAnimation(changeAnimation: Boolean) = apply { this.changeAnimation = changeAnimation }

    fun setUp() {
        // view model observe
        viewModel.refreshState.observe(owner, Observer {
            refreshLayout.setRefreshing(false)
        })
        viewModel.networkState.observe(owner, Observer {
            adapter.networkState = it
        })
        viewModel.listData.observe(owner, Observer {
            adapter.submitList(it)
        })

        // pull refresh action
        refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        // recycler view init
        val context = recyclerView.context
        recyclerView.setHasFixedSize(true)
        if (layoutManager != null) {
            recyclerView.layoutManager = layoutManager
        } else {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        decorations.forEach { recyclerView.addItemDecoration(it) }
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
        if (!changeAnimation) {
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        // adapter extra init
        adapter.onRetryListener = View.OnClickListener {
            viewModel.retry()
        }

        // scroll to top if insert the first item
        recyclerView.registerScrollTopWhenInsertTop()
    }

    enum class DecorationType {
        SPACE_NORMAL,
        AROUND_SPACE_NORMAL,
        NONE
    }
}
