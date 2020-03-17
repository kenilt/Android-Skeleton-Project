package com.kenilt.skeleton.model

data class CustomInfo(
        var jsonPath: String? = null,
        var json: String? = null,
        var code: Int = 200,    // if response code = -1 => exception
        var slowInfo: SlowInfo? = null,
        var executeTime: Int = -1
) {
    fun slow(delay: Long = 2000): CustomInfo {
        slowInfo = SlowInfo(delayTime = delay)
        return this
    }

    fun error(code: Int = 404, jsonPath: String = "errors/error.json"): CustomInfo {
        this.code = code
        this.jsonPath = jsonPath
        return this
    }

    fun errorContent(code: Int = 422, json: String = "{}"): CustomInfo {
        this.code = code
        this.json = json
        return this
    }

    fun exception(): CustomInfo {
        this.code = -1
        return this
    }
}
