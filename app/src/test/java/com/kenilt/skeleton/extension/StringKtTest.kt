package com.kenilt.skeleton.extension

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by thangnguyen on 2019-06-20.
 */
class StringKtTest {

    @Test
    fun getValue_whenNull_returnEmptyString() {
        val data: String? = null
        assertEquals("", data.value)
    }

    @Test
    fun getValue_whenNotNull_returnData() {
        val data: String? = "data"
        assertEquals("data", data.value)
    }

    @Test
    fun toURI_whenNull() {
        val data: String? = null
        assertEquals(null, data.toURI())
    }

    @Test
    fun toURI_whenEmpty() {
        val data: String? = ""
        assertEquals(null, data.toURI())
    }

    @Test
    fun toJSONObject_whenNull() {
        val data: String? = null
        assertEquals(null, data.toJSONObject())
    }

    @Test
    fun toJSONObject_whenEmpty() {
        val data: String? = ""
        assertEquals(null, data.toJSONObject())
    }

    @Test
    fun fixUnExpectedSpace_whenEmptyString_returnData() {
        val data = ""
        assertEquals("", data.fixUnExpectedSpace())
    }

    @Test
    fun fixUnExpectedSpace_whenStringWithTab_returnDataWithoutTab() {
        val data = "aa\taa"
        assertEquals("aaaa", data.fixUnExpectedSpace())
    }

    @Test
    fun fixUnExpectedSpace_whenStringData_returnDataWithoutTabAndRN() {
        val data = "a\ta\r\naa"
        assertEquals("aa\naa", data.fixUnExpectedSpace())
    }

    @Test
    fun removeAllLineBreaks_whenEmptyString_returnData() {
        val data = ""
        assertEquals("", data.removeAllLineBreaks())
    }

    @Test
    fun removeAllLineBreaks_whenDataWithTab_returnDataWithNoLineBreak() {
        val data = "a\ta"
        assertEquals("aa", data.removeAllLineBreaks())
    }

    @Test
    fun removeAllLineBreaks_whenDataWithTabAndBreak_returnDataWithNoLineBreak() {
        val data = "a\ta\na\r\na"
        assertEquals("aaaa", data.removeAllLineBreaks())
    }

    @Test
    fun isNotNullNorEmpty_whenNull_returnFalse() {
        val data: String? = null
        assertEquals(false, data.isNotNullNorEmpty())
    }

    @Test
    fun isNotNullNorEmpty_whenEmpty_returnFalse() {
        val data: String? = ""
        assertEquals(false, data.isNotNullNorEmpty())
    }

    @Test
    fun isNotNullNorEmpty_whenNotEmpty_returnTrue() {
        val data: String? = "aa"
        assertEquals(true, data.isNotNullNorEmpty())
    }

    @Test
    fun correctSlug_whenNull() {
        val data: String? = null
        assertEquals(null, data?.correctSlug())
    }

    @Test
    fun correctSlug_whenNotContainUnderscore() {
        val data: String? = "test"
        assertEquals("test", data?.correctSlug())
    }

    @Test
    fun correctSlug_whenContainsUnderscore() {
        val data: String? = "test_12"
        assertEquals("test", data?.correctSlug())
    }
}
