package com.kenilt.skeleton.utils

/**
 * Created by thangnguyen on 2019-06-05.
 */
object InstantClock {
    var clock = Clock()

    fun currentTimeMillis(): Long {
        return clock.currentTimeMillis()
    }
}
