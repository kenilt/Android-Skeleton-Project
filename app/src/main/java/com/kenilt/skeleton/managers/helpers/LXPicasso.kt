package com.kenilt.skeleton.managers.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView
import com.kenilt.skeleton.utils.Utils
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Created by thangnguyen on 12/14/17.
 */

object LXPicasso {
    @SuppressLint("StaticFieldLeak")
    @Volatile
    private lateinit var picasso: Picasso

    fun init(context: Context) {
        picasso = Picasso.Builder(context)
                .build()
    }

    fun load(imagePath: String?): RequestCreator {
        var path = imagePath
        if (path != null && path.trim().isEmpty()) {
            path = null
        }
        return picasso.load(path)
    }

    fun loadFromLowToHigh(imageView: ImageView, lowImageUrl: String, highImageUrl: String) {
        if (!TextUtils.isEmpty(lowImageUrl) && imageView.drawable == null) {
            load(lowImageUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            load(highImageUrl)
                                    .placeholder(imageView.drawable)
                                    .into(imageView)
                        }

                        override fun onError(e: Exception?) {
                            load(highImageUrl).into(imageView)
                        }
                    })
        } else {
            load(highImageUrl).into(imageView)
        }
    }

    fun load(drawable: Int): RequestCreator {
        return picasso.load(drawable)
    }

    fun load(uri: Uri?): RequestCreator {
        return picasso.load(uri)
    }

    fun loadResize(imagePath: String?, imageView: ImageView) {
        val requestCreator = load(imagePath)
        if (imagePath?.contains("original") == true) {
            requestCreator.resize(Utils.screenWidth, 0)
        }
        requestCreator.into(imageView)
    }
}
