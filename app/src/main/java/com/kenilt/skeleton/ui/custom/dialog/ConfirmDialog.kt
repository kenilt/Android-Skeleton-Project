package com.kenilt.skeleton.ui.custom.dialog

import android.app.Dialog
import android.content.Context
import android.view.View.OnClickListener
import com.kenilt.skeleton.R
import com.kenilt.skeleton.ui.helper.DialogUtil

class ConfirmDialog(private var context: Context) {

    var title: String = ""
    var description: String = ""
    var accept: String = ""
    var cancel: String = ""
    var onAccept: ((dialog: Dialog?) -> Unit)? = null
    var onCancel: ((dialog: Dialog?) -> Unit)? = null

    fun title(title: String) = apply { this.title = title }

    fun title(title: Int) = apply { this.title = context.getString(title) }

    fun description(description: String) = apply { this.description = description }

    fun description(description: Int) = apply { this.description = context.getString(description) }

    fun accept(accept: String) = apply { this.accept = accept }

    fun accept(accept: Int) = apply { this.accept = context.getString(accept) }

    fun cancel(cancel: String) = apply { this.cancel = cancel }

    fun cancel(cancel: Int) = apply { this.cancel = context.getString(cancel) }

    fun onAccept(onAccept: (dialog: Dialog?) -> Unit) = apply { this.onAccept = onAccept }

    fun onCancel(onCancel: (dialog: Dialog?) -> Unit) = apply { this.onCancel = onCancel }

    fun show() {
        if (title.isEmpty()) {
            title = context.getString(R.string.announcement)
        }
        if (description.isEmpty()) {
            description = context.getString(R.string.mgs_check_and_try_again)
        }
        if (accept.isEmpty()) {
            accept = context.getString(R.string.accept)
        }
        if (cancel.isEmpty()) {
            cancel = context.getString(R.string.cancel)
        }

        var dialog: Dialog? = null
        val acceptAction = OnClickListener {
            onAccept?.invoke(dialog)
        }
        val cancelAction = if (onCancel != null) OnClickListener {
            onCancel?.invoke(dialog)
        } else null
        dialog = DialogUtil.showConfirmDialog(context, title, description, accept, cancel, acceptAction, cancelAction)
    }
}
