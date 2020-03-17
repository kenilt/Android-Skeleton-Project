package com.kenilt.skeleton.ui.base

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kenilt.skeleton.utils.LXLog


/**
 * Created by Kenilt Nguyen on 9/12/18.
 */
open class BaseDialogFragment: DialogFragment() {

    protected lateinit var baseActivity: BaseActivity<*>

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

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commit()
        } catch (e: IllegalStateException) {
            LXLog.e("BaseDialogFragment", "Can not show dialog", e)
        }

    }
}
