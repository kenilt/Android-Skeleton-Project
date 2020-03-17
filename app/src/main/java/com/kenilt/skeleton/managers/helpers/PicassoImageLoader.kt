package com.kenilt.skeleton.managers.helpers

import android.widget.ImageView
import com.esafirm.imagepicker.features.imageloader.ImageLoader
import com.esafirm.imagepicker.features.imageloader.ImageType
import com.squareup.picasso.MemoryPolicy

/**
 * Created by thangnguyen on 2019-05-31.
 */
class PicassoImageLoader: ImageLoader {
    override fun loadImage(path: String?, imageView: ImageView?, imageType: ImageType?) {
        LXPicasso.load("file://$path")
                .resize(WIDTH, HEIGHT)
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(imageView)
    }

    companion object {
        const val WIDTH = 300
        const val HEIGHT = 300
    }
}
