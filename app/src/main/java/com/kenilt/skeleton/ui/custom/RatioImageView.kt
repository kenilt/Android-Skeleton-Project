package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.kenilt.skeleton.R
import kotlin.math.abs

/**
 * Created by thangnguyen on 1/4/19.
 */
class RatioImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    // aspectRatio = width / height
    var aspectRatio: Float = 0f
        set(value) {
            if (abs(field - value) > 0.0001) {
                field = value
                requestLayout()
            }
        }

    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
        try {
            val ratioParts = a.getString(R.styleable.RatioFrameLayout_aspectRatio)?.split(":")
            if (!ratioParts.isNullOrEmpty()) {
                val widthRate = ratioParts[0].toFloat()
                val heightRate = ratioParts.getOrNull(1)?.toFloat() ?: 1f
                aspectRatio = widthRate / heightRate
            }
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (aspectRatio > 0) {
            val originalWidth = MeasureSpec.getSize(widthMeasureSpec)
            val moddedHeight = (originalWidth / aspectRatio).toInt()
            super.onMeasure(MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(moddedHeight, MeasureSpec.EXACTLY))
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}
