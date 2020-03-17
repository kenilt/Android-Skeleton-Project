package com.kenilt.skeleton.ui.custom.dialog

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.kenilt.skeleton.R
import com.kenilt.skeleton.databinding.DialogRatingAppBinding
import com.kenilt.skeleton.managers.helpers.ControllerHelper
import com.kenilt.skeleton.utils.InstantClock


object RatingAppDialog {

    fun checkAndShowRatingDialog(context: Context) {
        if (isShouldShowDialog()) {
            show(context)
        }
    }

    fun isShouldShowDialog(): Boolean {
        // show only when should show is true
        if (!ControllerHelper.getShouldShowRating()) return false

        // last showing dialog is longer than 2 weeks
        val now = InstantClock.currentTimeMillis()
        val weekInMillis = 7 * 24 * 60 * 60
        if (now - ControllerHelper.getLastRatingTime() < 2 * weekInMillis) return false

        // no crashed in 4 weeks
        if (now - ControllerHelper.getLastCrashedTime() < 4 * weekInMillis) return false

        // like at least 3 times
        if (ControllerHelper.getLikeCount() < 3) return false

        // has at least 1 rating with 5 stars
        if (ControllerHelper.get5StarRatingCount() < 1) return false

        // has no ratings which < 4 stars
        if (ControllerHelper.getBadRatingCount() > 0) return false

        // open app at least 3 times
        if (ControllerHelper.getOpenAppCount() < 3) return false

        return true
    }

    private fun show(context: Context): Dialog {
        val binding = DataBindingUtil.inflate<DialogRatingAppBinding>(LayoutInflater.from(context), R.layout.dialog_rating_app, null, false)

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(binding.root)

        binding.ratingAppTxtLater.setOnClickListener {
            dialog.dismiss()
        }
        binding.ratingAppTxtDisagree.setOnClickListener {
            ControllerHelper.setShouldShowRating(false)
            dialog.dismiss()
        }
        binding.ratingAppTxtAgree.setOnClickListener {
            openPlayStoreForRating(context)
            ControllerHelper.setShouldShowRating(false)
            dialog.dismiss()
        }

        dialog.show()

        ControllerHelper.putLastRatingTime(InstantClock.currentTimeMillis())
//        (context as IGALogger).sendGAEventOfCategory(Constant.GENERAL, "Show suggest rating dialog")

        return dialog
    }

    private fun openPlayStoreForRating(context: Context) {
        val uri: Uri = Uri.parse("market://details?id=" + context.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            context.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName)))
        }
    }
}
