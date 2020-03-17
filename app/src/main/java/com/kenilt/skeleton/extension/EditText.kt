package com.kenilt.skeleton.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter

/**
 * Created by thangnguyen on 2019-06-12.
 */
fun EditText.focusEnd() {
    this.requestFocus()
    this.setSelection(this.text.length)
}

fun EditText.enterActionDown(action: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_NEXT
                || actionId == EditorInfo.IME_ACTION_SEND
                || actionId == EditorInfo.IME_ACTION_DONE
                || event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
            action()
            return@setOnEditorActionListener true
        }
        // Return true if you have consumed the action, else false.
        return@setOnEditorActionListener false
    }
}

fun EditText.onTextChanged(action: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
    this.addTextChangedListener(object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            action(s, start, before, count)
        }
    })
}

@BindingAdapter("onEditorAction")
fun setEditorActionListener(editText: EditText, action: () -> Unit) {
    editText.enterActionDown(action)
}
