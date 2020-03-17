package com.kenilt.skeleton.extension

import android.content.Context
import android.net.Uri
import com.kenilt.skeleton.R

fun Uri?.isYourDomainLink(context: Context): Boolean {
    val host = this?.host ?: return false
    return host == context.getString(R.string.web_host) || host == context.getString(R.string.app_link_host)
}

fun Uri?.isValidUrlScheme(): Boolean {
    val host = this?.scheme ?: return false
    return host == "http" || host == "https"
}

fun Uri?.firstSegment(): String? {
    return this?.pathSegments?.getOrNull(0)
}

fun Uri?.secondSegment(): String? {
    return this?.pathSegments?.getOrNull(1)
}

fun Uri?.thirdSegment(): String? {
    return this?.pathSegments?.getOrNull(2)
}

fun Uri?.segmentCount(): Int {
    return this?.pathSegments?.size ?: 0
}
