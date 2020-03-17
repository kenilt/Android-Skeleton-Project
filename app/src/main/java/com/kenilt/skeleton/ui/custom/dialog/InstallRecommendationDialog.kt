package com.kenilt.skeleton.ui.custom.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.kenilt.skeleton.R
import com.kenilt.skeleton.databinding.DialogInstallAppSuggestionBinding
import com.kenilt.skeleton.managers.helpers.ControllerHelper
import com.kenilt.skeleton.managers.listeners.BackScreenHandler
import com.kenilt.skeleton.ui.base.BaseDialogFragment
import com.kenilt.skeleton.utils.InstantAppUtil

/**
 * Created by thangnguyen on 3/17/20.
 */
class InstallRecommendationDialog: BaseDialogFragment(), BackScreenHandler {

    private lateinit var binding: DialogInstallAppSuggestionBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_install_app_suggestion, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCanceledOnTouchOutside(false)

        if (arguments?.getBoolean(KEY_FOR_FUNCTION) == true) {
            binding.tvMessage.text = getString(R.string.dialog_install_for_function_message)
        }

        binding.lixiAlertBtnConfirm.setOnClickListener {
            InstantAppUtil.openInstallPrompt(requireActivity())
            dismiss()
        }
        binding.lixiAlertBtnCancel.setOnClickListener { dismiss() }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onBack() {
        ControllerHelper.invitedReferralCode = null
//        sendGAEvent("Close recommendation install app dialog")
        dismiss()
    }

    companion object {
        private const val KEY_FOR_FUNCTION = "KEY_FOR_FUNCTION"

        fun newInstance(isForFunction: Boolean = false): InstallRecommendationDialog {
            return InstallRecommendationDialog().apply {
                arguments = Bundle().apply {
                    putBoolean(KEY_FOR_FUNCTION, isForFunction)
                }
            }
        }

        fun show(activity: FragmentActivity): InstallRecommendationDialog {
            val dialog = newInstance()
            dialog.show(activity.supportFragmentManager, "install-recommendation-dialog")
            return dialog
        }

        fun showForFunction(activity: FragmentActivity): InstallRecommendationDialog {
            val dialog = newInstance(true)
            dialog.show(activity.supportFragmentManager, "install-recommendation-dialog")
            return dialog
        }
    }
}
