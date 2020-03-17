package com.kenilt.skeleton.ui.custom

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.crashlytics.android.Crashlytics

/**
 * Created by thangnguyen on 3/12/19.
 */
class StaggeredGridWorkaroundLayoutManager(spanCount: Int, orientation: Int):
        StaggeredGridLayoutManager(spanCount, orientation) {
    override fun onScrollStateChanged(state: Int) {
        try {
            super.onScrollStateChanged(state)
        } catch (ex: IndexOutOfBoundsException) {
            Crashlytics.logException(ex)
        }
    }
}
