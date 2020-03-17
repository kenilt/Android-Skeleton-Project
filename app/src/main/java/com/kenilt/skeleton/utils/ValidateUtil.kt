package com.kenilt.skeleton.utils

import androidx.core.util.PatternsCompat

/**
 * Created by thanh.nguyen on 11/16/2016.
 */

object ValidateUtil {

    fun isValidName(fullName: String?): Boolean {
        return !fullName.isNullOrEmpty() && fullName.length >= 5
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhoneNumber(phone: String): Boolean {
        val length = phone.length
        return length in 10..11
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}
