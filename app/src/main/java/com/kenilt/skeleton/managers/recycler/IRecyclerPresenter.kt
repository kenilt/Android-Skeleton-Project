package com.kenilt.skeleton.managers.recycler

import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.managers.interfaces.IPageable
import retrofit2.Call

/**
 * Created by thangnguyen on 3/7/19.
 */
interface IRecyclerPresenter<T: IModel> {
    fun onRefreshData()
    fun callApiDataWithType(isRefresh: Boolean)
    fun getApiCallWithType(isRefresh: Boolean): Call<IPageable<T>>?
}
