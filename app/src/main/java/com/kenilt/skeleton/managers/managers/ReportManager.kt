package com.kenilt.skeleton.managers.managers

import com.kenilt.skeleton.managers.interfaces.IOnlineReporter

class ReportManager private constructor() {
    companion object {
        private val mReporter: IOnlineReporter = SlackReporter()

        @Synchronized
        fun getReporter(): IOnlineReporter {
            return mReporter
        }
    }
}
