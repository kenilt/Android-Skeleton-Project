package com.kenilt.skeleton.managers.helpers

/**
 * Created by thangnguyen on 12/7/18.
 */
class SimpleGeometricCounter {
    fun getValueAt(index: Int): Long {
        if (index < 100) {
            return 1L + index * index
        }
        return 10000
    }
}
