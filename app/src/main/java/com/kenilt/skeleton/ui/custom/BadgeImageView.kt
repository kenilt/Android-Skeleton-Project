package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.kenilt.skeleton.R
import com.kenilt.skeleton.extension.orElse
import com.kenilt.skeleton.managers.helpers.LXPicasso


class BadgeImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var prevUrl: String? = null

    var badgeType: Int = 0

    var badgeImageUrl: String? = null
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                if (value != prevUrl) {
                    LXPicasso.load(value).into(this@BadgeImageView)
                    prevUrl = value
                }
            }
        }

    init {
        scaleType = ScaleType.MATRIX
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BadgeImageView)
        try {
            badgeType = a.getInt(R.styleable.BadgeImageView_badgeType, 0)
            badgeImageUrl = a.getString(R.styleable.BadgeImageView_badgeImageUrl)
        } finally {
            a.recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        recomputeImgMatrix()
    }

    override fun setFrame(left: Int, top: Int, right: Int, bottom: Int): Boolean {
        recomputeImgMatrix()
        return super.setFrame(left, top, right, bottom)
    }

    private fun recomputeImgMatrix() {
        val drawable = drawable ?: return

        val matrix = imageMatrix

        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom
        val drawableWidth = drawable.intrinsicWidth orElse 1
        val drawableHeight = drawable.intrinsicHeight orElse 1

        // scale image to fit with view
        val scale = if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            viewWidth.toFloat() / drawableWidth
        } else {
            viewHeight.toFloat() / drawableHeight
        }
        matrix.setScale(scale, scale)

        // translation
        val tranX: Float
        val tranY: Float
        when (badgeType) {
            BadgeType.TOP_RIGHT.value -> {
                tranX = viewWidth - drawableWidth * scale
                tranY = 0f
            }
            BadgeType.BOTTOM_LEFT.value -> {
                tranX = 0f
                tranY = viewHeight - drawableHeight * scale
            }
            BadgeType.BOTTOM_RIGHT.value -> {
                tranX = viewWidth - drawableWidth * scale
                tranY = viewHeight - drawableHeight * scale
            }
            else -> {
                tranX = 0f
                tranY = 0f
            }
        }
        matrix.postTranslate(tranX, tranY)

        imageMatrix = matrix
    }

    enum class BadgeType(var value: Int) {
        TOP_LEFT (0),
        TOP_RIGHT (1),
        BOTTOM_LEFT (2),
        BOTTOM_RIGHT (3)
    }
}
