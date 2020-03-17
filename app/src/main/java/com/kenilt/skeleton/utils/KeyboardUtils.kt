package com.kenilt.skeleton.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Created by thangnguyen on 11/29/17.
 */
class KeyboardUtils {

    companion object {
        fun hideKeyboard(activity: Activity) {
            val inputManager = activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // check if no view has focus:
            val view = activity.currentFocus
            if (view != null) {
                inputManager.hideSoftInputFromWindow(view.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        fun forceHideKeyboard(activity: Activity) {
            val view = activity.currentFocus
            if (view != null) {
                forceHideKeyboard(activity, view)
            }
        }

        fun forceHideKeyboard(activity: Activity, view: View) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun showKeyboard(activity: Activity) {
            val m = activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            m.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
        }

        fun showKeyboard(activity: Activity, view: View) {
            view.requestFocus()
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
