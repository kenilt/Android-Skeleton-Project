package com.kenilt.skeleton.managers.helpers

/**
 * Created by Kenilt Nguyen on 8/13/18.
 */
class FibonnaciCounter {
    var tryCount: Int = 0
    var fiboValue: Long = 1
    var pf1: Long = 1
    var pf2: Long = 1

    fun reset() {
        tryCount = 0
        fiboValue = 1
        pf1 = 1
        pf2 = 1
    }

    fun next(): Long {
        tryCount++
        pf2 = pf1
        pf1 = fiboValue
        fiboValue = pf1 + pf2
        return fiboValue
    }
}
