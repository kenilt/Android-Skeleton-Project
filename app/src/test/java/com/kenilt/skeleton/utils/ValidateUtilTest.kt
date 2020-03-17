package com.kenilt.skeleton.utils

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by thangnguyen on 3/4/19.
 */
class ValidateUtilTest {

    @Test
    fun isValidName_whenValidName_returnTrue() {
        val res = ValidateUtil.isValidName("Valid Name")
        assertEquals(true, res)
    }

    @Test
    fun isValidName_whenNullName_returnFalse() {
        val res = ValidateUtil.isValidName(null)
        assertEquals(false, res)
    }

    @Test
    fun isValidName_whenEmptyName_returnFalse() {
        val res = ValidateUtil.isValidName("")
        assertEquals(false, res)
    }

    @Test
    fun isValidName_whenShortName_returnFalse() {
        val res = ValidateUtil.isValidName("Name")
        assertEquals(false, res)
    }

    @Test
    fun isValidName_whenNameLength5_returnTrue() {
        val res = ValidateUtil.isValidName("Short")
        assertEquals(true, res)
    }

    @Test
    fun isValidName_whenSpecialName_returnTrue() {
        val res = ValidateUtil.isValidName("!@#!@$@!#!$! !@$! @@#!%#")
        assertEquals(true, res)
    }

    @Test
    fun isValidEmail_whenEmailRightFormat() {
        val res = ValidateUtil.isValidEmail("nga@gmail.com")
        assertEquals(true, res)
    }

    @Test
    fun isValidPhoneNumber_ValidPhoneNumber_True() {
        val res = ValidateUtil.isValidPhoneNumber("0123456789")
        assertEquals(true, res)
    }

    @Test
    fun isValidPhoneNumber_PhoneNumberIsShort_False() {
        val res = ValidateUtil.isValidPhoneNumber("012345678")
        assertEquals(false, res)
    }

    @Test
    fun isValidPhoneNumber_PhoneNumberIsLong_False() {
        val res = ValidateUtil.isValidPhoneNumber("012345678901")
        assertEquals(false, res)
    }

    @Test
    fun isValidPassword_PasswordLengthIs8_True() {
        val res = ValidateUtil.isValidPassword("abcdefgh")
        assertEquals(true, res)
    }

    @Test
    fun isValidPassword_PasswordLengthIs9_True() {
        val res = ValidateUtil.isValidPassword("abcdefghi")
        assertEquals(true, res)
    }

    @Test
    fun isValidPassword_PasswordLengthIs7_False() {
        val res = ValidateUtil.isValidPassword("abcdefg")
        assertEquals(false, res)
    }

    @Test
    fun isValidPassword_EmptyPassword_False() {
        val res = ValidateUtil.isValidPassword("")
        assertEquals(false, res)
    }
}
