package com.kenilt.skeleton.utils

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


/**
 * https://gist.github.com/spiritinlife/6bd8e2bcad6576dcf65c
 * Created by Kenilt Nguyen on 9/4/18.
 */
object HMAC {

    fun hmacDigest(msg: String, keyString: String, algo: String): String {
        val byteMessage = msg.toByteArray(charset("UTF-8"))
        return hmacDigest(byteMessage, keyString, algo)
    }

    fun hmacDigest(msg: ByteArray, keyString: String, algo: String): String {
        var digest = ""
        try {
            val key = SecretKeySpec(keyString.toByteArray(charset("UTF-8")), algo)
            val mac = Mac.getInstance(algo)
            mac.init(key)

            // Base64.NO_WRAP   Encoder flag bit to omit all line terminators (i.e., the output will be on one long line).
            // If we do not use Base64.NO_WRAP the string will not be correct
            digest = Base64.encodeToString(mac.doFinal(msg), Base64.NO_WRAP)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return digest
    }
}
