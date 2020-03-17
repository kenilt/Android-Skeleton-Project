package com.kenilt.skeleton.extension

import okhttp3.HttpUrl

fun HttpUrl.lxFilePath(method: String?): String? {
    val urlBuilder = HttpUrl.Builder()
            .scheme(this.scheme)
            .host(this.host)
            .addPathSegments(this.pathSegments.joinToString("/").trim('/'))

    this.queryParameterNames.forEach { name ->
        val param = this.queryParameter(name)
        if (param != null && param.isNotEmpty() && name != "utm_id") {
            when (name) {
                "access_token" -> urlBuilder.addQueryParameter(name, "valid_token")
                "address_last_updated_at" -> urlBuilder.addQueryParameter(name, "1577908836")
                else -> urlBuilder.addQueryParameter(name, param)
            }
        }
    }

    if (method != "GET") {
        urlBuilder.addQueryParameter("method", method)
    }

    return urlBuilder.build().toUrl().file?.trim('/')
}
