package com.kenilt.skeleton.extension

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by thangnguyen on 2019-06-20.
 */
class IntKtTest {

    @Test
    fun getValue_whenNull_returnZero() {
        val data: Int? = null
        assertEquals(0, data.value)
    }

    @Test
    fun getValue_whenNotNull_returnData() {
        val data: Int? = 2
        assertEquals(2, data.value)
    }

    @Test
    fun orElse_whenNull_returnDefault() {
        val data: Int? = null
        assertEquals(1, data orElse 1)
    }

    @Test
    fun orElse_whenZero_returnDefault() {
        val data: Int? = 0
        assertEquals(1, data orElse 1)
    }

    @Test
    fun orElse_whenNotNull_returnData() {
        val data: Int? = 2
        assertEquals(2, data orElse 1)
    }

    @Test
    fun orElseBlock_whenNull_returnDefault() {
        val data: Int? = null
        assertEquals(1, data orElse { 1 })
    }

    @Test
    fun orElseBlock_whenZero_returnDefault() {
        val data: Int? = 0
        assertEquals(1, data orElse { 1 })
    }

    @Test
    fun orElseBlock_whenNotNull_returnData() {
        val data: Int? = 2
        assertEquals(2, data orElse { 1 })
    }

}
