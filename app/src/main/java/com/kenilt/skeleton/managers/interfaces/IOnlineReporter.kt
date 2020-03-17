package com.kenilt.skeleton.managers.interfaces

interface IOnlineReporter {
    fun sendLogMessage(message: String)
    fun sendErrorMessage(message: String)
    fun sendThrowable(message: String, throwable: Throwable)
    fun sendTimeResponseMessage(responseTime: Long, uuid: String, path: String)
}
