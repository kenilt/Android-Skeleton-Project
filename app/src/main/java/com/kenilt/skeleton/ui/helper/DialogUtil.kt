package com.kenilt.skeleton.ui.helper

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.kenilt.skeleton.R
import com.kenilt.skeleton.constant.enums.LXAlertStyle
import com.kenilt.skeleton.databinding.DialogDefaultBinding

/**
 * Created by thanh.nguyen on 6/16/16.
 */
object DialogUtil {

    private fun defaultDialog(context: Context, title: String, message: String,
                              style: LXAlertStyle,
                              confirmButtonTitle: String?,
                              cancelButtonTitle: String?,
                              okListener: View.OnClickListener?,
                              cancelListener: View.OnClickListener?): Dialog {
        val binding = DataBindingUtil.inflate<DialogDefaultBinding>(LayoutInflater.from(context), R.layout.dialog_default, null, false)

        binding.title = title
        if (title.isEmpty()) {
            binding.tvTitle.visibility = View.GONE
        }
        binding.message = message

        val isDefault = style === LXAlertStyle.DEFAULT
        binding.lixiAlertViewConfirm.visibility = if (isDefault) View.INVISIBLE else View.VISIBLE
        binding.lixiAlertViewOk.visibility = if (isDefault) View.VISIBLE else View.INVISIBLE

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(binding.root)

        /**
         * If default
         * 1. has listener
         * 2. no listener -> dismiss
         */
        if (isDefault) {
            if (okListener == null) {
                binding.lixiAlertBtnDefaultOk.setOnClickListener { dialog.dismiss() }
            } else {
                binding.lixiAlertBtnDefaultOk.setOnClickListener(okListener)
            }

            if (confirmButtonTitle != null) {
                binding.lixiAlertBtnDefaultOk.text = confirmButtonTitle
            }
        } else {
            if (cancelListener == null) {
                binding.lixiAlertBtnCancel.setOnClickListener { dialog.dismiss() }
            } else {
                binding.lixiAlertBtnCancel.setOnClickListener(cancelListener)
            }

            binding.lixiAlertBtnConfirm.setOnClickListener(okListener)

            if (confirmButtonTitle != null) {
                binding.lixiAlertBtnConfirm.text = confirmButtonTitle
            }

            if (cancelButtonTitle != null) {
                binding.lixiAlertBtnCancel.text = cancelButtonTitle
            }
        }

        return dialog
    }

    fun showErrorDialog(context: Context, title: String, message: String, closeListener: View.OnClickListener): Dialog {
        val dialog = defaultDialog(context, title, message, LXAlertStyle.DEFAULT, null, null, closeListener, null)
        if (context !is Activity || !context.isFinishing) {
            dialog.show()
        }
        return dialog
    }

    fun showErrorDialog(context: Context, title: String, message: String) {
        val dialog = defaultDialog(context, title, message, LXAlertStyle.DEFAULT, null, null, null, null)
        if (context !is Activity || !context.isFinishing) {
            dialog.show()
        }
    }

    fun showErrorDialog(context: Context, title: Int, message: Int) {
        val strTitle = context.resources.getString(title)
        val strMsg = context.resources.getString(message)
        showErrorDialog(context, strTitle, strMsg)
    }

    fun showNeedCheckInfoDialog(context: Context) {
        showErrorDialog(context, R.string.mgs_unknown_error, R.string.mgs_check_and_try_again)
    }

    fun checkAndShowErrorDialog(activity: Activity?, title: Int, message: Int) {
        if (activity != null && !activity.isFinishing) {
            showErrorDialog(activity, title, message)
        }
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param listener
     * @return
     */
    fun showInformationDialog(context: Context, title: String, message: String, listener: View.OnClickListener?): Dialog {
        val dialog = defaultDialog(context, title, message,
                LXAlertStyle.DEFAULT, null, null,
                listener, null)
        if (context !is Activity || !context.isFinishing) {
            dialog.show()
        }

        return dialog
    }

    fun showInformationDialog(context: Context, title: Int, message: Int, listener: View.OnClickListener? = null): Dialog {
        val strTitle = context.resources.getString(title)
        val strMsg = context.resources.getString(message)

        return showInformationDialog(context, strTitle, strMsg, listener)
    }

    fun showInformationDialog(context: Context, title: Int, message: Int, confirmTitle: Int, listener: View.OnClickListener?): Dialog {
        val strTitle = context.resources.getString(title)
        val strMsg = context.resources.getString(message)
        val strConfirmTitle = context.resources.getString(confirmTitle)

        val dialog = defaultDialog(context, strTitle, strMsg,
                LXAlertStyle.DEFAULT,
                strConfirmTitle, null,
                listener, null)
        if (context !is Activity || !context.isFinishing) {
            dialog.show()
        }

        return dialog
    }

    /**
     * @param context       context
     * @param title         title
     * @param msg           message
     * @param onYesListener yes listener
     * @param onNoListener  no listener
     * @return dialog
     */
    fun showConfirmDialog(context: Context, title: String, msg: String,
                          confirmButtonTitle: String,
                          cancelButtonTitle: String,
                          onYesListener: View.OnClickListener?,
                          onNoListener: View.OnClickListener? = null): Dialog {
        val dialog = defaultDialog(context, title, msg,
                LXAlertStyle.CONFIRMATION,
                confirmButtonTitle, cancelButtonTitle,
                onYesListener, onNoListener)
        if (context !is Activity || !context.isFinishing) {
            dialog.show()
        }

        return dialog
    }

    fun showConfirmDialog(context: Context, title: Int, msg: Int,
                          confirmButtonTitle: Int,
                          cancelButtonTitle: Int,
                          onYesListener: View.OnClickListener?,
                          onNoListener: View.OnClickListener? = null): Dialog {
        val resources = context.resources
        return showConfirmDialog(context,
                resources.getString(title),
                resources.getString(msg),
                resources.getString(confirmButtonTitle),
                resources.getString(cancelButtonTitle),
                onYesListener,
                onNoListener)
    }

    /**
     * @param context          context
     * @param title            title
     * @param msg              message
     * @param onOkListener     ok listener
     * @param onCancelListener cancel listener
     * @return dialog
     */
    fun showTryAgainDialog(context: Context, title: String, msg: String,
                           onOkListener: View.OnClickListener?,
                           onCancelListener: View.OnClickListener?): Dialog {
        val resources = context.resources
        val skip = resources.getString(R.string.skip)
        val tryAgain = resources.getString(R.string.try_again)

        val dialog = defaultDialog(context, title, msg,
                LXAlertStyle.CONFIRMATION,
                tryAgain, skip,
                onOkListener, onCancelListener)
        if (context !is Activity || !context.isFinishing) {
            dialog.show()
        }

        return dialog
    }

    @JvmOverloads
    fun showErrorList(context: Context, errors: List<String>?, title: String = context.getString(R.string.announcement)) {
        if (errors == null || errors.isEmpty()) {
            showNeedCheckInfoDialog(context)
        } else {
            val msg = TextUtils.join("\n", errors)
            showErrorDialog(context, title, msg)
        }
    }

    fun showErrorDialog(context: Context, errorString: String?) {
        if (errorString == null) return
        val title = context.getString(R.string.announcement)
        showErrorDialog(context, title, errorString)
    }
}
