package com.kenilt.skeleton.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.kenilt.skeleton.R
import com.kenilt.skeleton.managers.helpers.ControllerHelper
import com.kenilt.skeleton.ui.custom.ProgressDialog
import com.kenilt.skeleton.utils.KeyboardUtils
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by thanh.nguyen on 11/7/16.
 */

abstract class BaseActivity<T : ViewDataBinding> : DaggerAppCompatActivity() {

    private var isControllerComponentUsed = false
    protected lateinit var viewDataBinding: T
    private var hasAuth = false

    /**
     * setup content layout
     *
     * @return layout id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * A dialog showing a progress indicator and an optional text message or
     * view.
     */
    protected var progressDialog: Dialog? = null

    /**
     * Showing a quick little message for the user. It's will be cancel when
     * finish activity.
     */
    private var toast: Toast? = null

    /**
     * init for data
     */
    protected abstract fun init()

    /**
     * editFeedback screen
     */
    protected open fun startScreen() {}

    /**
     * resume screen
     */
    protected open fun resumeScreen() {}

    /**
     * pause screen
     */
    protected open fun pauseScreen() {}

    /**
     * destroy screen
     */
    protected open fun destroyScreen() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = DataBindingUtil.setContentView<T>(this, getLayoutId())
        if (contentView != null) {
            viewDataBinding = contentView
        }

        if (shouldCheckLoggedStateChange()) {
            hasAuth = ControllerHelper.hasToken()
        }
        init()
    }

    override fun onStart() {
        super.onStart()
        startScreen()
    }

    override fun onResume() {
        super.onResume()
        System.gc()
        System.runFinalization()
        resumeScreen()

        if (shouldCheckLoggedStateChange() && !hasAuth && ControllerHelper.hasToken()) {
            hasAuth = ControllerHelper.hasToken()
            resumeAfterLogin()
        }
    }

    override fun onPause() {
        super.onPause()
        pauseScreen()
    }

    override fun onDestroy() {
        super.onDestroy()

        System.gc()
        System.runFinalization()
        destroyScreen()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view = currentFocus
        if (view is EditText) {
            val scoops = IntArray(2)
            view.getLocationOnScreen(scoops)
            val x = event.rawX + view.left - scoops[0]
            val y = event.rawY + view.top - scoops[1]

            if (event.action == MotionEvent.ACTION_UP
                    && (x < view.left || x >= view.right || y < view.top || y > view.bottom)) {
                KeyboardUtils.forceHideKeyboard(this, view)
            }
        }
        return try {
            super.dispatchTouchEvent(event)
        } catch (ex: NullPointerException) {    // prevent crash on youtube api, sometimes
            false
        }
    }

    open fun shouldCheckLoggedStateChange(): Boolean {
        return false
    }

    open fun resumeAfterLogin() { }

    /**
     * @param msgResId
     * @param keyListener
     */
    @JvmOverloads
    fun showProgress(msgResId: Int = R.string.loading_data,
                     keyListener: DialogInterface.OnKeyListener? = null) {

        if (isFinishing) {
            return
        }

        if (progressDialog?.isShowing == true) {
            return
        }
        progressDialog = ProgressDialog.progressDialog(this, msgResId)
        progressDialog?.setCancelable(false)

        if (keyListener != null) {
            progressDialog?.setOnKeyListener(keyListener)
        } else {
            progressDialog?.setCancelable(false)
        }
        progressDialog?.show()
    }

    /**
     * @param isCancel
     */
    fun setCancelableProgress(isCancel: Boolean) {
        progressDialog?.setCancelable(isCancel)
    }

    /**
     * cancel progress dialog.
     */
    fun dismissProgress() {
        if (isFinishing) {
            return
        }
        progressDialog?.dismiss()
        progressDialog = null
    }

    /**
     * Show message by the Toast object, it will be canceled when finish this
     * activity.
     *
     * @param msg message want to show
     */
    @SuppressLint("ShowToast")
    fun showToastMessage(msg: CharSequence) {
        if (isFinishing)
            return
        if (toast == null) {
            toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
        }

        val toast = toast ?: return
        // Cancel last message toast
        if (toast.view.isShown) {
            toast.cancel()
        }
        toast.setText(msg)
        toast.show()
    }

    /**
     * Show message by the Toast object, it will be canceled when finish this
     * activity.
     *
     * @param resId id of message wants to show
     */
    @SuppressLint("ShowToast")
    fun showToastMessage(resId: Int) {
        if (isFinishing) {
            return
        }
        if (toast == null) {
            toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
        }

        val toast = toast ?: return
        if (toast.view.isShown) {
            toast.cancel()
        }
        toast.setText(getString(resId))
        toast.show()
    }

    /**
     * Cancel toast.
     */
    fun cancelToast() {
        if (toast?.view?.isShown == true) {
            toast?.cancel()
        }
    }

    /**
     * Show toast message, the message is not canceled when finish this
     * activity.
     *
     * @param msg message wants to show
     */
    fun showToastMessageNotRelease(msg: CharSequence) {
        if (isFinishing) {
            return
        }
        val toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
        if (toast != null) {
            // Cancel last message toast
            if (toast.view.isShown) {
                toast.cancel()
            }
            toast.setText(msg)
            toast.show()
        }
    }

    /**
     * Show toast message, the message is not canceled when finish this
     * activity.
     *
     * @param resId message wants to show
     */
    fun showToastMessageNotRelease(resId: Int) {
        if (isFinishing) {
            return
        }
        val toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
        if (toast != null) {
            if (!toast.view.isShown) {
                toast.cancel()
            }
            toast.setText(getString(resId))
            toast.show()
        }
    }
}
