package com.kenilt.skeleton.ui.base.paged

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.model.vo.Listing
import com.kenilt.skeleton.model.vo.NetworkState
import com.kenilt.skeleton.model.vo.PagedCached

/**
 * Created by thangnguyen on 2019-07-03.
 */
abstract class BasePagedViewModel<T: IModel>: ViewModel() {

    val pagedCached = PagedCached<T>()
    private val pageId = MutableLiveData<Int>()
    protected val listResult = Transformations.map(pageId) {
        callLoadListData(pagedCached)
    }

    val listData: LiveData<PagedList<T>> = Transformations.switchMap(listResult) { it.pagedList }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(listResult) { it.networkState }
    val refreshState: LiveData<NetworkState> = Transformations.switchMap(listResult) { it.refreshState }

    open fun refresh() {
        pagedCached.clearCache()
        listResult.value?.refresh?.invoke()
    }

    open fun retry() {
        listResult.value?.retry?.invoke()
    }

    fun postLoadPage() {
        pageId.postValue(1)
    }

    fun insertItem(item: T, position: Int = 0) {
        pagedCached.insertItem(item, position)
        listResult.value?.refresh?.invoke()
    }

    fun updateItem(item: T, position: Int) {
        pagedCached.updateItem(item, position)
        listResult.value?.refresh?.invoke()
    }

    fun updateItemById(item: T) {
        pagedCached.updateItemById(item)
        listResult.value?.refresh?.invoke()
    }

    fun removeItem(item: T) {
        removeItemById(item.getStableId())
    }

    fun removeItemById(id: Long) {
        pagedCached.removeItemById(id)
        listResult.value?.refresh?.invoke()
    }

    protected abstract fun callLoadListData(pagedCached: PagedCached<T>): Listing<T>
}
