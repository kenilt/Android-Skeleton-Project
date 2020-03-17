package com.kenilt.skeleton.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.kenilt.skeleton.R
import com.kenilt.skeleton.managers.helpers.ControllerHelper
import com.kenilt.skeleton.ui.custom.ProgressDialog


/**
 * Created by thanh.nguyen on 11/7/16.
 */

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    protected lateinit var viewDataBinding: T
    protected lateinit var baseActivity: BaseActivity<*>
    private var hasAuth = false

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

    protected abstract fun init(view: View?)

    protected abstract fun getLayoutId(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        val activity = activity
        try {
            baseActivity = activity as BaseActivity<*>
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement FullImageActivity")
        }
    }

    open fun screenResume() {}

    open fun destroyScreen() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (shouldCheckLoggedStateChange()) {
            hasAuth = ControllerHelper.hasToken()
        }
        init(view)
    }

    override fun onResume() {
        super.onResume()
        screenResume()

        if (shouldCheckLoggedStateChange() && !hasAuth && ControllerHelper.hasToken()) {
            hasAuth = ControllerHelper.hasToken()
            resumeAfterLogin()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
        destroyScreen()
    }

    override fun onPause() {
        dismissProgress()
        super.onPause()
    }

    open fun shouldCheckLoggedStateChange(): Boolean {
        return false
    }

    open fun resumeAfterLogin() { }

    protected fun isViewBindingInitialized() = this::viewDataBinding.isInitialized

    /**
     * @param msgResId
     * @param keyListener
     */
    @JvmOverloads
    fun showProgress(msgResId: Int = R.string.loading_data,
                     keyListener: DialogInterface.OnKeyListener? = null) {
        if (!isAdded) {
            return
        }

        if (progressDialog?.isShowing == true) {
            return
        }
        val context = activity ?: return
        progressDialog = ProgressDialog.progressDialog(context, msgResId)

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
        if (!isAdded) {
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
        if (!isAdded) {
            return
        }
        if (toast == null) {
            toast = Toast.makeText(activity, "",
                    Toast.LENGTH_LONG)
        }

        toast?.let { toast ->
            // Cancel last message toast
            if (toast.view.isShown) {
                toast.cancel()
            }
            toast.setText(msg)
            toast.show()
        }
    }

    /**
     * Show message by the Toast object, it will be canceled when finish this
     * activity.
     *
     * @param resId id of message wants to show
     */
    @SuppressLint("ShowToast")
    fun showToastMessage(resId: Int) {
        if (!isAdded)
            return
        if (toast == null) {
            toast = Toast.makeText(activity, "",
                    Toast.LENGTH_LONG)
        }

        toast?.let { toast ->
            if (toast.view.isShown) {
                toast.cancel()
            }
            toast.setText(getString(resId))
            toast.show()
        }
    }

    /**
     * Cancel toast.
     */
    fun cancelToast() {
        if (toast != null && toast?.view?.isShown == true) {
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
        if (!isAdded) {
            return
        }
        val toast = Toast.makeText(activity, "",
                Toast.LENGTH_LONG)
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
        if (!isAdded) {
            return
        }
        val toast = Toast.makeText(activity, "",
                Toast.LENGTH_LONG)
        if (toast != null) {
            if (!toast.view.isShown) {
                toast.cancel()
            }
            toast.setText(getString(resId))
            toast.show()
        }
    }
}
