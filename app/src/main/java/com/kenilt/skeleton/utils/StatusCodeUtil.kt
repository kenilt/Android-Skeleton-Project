package com.kenilt.skeleton.utils

/**
 * Created by neal on 2/20/17.
 */

object StatusCodeUtil {

    fun isSuccess(code: Int): Boolean {
        return code in 200..299
    }

    fun isInformation(code: Int): Boolean {
        return code in 100..199
    }

    fun isServerError(code: Int): Boolean {
        return code in 500..599
    }

    fun isClientError(code: Int): Boolean {
        return code in 400..499
    }

}
