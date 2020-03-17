package com.kenilt.skeleton.ui.base.paged

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.kenilt.skeleton.managers.interfaces.IModel

/**
 * Created by thangnguyen on 2019-07-03.
 */
abstract class LXDataSourceFactory<T: BasePagedKeyDataSource<V, *>, V: IModel> : DataSource.Factory<Int, V>() {

    var sourceLiveData: MutableLiveData<T> = MutableLiveData()

    override fun create(): DataSource<Int, V> {
        val dataSource = getDataSource()
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

    abstract fun getDataSource(): T
}
