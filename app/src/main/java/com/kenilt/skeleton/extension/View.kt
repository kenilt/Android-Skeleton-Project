package com.kenilt.skeleton.extension

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.transition.TransitionManager
import com.kenilt.skeleton.managers.helpers.LXPicasso
import com.kenilt.skeleton.managers.managers.ReportManager
import com.kenilt.skeleton.utils.NumberUtil

@set:BindingAdapter("visibleOrGone")
var View.visibleOrGone
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

@set:BindingAdapter("visible")
var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else INVISIBLE
    }

@set:BindingAdapter("invisible")
var View.invisible
    get() = visibility == INVISIBLE
    set(value) {
        visibility = if (value) INVISIBLE else VISIBLE
    }

@set:BindingAdapter("gone")
var View.gone
    get() = visibility == GONE
    set(value) {
        visibility = if (value) GONE else VISIBLE
    }

@BindingAdapter("animatedVisibility")
fun View.setAnimatedVisibility(visibility: Int) {
    if (this.parent is ViewGroup) {
        TransitionManager.beginDelayedTransition(this.parent as ViewGroup)
    }
    this.visibility = visibility
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    LXPicasso.load(imageUrl).into(this)
}

@BindingAdapter("backgroundColor")
fun View.setBackground(colorHex: String?) {
    if (colorHex == null) return
    val colorCode = if (colorHex.contains("#")) colorHex else "#$colorHex"
    try {
        val color = Color.parseColor(colorCode)
        setBackgroundColor(color)
    } catch (ex: Exception) {
        ReportManager.getReporter().sendThrowable("Wrong background color code $colorCode", ex)
    }
}

@Suppress("DEPRECATION")
@BindingAdapter("htmlText")
fun TextView.setHtmlText(text: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        this.text = Html.fromHtml(text)
    }
}

@BindingAdapter("priceText")
fun TextView.setPriceText(price: Int) {
    this.text = NumberUtil.fastFormatPrice(price)
}

@BindingAdapter("numberText")
fun TextView.setNumberText(number: Int) {
    this.text = NumberUtil.fastFormatNumber(number)
}

@BindingAdapter("possiblePriceText")
fun TextView.setPossiblePriceText(price: Int) {
    this.text = NumberUtil.fastFormatPossiblePrice(price)
}

@BindingAdapter("oldPossiblePriceText")
fun TextView.setOldPossiblePriceText(oldPriceText: String?) {
    this.text = oldPriceText
    if (oldPriceText?.isNotEmpty() == true) {
        this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@BindingAdapter("numberToFormat", "afterNumberText")
fun TextView.setFormatPrice(price: Double, afterText: String?) {
    val content = "${NumberUtil.fastFormatNumber(price.toInt())} $afterText"
    this.text = content
}

@BindingAdapter("beforeNumberText", "numberToFormat", "afterNumberText")
fun TextView.setFormatPrice(beforeText: String?, price: Double, afterText: String?) {
    val content = "$beforeText ${NumberUtil.fastFormatNumber(price.toInt())} $afterText"
    this.text = content
}

@BindingAdapter("beforeNumberText", "priceText")
fun TextView.setFormatPrice(beforeText: String?, price: Double) {
    val content = "$beforeText ${NumberUtil.fastFormatPrice(price.toInt())}"
    this.text = content
}
