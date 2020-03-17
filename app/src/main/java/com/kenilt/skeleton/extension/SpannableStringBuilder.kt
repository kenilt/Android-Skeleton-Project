package com.kenilt.skeleton.extension

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import com.kenilt.skeleton.managers.helpers.CustomTypefaceSpan

/**
 * Created by thangnguyen on 2019-05-16.
 */
fun SpannableStringBuilder.appendPart(spannablePart: SpannablePart) = apply { spannablePart.attachInto(this) }

class SpannablePart {
    private var text: String = ""
    private var color: Int = 0
    private var isStrike: Boolean = false
    private var typeface: Typeface? = null
    private var clickableSpan: ClickableSpan? = null

    fun text(text: String) = apply { this.text = text }
    fun color(color: Int) = apply { this.color = color }
    fun typeface(typeface: Typeface) = apply { this.typeface = typeface }
    fun isStrike(isStrike: Boolean) = apply { this.isStrike = isStrike }
    fun clickableSpan(clickableSpan: ClickableSpan) = apply { this.clickableSpan = clickableSpan }

    fun attachInto(stringBuilder: SpannableStringBuilder) {
        val startPos = stringBuilder.length
        stringBuilder.append(text)
        val endPos = stringBuilder.length
        if (color != 0) {
            stringBuilder.setSpan(ForegroundColorSpan(color), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        typeface?.also {
            stringBuilder.setSpan(CustomTypefaceSpan("", it), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (isStrike) {
            stringBuilder.setSpan(StrikethroughSpan(), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (clickableSpan != null) {
            stringBuilder.setSpan(clickableSpan, startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}
