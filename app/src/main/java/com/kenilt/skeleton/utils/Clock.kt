package com.kenilt.skeleton.utils

/**
 * Created by thangnguyen on 2019-06-05.
 */
open class Clock {
    open fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}
