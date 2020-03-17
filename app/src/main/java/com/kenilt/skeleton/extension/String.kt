package com.kenilt.skeleton.extension

import android.net.Uri
import org.json.JSONObject

/**
 * Created by thangnguyen on 4/3/18.
 */
val String?.value: String
    get() = this ?: ""

fun String?.toURI(): Uri? {
    if (this.isNullOrEmpty()) {
        return null
    }
    var uri: Uri? = null
    try {
        uri = Uri.parse(this)
    } catch (ex: Exception) {
        // do nothing
    }
    return uri
}

fun String?.toJSONObject(): JSONObject? {
    if (this.isNullOrEmpty()) {
        return null
    }
    var result: JSONObject? = null
    try {
        result = JSONObject(this)
    } catch (ex: Exception) {
        // do nothing
    }
    return result
}

fun String.fixUnExpectedSpace(): String {
        return this.replace("[\t]".toRegex(), "")
                .replace("[\r\n]+".toRegex(), "\n")
                .trim()
    }

fun String.removeAllLineBreaks(): String {
    return this.replace("[\t]".toRegex(), "")
            .replace("[\r\n]+".toRegex(), "")
            .replace("[\n]+".toRegex(), "")
            .trim()
}

fun String?.isNotNullNorEmpty(): Boolean {
    return this?.isNotEmpty() ?: false
}

fun String.correctSlug(): String {
    val slug = this
    return if (slug.contains('_')) {
        slug.split('_')[0]
    } else {
        slug
    }
}
