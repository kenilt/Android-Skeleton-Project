package com.kenilt.skeleton.managers.recycler

import com.kenilt.skeleton.managers.interfaces.IModel

/**
 * Created by thangnguyen on 3/7/19.
 */
interface IRecyclerHelper<T: IModel> {
    fun setRefreshing(isRefreshing: Boolean)
    fun setEnded(isEnded: Boolean)
    fun setDataListForAdapter(dataModelList: MutableList<T>, isRefresh: Boolean)
}
