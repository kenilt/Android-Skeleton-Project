package com.kenilt.skeleton.extension

/**
 * Created by thangnguyen on 2019-07-29.
 */
fun <T> MutableList<T>?.isNotNullNorEmpty(): Boolean {
    return !this.isNullOrEmpty()
}
