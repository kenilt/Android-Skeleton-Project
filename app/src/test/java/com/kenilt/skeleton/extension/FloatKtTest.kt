package com.kenilt.skeleton.extension

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by thangnguyen on 2019-06-20.
 */
class FloatKtTest {

    @Test
    fun getValue_whenNull_returnZero() {
        val data: Float? = null
        assertEquals(0f, data.value)
    }

    @Test
    fun getValue_whenNotNull_returnData() {
        val data: Float? = 2.3f
        assertEquals(data, data.value)
    }

    @Test
    fun format_when0Digit_returnData() {
        val data = 9.1f
        assertEquals("9", data.format(0))
    }

    @Test
    fun format_when1DigitRoundEqual_returnDataWith1Digit() {
        val data = 9.12f
        assertEquals("9.1", data.format(1))
    }

    @Test
    fun format_when1DigitRoundUpper_returnDataWith1Digit() {
        val data = 9.16f
        assertEquals("9.2", data.format(1))
    }

    @Test
    fun format_when5DigitLongerThanData_returnDataWith5Digit() {
        val data = 9.16f
        assertEquals("9.16000", data.format(5))
    }

    @Test
    fun orElse_whenNull_returnDefault() {
        val data: Float? = null
        assertEquals(1f, data orElse 1f)
    }

    @Test
    fun orElse_whenZero_returnDefault() {
        val data: Float? = 0f
        assertEquals(1f, data orElse 1f)
    }

    @Test
    fun orElse_whenNotNull_returnData() {
        val data: Float? = 2f
        assertEquals(2f, data orElse 1f)
    }

    @Test
    fun orElseBlock_whenNull_returnDefault() {
        val data: Float? = null
        assertEquals(1f, data orElse { 1f })
    }

    @Test
    fun orElseBlock_whenZero_returnDefault() {
        val data: Float? = 0f
        assertEquals(1f, data orElse { 1f })
    }

    @Test
    fun orElseBlock_whenNotNull_returnData() {
        val data: Float? = 2f
        assertEquals(2f, data orElse { 1f })
    }

}
