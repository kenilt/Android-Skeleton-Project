package com.kenilt.skeleton.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Created by thangnguyen on 2019-09-17.
 */

fun <T> MediatorLiveData<*>.justWatch(source: LiveData<T>) {
    addSource(source) {
        // do nothing, because just watch
    }
}

fun <T> MediatorLiveData<T>.fromSource(source: LiveData<T>) {
    addSource(source) {
        value = it
    }
}

fun <T> MediatorLiveData<T>.fromSource(source: LiveData<T>, extraAction: (T) -> Unit) {
    addSource(source) {
        value = it
        extraAction.invoke(it)
    }
}

fun <T> MediatorLiveData<T>.changeOneTimeFromSource(source: LiveData<T>, action: (T) -> Unit) {
    removeSource(source)
    addSource(source) { data ->
        value = data
        action.invoke(data)
        removeSource(source)
    }
}

fun <T> MediatorLiveData<*>.watchChangeOneTime(source: LiveData<T>, action: (T) -> Unit) {
    removeSource(source)
    addSource(source) { data ->
        action.invoke(data)
        removeSource(source)
    }
}

fun <T> MediatorLiveData<T>.addSource(vararg sources: LiveData<*>, observer: () -> Unit) {
    sources.forEach { source ->
        addSource(source) {
            observer.invoke()
        }
    }
}
