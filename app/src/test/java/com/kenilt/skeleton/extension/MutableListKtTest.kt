package com.kenilt.skeleton.extension

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by thangnguyen on 2019-09-24.
 */
class MutableListKtTest {

    @Test
    fun isNotNullNorEmpty_whenListNull() {
        val list: MutableList<String>? = null
        assertFalse(list.isNotNullNorEmpty())
    }

    @Test
    fun isNotNullNorEmpty_whenListEmpty() {
        val list: MutableList<String>? = ArrayList()
        assertFalse(list.isNotNullNorEmpty())
    }

    @Test
    fun isNotNullNorEmpty_whenListNotEmpty() {
        val list: MutableList<String>? = ArrayList()
        list?.add("Test")
        assertTrue(list.isNotNullNorEmpty())
    }

    @Test
    fun isNotNullNorEmpty_whenNull_returnFalse() {
        val string: String? = null
        assertFalse(string.isNotNullNorEmpty())
    }

    @Test
    fun isNotNullNorEmpty_whenEmpty_returnFalse() {
        val string: String? = ""
        assertFalse(string.isNotNullNorEmpty())
    }

    @Test
    fun isNotNullNorEmpty_whenNotEmpty_returnTrue() {
        val string: String? = "test"
        assertTrue(string.isNotNullNorEmpty())
    }
}
