package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.kenilt.skeleton.R


/**
 * Created by thangnguyen on 2019-09-12.
 * Link: https://stackoverflow.com/a/27054463/2347872
 */
class DashView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val mPaint: Paint
    private var orientation: Int = 0

    init {
        val dashGap: Int
        val dashLength: Int
        val dashThickness: Int
        val color: Int

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.DashView, 0, 0)

        try {
            dashGap = a.getDimensionPixelSize(R.styleable.DashView_dashGap, 5)
            dashLength = a.getDimensionPixelSize(R.styleable.DashView_dashLength, 5)
            dashThickness = a.getDimensionPixelSize(R.styleable.DashView_dashThickness, 3)
            color = a.getColor(R.styleable.DashView_color, -0x1000000)
            orientation = a.getInt(R.styleable.DashView_orientation, ORIENTATION_HORIZONTAL)
        } finally {
            a.recycle()
        }

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = color
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = dashThickness.toFloat()
        mPaint.pathEffect = DashPathEffect(floatArrayOf(dashLength.toFloat(), dashGap.toFloat()), 0f)
    }

    override fun onDraw(canvas: Canvas) {
        if (orientation == ORIENTATION_HORIZONTAL) {
            val center = height * 0.5f
            canvas.drawLine(0f, center, width.toFloat(), center, mPaint)
        } else {
            val center = width * 0.5f
            canvas.drawLine(center, 0f, center, height.toFloat(), mPaint)
        }
    }

    companion object {
        var ORIENTATION_HORIZONTAL = 0
        var ORIENTATION_VERTICAL = 1
    }
}
