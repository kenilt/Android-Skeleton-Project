package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

class LXWebView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : WebView(getLXWebViewContext(context), attrs, defStyleAttr, defStyleRes)

private fun getLXWebViewContext(context: Context): Context {
    if (Build.VERSION.SDK_INT in 21..22) {// Android Lollipop 5.0 & 5.1
        return context.createConfigurationContext(Configuration())
    }
    return context
}
