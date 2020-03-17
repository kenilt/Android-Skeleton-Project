package com.kenilt.skeleton.utils

import android.util.Base64

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES256Cipher {

    fun getRandomAesCryptIv(): ByteArray {
        val randomBytes = ByteArray(16)
        SecureRandom().nextBytes(randomBytes)

        return IvParameterSpec(randomBytes).iv
    }

    fun getAesCryptKey(beginKey: String): ByteArray {
        return try {
            val sha256Hash = MessageDigest.getInstance("SHA-256")
            sha256Hash.update(beginKey.toByteArray(StandardCharsets.UTF_8))

            sha256Hash.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()

            ByteArray(0)
        }

    }

    /**
     * @param encodedIV is Base64 encoded of a string which has length is 16
     */
    fun getAesCryptIv(encodedIV: String): ByteArray {
        return try {
            val decodeData = Base64.decode(encodedIV.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
            return IvParameterSpec(decodeData).iv
        } catch (ex: Exception) {
            ByteArray(0)
        }
    }

    fun encrypt(aesCryptKey: ByteArray, aesCryptIv: ByteArray, plainText: String): String {
        return try {
            val ivSpec = IvParameterSpec(aesCryptIv)
            val newKey = SecretKeySpec(aesCryptKey, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec)

            Base64.encodeToString(cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8)), Base64.DEFAULT)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }

    fun decrypt(aesCryptKey: ByteArray, aesCryptIv: ByteArray, cipherText: String): String {
        return try {
            val ivSpec = IvParameterSpec(aesCryptIv)
            val newKey = SecretKeySpec(aesCryptKey, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec)

            String(cipher.doFinal(Base64.decode(cipherText, Base64.DEFAULT)), StandardCharsets.UTF_8)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }
}
