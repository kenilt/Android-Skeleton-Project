package com.kenilt.skeleton.extension

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by thangnguyen on 2019-06-20.
 */
class LongKtTest {

    @Test
    fun getValue_whenNull_returnZero() {
        val data: Long? = null
        assertEquals(0L, data.value)
    }

    @Test
    fun getValue_whenNotNull_returnData() {
        val data: Long? = 2L
        assertEquals(2L, data.value)
    }

    @Test
    fun toNumberOfDays_when0_return0() {
        val data = 0L
        assertEquals(0L, data.toNumberOfDays())
    }

    @Test
    fun toNumberOfDays_when86399_return0() {
        val data = 86399L
        assertEquals(0L, data.toNumberOfDays())
    }

    @Test
    fun toNumberOfDays_when86400_return1() {
        val data = 86400L
        assertEquals(1L, data.toNumberOfDays())
    }

}
