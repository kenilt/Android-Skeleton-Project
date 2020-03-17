package com.kenilt.skeleton.utils

import android.content.Context
import com.kenilt.skeleton.R
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*


/**
 * Created by thangnguyen on 12/3/18.
 */
class NumberUtilTest {

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockContext.getString(R.string.format_full_price_vnd)).thenReturn("###,###,### đ")
        Mockito.`when`(mockContext.getString(R.string.normal_number_format)).thenReturn("###,###,###,###")

        Locale.setDefault(Locale.US)
    }

    @Test
    fun formatPrice_UseCharOfZero() {
        val res = NumberUtil.formatPrice(0.0, mockContext)
        assertEquals("0 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf1Number() {
        val res = NumberUtil.formatPrice(9.0, mockContext)
        assertEquals("9 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf3Numbers() {
        val res = NumberUtil.formatPrice(123.0, mockContext)
        assertEquals("123 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf4Numbers() {
        val res = NumberUtil.formatPrice(2345.0, mockContext)
        assertEquals("2,345 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf6Numbers() {
        val res = NumberUtil.formatPrice(345678.0, mockContext)
        assertEquals("345,678 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf7Numbers() {
        val res = NumberUtil.formatPrice(3456789.0, mockContext)
        assertEquals("3,456,789 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf8Numbers() {
        val res = NumberUtil.formatPrice(23456789.0, mockContext)
        assertEquals("23,456,789 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf9Numbers() {
        val res = NumberUtil.formatPrice(123456789.0, mockContext)
        assertEquals("123,456,789 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf10Numbers() {
        val res = NumberUtil.formatPrice(1234567890.0, mockContext)
        assertEquals("1,234,567,890 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf10NumbersNegative() {
        val res = NumberUtil.formatPrice(-1234567890.0, mockContext)
        assertEquals("-1,234,567,890 đ", res)
    }

    @Test
    fun formatPrice_UseCharOf9NumbersNegative() {
        val res = NumberUtil.formatPrice(-234567890.0, mockContext)
        assertEquals("-234,567,890 đ", res)
    }

    @Test
    fun formatNumber_UseCharOfZero() {
        val res = NumberUtil.formatNumber(0.0, mockContext)
        assertEquals("0", res)
    }

    @Test
    fun formatNumber_UseCharOf1Number() {
        val res = NumberUtil.formatNumber(9.0, mockContext)
        assertEquals("9", res)
    }

    @Test
    fun formatNumber_UseCharOf3Numbers() {
        val res = NumberUtil.formatNumber(123.0, mockContext)
        assertEquals("123", res)
    }

    @Test
    fun formatNumber_UseCharOf4Numbers() {
        val res = NumberUtil.formatNumber(2345.0, mockContext)
        assertEquals("2,345", res)
    }

    @Test
    fun formatNumber_UseCharOf6Numbers() {
        val res = NumberUtil.formatNumber(345678.0, mockContext)
        assertEquals("345,678", res)
    }

    @Test
    fun formatNumber_UseCharOf7Numbers() {
        val res = NumberUtil.formatNumber(3456789.0, mockContext)
        assertEquals("3,456,789", res)
    }

    @Test
    fun formatNumber_UseCharOf8Numbers() {
        val res = NumberUtil.formatNumber(23456789.0, mockContext)
        assertEquals("23,456,789", res)
    }

    @Test
    fun formatNumber_UseCharOf9Numbers() {
        val res = NumberUtil.formatNumber(123456789.0, mockContext)
        assertEquals("123,456,789", res)
    }

    @Test
    fun formatNumber_UseCharOf10Numbers() {
        val res = NumberUtil.formatNumber(1234567890.0, mockContext)
        assertEquals("1,234,567,890", res)
    }

    @Test
    fun formatNumber_UseCharOf10NumbersNegative() {
        val res = NumberUtil.formatNumber(-1234567890.0, mockContext)
        assertEquals("-1,234,567,890", res)
    }

    @Test
    fun formatNumber_UseCharOf9NumbersNegative() {
        val res = NumberUtil.formatNumber(-234567890.0, mockContext)
        assertEquals("-234,567,890", res)
    }

    @Test
    fun fastFormatNumber_UseCharOfZero() {
        val res = NumberUtil.fastFormatNumber(0)
        assertEquals("0", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf1Number() {
        val res = NumberUtil.fastFormatNumber(9)
        assertEquals("9", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf3Numbers() {
        val res = NumberUtil.fastFormatNumber(123)
        assertEquals("123", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf4Numbers() {
        val res = NumberUtil.fastFormatNumber(2345)
        assertEquals("2,345", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf6Numbers() {
        val res = NumberUtil.fastFormatNumber(345678)
        assertEquals("345,678", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf7Numbers() {
        val res = NumberUtil.fastFormatNumber(3456789)
        assertEquals("3,456,789", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf8Numbers() {
        val res = NumberUtil.fastFormatNumber(23456789)
        assertEquals("23,456,789", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf9Numbers() {
        val res = NumberUtil.fastFormatNumber(123456789)
        assertEquals("123,456,789", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf10Numbers() {
        val res = NumberUtil.fastFormatNumber(1234567890)
        assertEquals("1,234,567,890", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf10NumbersNegative() {
        val res = NumberUtil.fastFormatNumber(-1234567890)
        assertEquals("-1,234,567,890", res)
    }

    @Test
    fun fastFormatNumber_UseCharOf9NumbersNegative() {
        val res = NumberUtil.fastFormatNumber(-234567890)
        assertEquals("-234,567,890", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOfZero() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(0)
        assertEquals("0 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf1Number() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(9)
        assertEquals("9 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf3Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(123)
        assertEquals("123 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf4Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(2345)
        assertEquals("2,345 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf6Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(345678)
        assertEquals("345,678 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf7Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(3456789)
        assertEquals("3,456,789 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf8Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(23456789)
        assertEquals("23,456,789 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf9Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(123456789)
        assertEquals("123,456,789 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf10Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(1234567890)
        assertEquals("1,234,567,890 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf10NumbersNegative() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(-1234567890)
        assertEquals("-1,234,567,890 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenLarge_UseCharOf9NumbersNegative() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 351
        val res = NumberUtil.fastFormatPossiblePrice(-234567890)
        assertEquals("-234,567,890 đ", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOfZero() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(0)
        assertEquals("0 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf1Number() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(9)
        assertEquals("0 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf3Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(123)
        assertEquals("0 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf4Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(2345)
        assertEquals("2 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf6Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(345678)
        assertEquals("345 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf7Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(3456789)
        assertEquals("3,456 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf8Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(23456789)
        assertEquals("23,456 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf9Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(123456789)
        assertEquals("123,456 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf10Numbers() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(1234567890)
        assertEquals("1,234,567 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf10NumbersNegative() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(-1234567890)
        assertEquals("-1,234,567 K", res)
    }

    @Test
    fun fastFormatPossiblePrice_whenSmall_UseCharOf9NumbersNegative() {
        mockkObject(Utils)
        every { Utils.widthInDp } returns 349
        val res = NumberUtil.fastFormatPossiblePrice(-234567890)
        assertEquals("-234,567 K", res)
    }
}
