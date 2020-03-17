package com.kenilt.helper

import java.io.ByteArrayOutputStream
import java.io.File


/**
 * Created by thangnguyen on 12/12/18.
 */
object AssetsHelper {

    fun readFile(path: String): String {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(path) ?: return ""
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length = inputStream.read(buffer)
        while (length != -1) {
            result.write(buffer, 0, length)
            length = inputStream.read(buffer)
        }
        return result.toString("UTF-8")
    }

    fun readJsonFile(path: String?): String {
        return readFile("json/$path")
    }

    fun getJson(path : String) : String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}
