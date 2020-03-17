package com.kenilt.skeleton.managers.helpers


object Logout {
    private const val DEFAULT_LEVEL = 5
    private const val FALLBACK_LEVEL = 6

    private const val REST_CALL_FILE = "RestCall.kt"

    fun getNearestClassNameMethodAndLineNumber(): String {
        val stackTrace = Thread.currentThread().stackTrace
        var levelTrace = stackTrace.getOrNull(DEFAULT_LEVEL)
        if (levelTrace?.fileName == REST_CALL_FILE) {
            levelTrace = stackTrace.getOrNull(FALLBACK_LEVEL)
        }
        return "${levelTrace?.fileName}->${levelTrace?.methodName}:${levelTrace?.lineNumber}"
    }
}
