package com.kenilt.skeleton.extension

/**
 * Created by thangnguyen on 11/24/17.
 */

val Boolean?.isTrue: Boolean
    get() = this ?: false

val Boolean?.isFalse: Boolean
    get() = this != true
