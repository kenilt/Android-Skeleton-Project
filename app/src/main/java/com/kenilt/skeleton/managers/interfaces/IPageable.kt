package com.kenilt.skeleton.managers.interfaces

/**
 * Created by thangnguyen on 4/10/18.
 */
interface IPageable<T> {

    fun getDataList(): MutableList<T>?

    fun hasNextPage(): Boolean
}
