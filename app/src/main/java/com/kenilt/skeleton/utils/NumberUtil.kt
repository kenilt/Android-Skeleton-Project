package com.kenilt.skeleton.utils

import android.content.Context
import com.kenilt.skeleton.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * Created by thanh.nguyen on 5/15/15.
 */
object NumberUtil {

    private val groupingSeparator by lazy { DecimalFormatSymbols.getInstance().groupingSeparator }

    private fun formatDecimal(number: Double, format: String): String {
        val formatter = DecimalFormat(format)
        return formatter.format(number)
    }

    fun formatPrice(number: Double, context: Context): String {
        return formatDecimal(number,
                context.getString(R.string.format_full_price_vnd))
    }

    fun formatNumber(number: Double, context: Context): String {
        return formatDecimal(number,
                context.getString(R.string.normal_number_format))
    }

    fun fastFormatNumber(number: Int): String {
        if (number < 1000 && number > -1000) return number.toString()

        val startRequiredCount = if (number > 0) 0 else 1
        val charArray = number.toString().toCharArray()
        val originalSize = charArray.size
        val size = originalSize + (originalSize - 1 - startRequiredCount) / 3
        val resultArray = CharArray(size)
        var iRes = size - 1
        var iSeparate = 0
        for (i in (originalSize-1) downTo 0) {
            resultArray[iRes] = charArray[i]
            iRes--
            iSeparate++
            if (iSeparate == 3 && iRes > startRequiredCount) {
                iSeparate = 0
                resultArray[iRes] = groupingSeparator
                iRes--
            }
        }

        return String(resultArray)
    }

    fun fastFormatPrice(number: Int): String {
        return fastFormatNumber(number) + " đ"
    }

    private fun fastFormatShortPrice(number: Int): String {
        return fastFormatNumber(number / 1000) + " K"
    }

    fun fastFormatPossiblePrice(number: Int): String {
        return if (Utils.widthInDp > 350) {
            fastFormatPrice(number)
        } else {
            fastFormatShortPrice(number)
        }
    }
}
