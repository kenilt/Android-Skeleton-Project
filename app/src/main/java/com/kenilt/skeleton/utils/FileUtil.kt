package com.kenilt.skeleton.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


/**
 * Created by neal on 2/12/17.
 */

object FileUtil {

    /**
     * Turn drawable resource into byte array.
     *
     * @param context parent context
     * @param id      drawable resource id
     * @return byte array
     */
    fun getFileDataFromDrawable(context: Context, id: Int): ByteArray {
        val drawable = ContextCompat.getDrawable(context, id)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    /**
     * Turn drawable into byte array.
     *
     * @param drawable data
     * @return byte array
     */
    fun getFileDataFromDrawable(drawable: Drawable): ByteArray {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }


    fun getFileFromUri(context: Context, uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
                ?: return null

        cursor.moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val filePath = cursor.getString(columnIndex)
        cursor.close()

        return File(filePath)
    }

    // REMEMBER request permission before use this function
    fun writeStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }

    // REMEMBER request permission before use this function
    fun createAFile(fileName: String): File? {
        var file: File? = null
        try {
            val rootPath: String = Environment.getExternalStorageDirectory()
                    .absolutePath + "/Skeleton/"
            val root = File(rootPath)
            if (!root.exists()) {
                root.mkdirs()
            }
            file = File(rootPath + fileName)
            if (file.exists()) {
                file.delete()
            }
            file.parentFile?.mkdirs()
            file.createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }

}
