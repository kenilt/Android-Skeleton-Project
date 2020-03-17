package com.kenilt.skeleton.model

data class SlowInfo(
        var bytesPerSecond: Long = 100 * 1024,
        var delayTime: Long = 2000,
        var executeTime: Int = 1
)
