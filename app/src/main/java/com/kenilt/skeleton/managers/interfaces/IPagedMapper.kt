package com.kenilt.skeleton.managers.interfaces

/**
 * Created by thangnguyen on 2019-08-23.
 */
interface IPagedMapper<Value, Remote> {
    fun convert(data: Remote?): Value?
}
