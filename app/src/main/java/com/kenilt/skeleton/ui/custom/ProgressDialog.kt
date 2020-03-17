package com.kenilt.skeleton.ui.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import com.kenilt.skeleton.R
import com.kenilt.skeleton.extension.isNotNullNorEmpty

/**
 * Created by thangnguyen on 2019-09-11.
 */
class ProgressDialog {

    companion object {

        fun progressDialog(context: Context, stringId: Int): Dialog {
            val message = if (stringId != 0) context.getString(stringId) else null
            return progressDialog(context, message)
        }

        fun progressDialog(context: Context, message: String? = null): Dialog {
            val dialog = Dialog(context)
            val view = View.inflate(context, R.layout.dialog_progress, null)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (message.isNotNullNorEmpty()) {
                dialog.findViewById<TextView>(R.id.progress_tvMessage).text = message
            }
            return dialog
        }
    }
}
