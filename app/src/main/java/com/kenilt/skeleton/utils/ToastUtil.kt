package com.kenilt.skeleton.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.kenilt.skeleton.R
import com.kenilt.skeleton.extension.isNotNullNorEmpty
import kotlinx.android.synthetic.main.view_add_to_cart_toast.view.*


/**
 * Created by thangnguyen on 3/21/19.
 */
object ToastUtil {
    fun showAddToCartSuccessToast(activity: Activity, boxName: String? = null) {
        val message = if (boxName.isNotNullNorEmpty()) {
            activity.getString(R.string.just_add_box_to_cart_success, boxName)
        } else {
            activity.getString(R.string.just_add_to_cart_success)
        }
        showCartSuccessToast(activity, message)
    }

    private fun showCartSuccessToast(activity: Activity, message: String) {
        if (activity.isFinishing) return

        val inflater = activity.layoutInflater
        val toastLayout = inflater.inflate(R.layout.view_add_to_cart_toast, null)

        toastLayout.custom_toast_message.text = message

        val toast = Toast(activity.applicationContext)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastLayout
        toast.setGravity(Gravity.TOP.or(Gravity.FILL_HORIZONTAL), 0, 0)
        toast.show()
    }

    fun showNetworkErrorToast(context: Context) {
        if (context !is Activity || !context.isFinishing) {
            val toast = Toast.makeText(context,
                    "${context.getString(R.string.mgs_warning_no_interner)}\n${context.getString(R.string.mgs_check_and_try_again)}",
                    Toast.LENGTH_SHORT)
            val view = toast.view.findViewById<TextView>(android.R.id.message)
            view?.let {
                view.gravity = Gravity.CENTER
            }
            toast.show()
        }
    }
}
