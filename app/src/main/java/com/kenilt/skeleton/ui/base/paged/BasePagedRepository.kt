package com.kenilt.skeleton.ui.base.paged

import androidx.lifecycle.Transformations
import com.kenilt.skeleton.extension.toLiveData
import com.kenilt.skeleton.managers.AppExecutors
import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.model.vo.Listing
import com.kenilt.skeleton.model.vo.PagedCached
import javax.inject.Inject

/**
 * Created by thangnguyen on 2019-07-05.
 */
abstract class BasePagedRepository<T: IModel> {

    @Inject
    lateinit var appExecutors: AppExecutors

    fun loadListingData(pagedCached: PagedCached<T>?): Listing<T> {
        val sourceFactory = object: LXDataSourceFactory<BasePagedKeyDataSource<T, *>, T>() {
            override fun getDataSource(): BasePagedKeyDataSource<T, *> {
                return getNewDataSource(pagedCached)
            }
        }

        val livePagedList = sourceFactory.toLiveData(PAGE_SIZE, fetchExecutor = appExecutors.networkIO())

        return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                    it.networkState
                },
                refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                    it.initialLoad
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                }
        )
    }

    abstract fun getNewDataSource(pagedCached: PagedCached<T>? = null): BasePagedKeyDataSource<T, *>

    companion object {
        const val PAGE_SIZE = 12
    }
}
