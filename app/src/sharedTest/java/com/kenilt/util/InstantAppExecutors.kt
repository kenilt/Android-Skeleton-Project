package com.kenilt.util

import com.kenilt.skeleton.managers.AppExecutors

/**
 * Created by thangnguyen on 2019-07-25.
 */
class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = InstantExecutor()
    }
}
