package com.kenilt.skeleton.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by neal on 2/16/17.
 */

object EditTextUtil {

    fun focus(editText: EditText) {
        editText.requestFocus()
        val imm = editText.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun dismissKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.applicationWindowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun setEditTextLimitByWords(editText: EditText, wordLimit: Int) {
        fun countWords(s: String): Int {
            val trim = s.trim()
            if (trim.isEmpty())
                return 0
            return trim.split("\\s+".toRegex()).size // separate string around spaces
        }

        var filter: InputFilter? = null

        fun setCharLimit(et: EditText, max: Int) {
            filter = InputFilter.LengthFilter(max)
            et.filters = arrayOf(filter)
        }

        fun removeFilter(et: EditText) {
            if (filter != null) {
                et.filters = arrayOfNulls(0)
                filter = null
            }
        }

        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                val wordsLength = countWords(input)
                if (input.lastOrNull() == ' ' && wordsLength >= wordLimit) {
                    setCharLimit(editText, editText.text.length)
                } else if (editText.filters.isNotEmpty()) {
                    removeFilter(editText)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}
