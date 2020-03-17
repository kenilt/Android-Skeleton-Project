package com.kenilt.skeleton.extension

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by thangnguyen on 2019-06-20.
 */
class BooleanKtTest {

    @Test
    fun isTrue_whenNull_returnFalse() {
        val data: Boolean? = null
        assertEquals(false, data.isTrue)
    }

    @Test
    fun isTrue_whenFalse_returnFalse() {
        val data: Boolean? = false
        assertEquals(false, data.isTrue)
    }

    @Test
    fun isTrue_whenTrue_returnTrue() {
        val data: Boolean? = true
        assertEquals(true, data.isTrue)
    }

    @Test
    fun isFalse_whenNull_returnTrue() {
        val data: Boolean? = null
        assertEquals(true, data.isFalse)
    }

    @Test
    fun isFalse_whenFalse_returnTrue() {
        val data: Boolean? = false
        assertEquals(true, data.isFalse)
    }

    @Test
    fun isFalse_whenTrue_returnFalse() {
        val data: Boolean? = true
        assertEquals(false, data.isFalse)
    }

}
