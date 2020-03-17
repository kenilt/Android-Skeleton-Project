package com.kenilt.skeleton.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.kenilt.skeleton.R

object ClipboardUtil {
    fun copyToClipboard(context: Context, strContent: String, strLabel: String? = null) {
        val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.primaryClip = ClipData.newPlainText(strLabel, strContent)
        Toast.makeText(context, context.getString(R.string.copied_text, strContent), Toast.LENGTH_SHORT).show()
    }
}
