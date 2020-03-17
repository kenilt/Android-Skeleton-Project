package com.kenilt.skeleton.ui.custom

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.Keep
import com.kenilt.skeleton.R

/**
 * https://github.com/MRezaNasirloo/CircularProgressBar/blob/master/app/src/main/java/com/mrn/customprogressbar/CircleProgressBar.java
 * Created by thangnguyen on 3/21/19.
 */
class CircularProgressBar : View {

    /**
     * ProgressBar's line thickness
     */
    var strokeWidth = 4f
        set(value) {
            field = value
            foregroundPaint.strokeWidth = value
            invalidate()
            requestLayout()//Because it should recalculate its bounds
        }
    var backStrokeWidth = 4f
        set(value) {
            field = value
            backgroundPaint.strokeWidth = value
            invalidate()
            requestLayout()//Because it should recalculate its bounds
        }
    private var progress = 0f
    private var min = 0
    private var max = 100
    /**
     * Start the progress at 12 o'clock
     */
    private val startAngle = -90
    var color = Color.DKGRAY
        set(value) {
            field = value
            foregroundPaint.color = value
            invalidate()
            requestLayout()
        }
    var backColor = Color.DKGRAY
        set(value) {
            field = value
            backgroundPaint.color = value
            invalidate()
            requestLayout()
        }
    private lateinit var rectF: RectF
    private lateinit var backgroundPaint: Paint
    private lateinit var foregroundPaint: Paint

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    fun getProgress(): Float {
        return progress
    }

    @Keep
    fun setProgress(progress: Float) {
        if (this.progress != progress) {
            this.progress = progress
            invalidate()
        }
    }

    fun getMin(): Int {
        return min
    }

    fun setMin(min: Int) {
        this.min = min
        invalidate()
    }

    fun getMax(): Int {
        return max
    }

    fun setMax(max: Int) {
        if (this.max != max) {
            this.max = max
            invalidate()
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        rectF = RectF()
        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint.style = Paint.Style.STROKE
        foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        foregroundPaint.style = Paint.Style.STROKE

        val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CircleProgressBar,
                0, 0)
        //Reading values from the XML layout
        try {
            strokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_progressBarThickness, strokeWidth)
            progress = typedArray.getFloat(R.styleable.CircleProgressBar_progress, progress)
            color = typedArray.getInt(R.styleable.CircleProgressBar_progressbarColor, color)
            min = typedArray.getInt(R.styleable.CircleProgressBar_min, min)
            max = typedArray.getInt(R.styleable.CircleProgressBar_max, max)
        } finally {
            typedArray.recycle()
        }

        foregroundPaint.color = color
        foregroundPaint.strokeWidth = strokeWidth
        foregroundPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawOval(rectF, backgroundPaint)
        val angle = 360 * progress / max
        canvas.drawArc(rectF, startAngle.toFloat(), angle, false, foregroundPaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(width, height)
        setMeasuredDimension(min, min)
        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2)
    }

    /**
     * Lighten the given color by the factor
     *
     * @param color  The color to lighten
     * @param factor 0 to 4
     * @return A brighter color
     */
    fun lightenColor(color: Int, factor: Float): Int {
        val r = Color.red(color) * factor
        val g = Color.green(color) * factor
        val b = Color.blue(color) * factor
        val ir = Math.min(255, r.toInt())
        val ig = Math.min(255, g.toInt())
        val ib = Math.min(255, b.toInt())
        val ia = Color.alpha(color)
        return Color.argb(ia, ir, ig, ib)
    }

    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param color  The color to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    /**
     * Set the progress with an animation.
     * Note that the [android.animation.ObjectAnimator] Class automatically set the progress
     * so don't call the [CircularProgressBar.setProgress] directly within this method.
     *
     * @param progress The progress it should animate to it.
     */
    fun setProgressWithAnimation(progress: Float) {
        if (this.progress != progress) {
            val objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress)
            objectAnimator.duration = 500
            objectAnimator.interpolator = DecelerateInterpolator()
            objectAnimator.start()
        }
    }
}
