package com.kenilt.skeleton.model.vo

import com.kenilt.skeleton.extension.value
import com.kenilt.skeleton.managers.interfaces.IModel

/**
 * Created by thangnguyen on 2019-07-30.
 */
class PagedCached<Value: IModel> {
    var cachedItems: MutableList<Value>? = null
    var cachedNextKey: Int? = null

    fun cacheInitialData(items: List<Value>, nextKey: Int?) {
        cachedItems = ArrayList()
        cacheData(items, nextKey)
    }

    fun cacheData(items: List<Value>, nextKey: Int?) {
        cachedItems?.addAll(items)
        cachedNextKey = nextKey
    }

    fun hasCachedData(): Boolean {
        return cachedItems != null
    }

    fun insertItem(item: Value, position: Int = 0) {
        cachedItems?.add(position, item)
    }

    fun updateItem(item: Value, position: Int) {
        if (position < cachedItems?.size.value) {
            cachedItems?.set(position, item)
        }
    }

    fun updateItemById(item: Value) {
        val position = cachedItems?.indexOfFirst { it.getStableId() == item.getStableId() } ?: return
        if (position >= 0) {
            updateItem(item, position)
        }
    }

    fun removeItem(item: Value) {
        removeItemById(item.getStableId())
    }

    fun removeItemById(id: Long) {
        cachedItems?.removeAll { it.getStableId() == id }
    }

    fun clearCache() {
        cachedItems = null
        cachedNextKey = null
    }
}
