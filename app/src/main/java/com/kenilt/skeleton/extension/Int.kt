package com.kenilt.skeleton.extension

/**
 * Created by thangnguyen on 11/24/17.
 */

val Int?.value: Int
    get() = this ?: 0

infix fun Int?.orElse(default: Int): Int = if (this == null || this == 0) default else this

infix fun Int?.orElse(default: (() -> Int)): Int = if (this == null || this == 0) default.invoke() else this
