package com.kenilt.skeleton.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.kenilt.skeleton.R
import kotlinx.android.synthetic.main.view_loading_or_no_item.view.*


/**
 * Created by thangnguyen on 3/13/18.
 */
class FooterLoadingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    enum class FooterState {
        LOADING, DONE, NO_ITEM
    }

    private var state: FooterState? = null

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.view_loading_or_no_item, this, true)
    }

    fun setNoItemText(text: String?) {
        if (text != null) {
            loading_footer_txtNoItem.text = text
        }
    }

    fun setNoItemTextColor(color: Int) {
        loading_footer_txtNoItem.setTextColor(color)
    }

    fun setState(state: FooterState) {
        this.state = state
        when (state) {
            FooterState.LOADING -> {
                loading_footer_rltContainer.visibility = View.VISIBLE
                loading_footer_pgbLoading.visibility = View.VISIBLE
                loading_footer_txtNoItem.visibility = View.GONE
            }
            FooterState.DONE -> loading_footer_rltContainer.visibility = View.GONE
            FooterState.NO_ITEM -> {
                loading_footer_rltContainer.visibility = View.VISIBLE
                loading_footer_pgbLoading.visibility = View.GONE
                loading_footer_txtNoItem.visibility = View.VISIBLE
            }
        }
    }

    fun getState(): FooterState? {
        return state
    }

    fun setShowContent(isShow: Boolean) {
        var visibility = View.VISIBLE
        if (!isShow) {
            visibility = View.GONE
        }
        loading_footer_rltContainer.visibility = visibility
    }

    fun setLoadingMarginTop(marginTop: Int) {
        val layoutParams = loading_footer_pgbLoading.layoutParams as LayoutParams
        layoutParams.topMargin = marginTop
    }
}
