package com.kenilt.skeleton.ui.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

/**
 * Inspired by Blogcat
 * https://github.com/Blogcat/Android-ExpandableTextView
 */
class ExpandableTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_ANIM_DURATION = 250L
    }

    var collapsedMaxLines: Int = maxLines
    private var hasHiddenText = false
    private var isExpanded = false
    private var isAnimating = false
    var animationDuration: Long = DEFAULT_ANIM_DURATION
    var expandInterpolator: TimeInterpolator = AccelerateDecelerateInterpolator()
    var collapseInterpolator: TimeInterpolator = AccelerateDecelerateInterpolator()

    private var collapsedHeight: Int = 0

    var onExpandStateChangeListener: OnExpandStateChangeListener? = null

    var readMoreTextView: TextView? = null
        set(value) {
            field = value
            value?.setOnClickListener {
                this.expand()
            }
        }
    private var previousReadMoreHeight: Int = 0

    override fun setText(text: CharSequence?, type: BufferType?) {
        resetMaxLines()
        isExpanded = false
        super.setText(text, type)

        if (text.isNullOrEmpty()) {
            readMoreTextView?.visibility = View.GONE
        } else {
            post {
                validateReadMoreVisibility()
            }
        }
    }

    private fun validateReadMoreVisibility() {
        hasHiddenText = false
        val layout = layout
        if (layout != null) {
            val lines = layout.lineCount
            if (lines == collapsedMaxLines) {
                val ellipsisCount = layout.getEllipsisCount(lines - 1)
                hasHiddenText = ellipsisCount > 0
            }
        }

        if (hasHiddenText && !isExpanded) {
            readMoreTextView?.visibility = View.VISIBLE
        } else {
            readMoreTextView?.visibility = View.GONE
        }
    }

    fun expand(): Boolean {
        if (!hasHiddenText || isExpanded || isAnimating) {
            return false
        }

        // indicate that we are now animating
        isAnimating = true

        // measure collapsed height
        measure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))

        collapsedHeight = this.measuredHeight

        // set maxLines to MAX Integer, so we can calculate the expanded height
        maxLines = Integer.MAX_VALUE

        // measure expanded height
        measure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))

        val expandedHeight = measuredHeight

        // animate from collapsed height to expanded height
        val valueAnimator = ValueAnimator.ofInt(collapsedHeight, expandedHeight)
        val thisView = this@ExpandableTextView
        valueAnimator.addUpdateListener { animation ->
            thisView.height = animation.animatedValue as Int
        }
        // wait for the animation to end
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // reset min & max height (previously set with setHeight() method)
                thisView.maxHeight = Integer.MAX_VALUE
                thisView.minHeight = 0

                // if fully expanded, set height to WRAP_CONTENT, because when rotating the device
                // the height calculated with this ValueAnimator isn't correct anymore
                val layoutParams = thisView.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                thisView.layoutParams = layoutParams

                // keep track of current status
                thisView.isExpanded = true
                thisView.isAnimating = false

                onExpandStateChangeListener?.onExpandStateChanged(thisView, thisView.isExpanded)
            }
        })
        valueAnimator.interpolator = expandInterpolator
        valueAnimator.duration = animationDuration
        valueAnimator.start()

        readMoreTextView?.let { readMoreTextView ->
            previousReadMoreHeight = readMoreTextView.height
            val subValueAnimator = ValueAnimator.ofInt(previousReadMoreHeight, 0)
            subValueAnimator.addUpdateListener { animation ->
                val height = animation.animatedValue as Int
                readMoreTextView.height = height
                var alpha = 255 - 2 * (255 - 255 * height / previousReadMoreHeight)
                if (alpha < 0) alpha = 0
                readMoreTextView.setTextColor(readMoreTextView.textColors.withAlpha(alpha))
                readMoreTextView.background?.alpha = alpha
            }
            // wait for the animation to end
            subValueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // reset min & max height (previously set with setHeight() method)
                    readMoreTextView.maxHeight = Integer.MAX_VALUE
                    readMoreTextView.minHeight = 0

                    readMoreTextView.setTextColor(readMoreTextView.textColors.withAlpha(255))
                    readMoreTextView.background?.alpha = 255

                    val layoutParams = readMoreTextView.layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    readMoreTextView.layoutParams = layoutParams
                    readMoreTextView.visibility = View.GONE
                }
            })
            subValueAnimator.interpolator = expandInterpolator
            subValueAnimator.duration = animationDuration
            subValueAnimator.start()
        }

        return true
    }

    private fun collapse(): Boolean {
        if (!isExpanded || isAnimating) {
            return false
        }

        // measure expanded height
        val expandedHeight = measuredHeight

        // indicate that we are now animating
        isAnimating = true

        // animate from expanded height to collapsed height
        val valueAnimator = ValueAnimator.ofInt(expandedHeight, collapsedHeight)
        val thisView = this@ExpandableTextView
        valueAnimator.addUpdateListener { animation ->
            thisView.height = animation.animatedValue as Int
        }

        // wait for the animation to end
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // keep track of current status
                thisView.isExpanded = false
                thisView.isAnimating = false

                // set maxLines back to original value
                thisView.maxLines = collapsedMaxLines

                // if fully collapsed, set height back to WRAP_CONTENT, because when rotating the device
                // the height previously calculated with this ValueAnimator isn't correct anymore
                val layoutParams = thisView.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                thisView.layoutParams = layoutParams

                onExpandStateChangeListener?.onExpandStateChanged(thisView, thisView.isExpanded)
            }
        })

        // set interpolator
        valueAnimator.interpolator = collapseInterpolator
        valueAnimator.duration = animationDuration
        valueAnimator.start()

        readMoreTextView?.let { readMoreTextView ->
            readMoreTextView.visibility = View.VISIBLE
            val subValueAnimator = ValueAnimator.ofInt(0, previousReadMoreHeight)
            subValueAnimator.addUpdateListener { animation ->
                val height = animation.animatedValue as Int
                readMoreTextView.height = height
                val alpha = 255 * height / previousReadMoreHeight
                readMoreTextView.setTextColor(readMoreTextView.textColors.withAlpha(alpha))
                readMoreTextView.background?.alpha = alpha
            }
            // wait for the animation to end
            subValueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // reset min & max height (previously set with setHeight() method)
                    readMoreTextView.maxHeight = Integer.MAX_VALUE
                    readMoreTextView.minHeight = 0

                    readMoreTextView.setTextColor(readMoreTextView.textColors.withAlpha(255))
                    readMoreTextView.background?.alpha = 255

                    val layoutParams = readMoreTextView.layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    readMoreTextView.layoutParams = layoutParams
                }
            })
            subValueAnimator.interpolator = expandInterpolator
            subValueAnimator.duration = animationDuration
            subValueAnimator.start()
        }

        return true
    }

    fun toggle() {
        if (isExpanded) collapse() else expand()
    }

    private fun resetMaxLines() {
        if (collapsedMaxLines > 0 && maxLines != collapsedMaxLines) {
            maxLines = collapsedMaxLines
        }
    }

    fun canExpand(): Boolean {
        return hasHiddenText && !isExpanded
    }

    interface OnExpandStateChangeListener {
        /**
         * Called when the expand/collapse animation has been finished
         *
         * @param textView - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        fun onExpandStateChanged(textView: TextView, isExpanded: Boolean)
    }

}
