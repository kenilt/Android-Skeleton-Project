package com.kenilt.util

import java.util.concurrent.Executor

/**
 * Created by thangnguyen on 2019-07-25.
 */
class InstantExecutor: Executor {
    override fun execute(command: Runnable?) {
        command?.run()
    }
}
