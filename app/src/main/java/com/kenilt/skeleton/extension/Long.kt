package com.kenilt.skeleton.extension

val Long?.value: Long
    get() = this ?: 0

fun Long.toNumberOfDays(): Long {
    return this / 86400
}
