package com.kenilt.skeleton.extension

/**
 * Created by thangnguyen on 11/26/18.
 */

val Float?.value: Float
    get() = this ?: 0f

fun Float.format(digits: Int): String = "%.${digits}f".format(this)

infix fun Float?.orElse(default: Float): Float = if (this == null || this == 0f) default else this

infix fun Float?.orElse(default: (() -> Float)): Float = if (this == null || this == 0f) default.invoke() else this
