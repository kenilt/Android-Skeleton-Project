package com.kenilt.skeleton.utils

import android.os.Handler
import android.os.Looper
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import java.util.concurrent.Executors

/**
 * Created by thangnguyen on 2019-08-02.
 */
object PagedListUtil {

    fun <Value> convertListToPagedList(dataList: List<Value>): PagedList<Value> {
        val myConfig = PagedList.Config.Builder()
                .setPageSize(12)
                .build()
        val dataSource = object : PageKeyedDataSource<Int, Value>() {
            override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Value>) {
                callback.onResult(dataList, null, null)
            }

            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {}

            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {}
        }
        return PagedList.Builder(dataSource, myConfig)
                .setInitialKey(1)
                .setNotifyExecutor { Handler(Looper.getMainLooper()).post(it) }
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()
    }
}
