package com.kenilt.skeleton.utils

/**
 * Created by thangnguyen on 2019-06-05.
 */
class TestableClock: Clock() {
    var currentMillis = 0L

    override fun currentTimeMillis(): Long {
        return currentMillis
    }
}
