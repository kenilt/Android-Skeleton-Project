package com.kenilt.skeleton.extension

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import java.util.concurrent.Executor

/**
 * Constructs a `LiveData<PagedList>`, from this `DataSource.Factory`, convenience for
 * [LivePagedListBuilder].
 *
 * No work (such as loading) is done immediately, the creation of the first PagedList is is
 * deferred until the LiveData is observed.
 *
 * @param pageSize Page size.
 * @param initialLoadKey Initial load key passed to the first PagedList/DataSource.
 * @param boundaryCallback The boundary callback for listening to PagedList load state.
 * @param fetchExecutor Executor for fetching data from DataSources.
 *
 * @see LivePagedListBuilder
 */
@SuppressLint("RestrictedApi")
fun <Key, Value> DataSource.Factory<Key, Value>.toLiveData(
        pageSize: Int,
        initialLoadKey: Key? = null,
        boundaryCallback: PagedList.BoundaryCallback<Value>? = null,
        fetchExecutor: Executor = ArchTaskExecutor.getIOThreadExecutor()
): LiveData<PagedList<Value>> {
    return LivePagedListBuilder(this, Config(pageSize))
            .setInitialLoadKey(initialLoadKey)
            .setBoundaryCallback(boundaryCallback)
            .setFetchExecutor(fetchExecutor)
            .build()
}

/**
 * Constructs a [PagedList.Config], convenience for [PagedList.Config.Builder].
 *
 * @param pageSize Number of items loaded at once from the DataSource.
 * @param prefetchDistance Distance the PagedList should prefetch.
 * @param enablePlaceholders False if null placeholders should be disabled.
 * @param initialLoadSizeHint Number of items to load while initializing the PagedList.
 * @param maxSize Maximum number of items to keep in memory, or
 *                [PagedList.Config.MAX_SIZE_UNBOUNDED] to disable page dropping.
 */
@Suppress("FunctionName")
fun Config(
        pageSize: Int,
        prefetchDistance: Int = pageSize,
        enablePlaceholders: Boolean = true
): PagedList.Config {
    return PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setPrefetchDistance(prefetchDistance)
            .setEnablePlaceholders(enablePlaceholders)
            .build()
}
