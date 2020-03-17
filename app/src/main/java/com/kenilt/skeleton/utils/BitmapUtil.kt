package com.kenilt.skeleton.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by neal on 2/12/17.
 */

object BitmapUtil {

    private const val MAX_SIZE_AVATAR = 400
    private const val MAX_SIZE_DEFAULT = 1536

    fun getImageUri(context: Context, bm: Bitmap, scale: Int, title: String): Uri? {
        val bytes = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, scale, bytes)
        val path = MediaStore.Images.Media
                .insertImage(context.contentResolver, bm, title, null)
        var uri: Uri? = null
        try {
            uri = Uri.parse(path)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return uri
    }

    fun resizeBitmap(bm: Bitmap, maxSize: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        var scale = 1f
        if (height > maxSize) {
            scale = maxSize.toFloat() / height.toFloat()
        }

        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scale, scale)

        // "RECREATE" THE NEW BITMAP

        return Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false)
    }

    fun getSquareBitmap(bm: Bitmap): Bitmap {
        val result: Bitmap
        val width = bm.width
        val height = bm.height

        result = if (width >= height) {
            Bitmap.createBitmap(bm,
                    width / 2 - height / 2, 0,
                    height, height
            )
        } else {
            Bitmap.createBitmap(bm,
                    0, height / 2 - width / 2,
                    width, width
            )
        }

        return result
    }

    fun getResizedBitmap(bm: Bitmap): Bitmap {
        return resizeBitmap(bm, MAX_SIZE_DEFAULT)
    }

    fun getResizedAvatar(bm: Bitmap): Bitmap {
        return resizeBitmap(bm, MAX_SIZE_AVATAR)
    }

    fun getBitmapFromFile(file: File?): Bitmap? {
        return if (file != null && file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else null

    }

    fun getBitmapFromFileWithOrientation(file: File?): Bitmap? {
        if (file != null && file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)

            val rotation = UriUtil.getRotation(file.path)
            if (rotation.toFloat() != 0f) {
                val matrix = Matrix()
                matrix.preRotate(rotation.toFloat())

                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            }

            return bitmap
        }

        return null
    }

}
