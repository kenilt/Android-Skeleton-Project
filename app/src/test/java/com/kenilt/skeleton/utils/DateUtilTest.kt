package com.kenilt.skeleton.utils

import android.content.Context
import android.content.res.Resources
import com.kenilt.skeleton.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Created by thangnguyen on 3/4/19.
 */
class DateUtilTest {
    @Mock
    lateinit var mockContext: Context
    @Mock
    lateinit var mockResources: Resources
    private var nowSecond: Long = 0
    private val clock = TestableClock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockContext.resources).thenReturn(mockResources)
        Mockito.`when`(mockContext.getString(R.string.minute)).thenReturn("phút")
        Mockito.`when`(mockContext.getString(R.string.hour)).thenReturn("giờ")
        Mockito.`when`(mockContext.getString(R.string.ago)).thenReturn("trước")
        Mockito.`when`(mockContext.getString(R.string.just_done)).thenReturn("vừa xong")
        Mockito.`when`(mockContext.getString(R.string.yesterday_at)).thenReturn("Hôm qua lúc")
        Mockito.`when`(mockContext.getString(R.string.default_date_format)).thenReturn("dd/MM/yyyy \'lúc\' HH:mm")
        Mockito.`when`(mockResources.getStringArray(R.array.day_of_week)).thenReturn(arrayOf("Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"))
        Mockito.`when`(mockContext.getString(R.string.at_time)).thenReturn("lúc")

        val timeZone = TimeZone.getTimeZone("Asia/Bangkok")
        DateUtil.defaultTimeZone = timeZone
        val calendar = Calendar.getInstance(timeZone)
        DateUtil.calendar = calendar

        nowSecond = 1551762000L
        InstantClock.clock = clock
        clock.currentMillis = nowSecond * 1000
    }

    @Test
    fun dateFromSeconds_test() {
        val res = DateUtil.dateFromSeconds(nowSecond)
        assertEquals(nowSecond * 1000, res.time)
    }

    @Test
    fun dateStringFromDate_test() {
        val date = Date(nowSecond * 1000)
        val res = DateUtil.dateStringFromDate(date, "dd/MM/yyyy HH:mm:ss")
        assertEquals("05/03/2019 12:00:00", res)
    }

    @Test
    fun dateStringFromSeconds_test() {
        val res = DateUtil.dateStringFromSeconds(nowSecond, "dd/MM/yyyy HH:mm:ss")
        assertEquals("05/03/2019 12:00:00", res)
    }

    @Test
    fun defaultDateStringFromSeconds_test() {
        val res = DateUtil.defaultDateStringFromSeconds(nowSecond)
        assertEquals("05/03/2019 12:00:00 PM", res)
    }

    @Test
    fun defaultShortDateStringFromSeconds_test() {
        val res = DateUtil.defaultShortDateStringFromSeconds(nowSecond)
        assertEquals("05/03/2019", res)
    }

    @Test
    fun getPastTimeString_JustDoneAtZero() {
        val res = DateUtil.getPastTimeString(nowSecond, mockContext)
        assertEquals("vừa xong", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_JustDoneAtZero() {
        val res = DateUtil.getPastTimeString(nowSecond, nowSecond, mockContext)
        assertEquals("vừa xong", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_JustDoneSecond59() {
        val res = DateUtil.getPastTimeString(nowSecond - 59, nowSecond, mockContext)
        assertEquals("vừa xong", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_OneMinuteAgoSecond() {
        val res = DateUtil.getPastTimeString(nowSecond - 60, nowSecond, mockContext)
        assertEquals("1 phút trước", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_59MinutesAgoSecond() {
        val res = DateUtil.getPastTimeString(nowSecond - 3599, nowSecond, mockContext)
        assertEquals("59 phút trước", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_OneHourAgoSecond() {
        val res = DateUtil.getPastTimeString(nowSecond - 3600, nowSecond, mockContext)
        assertEquals("1 giờ trước", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_23HoursAgoSecond() {
        val res = DateUtil.getPastTimeString(nowSecond - 86399, nowSecond, mockContext)
        assertEquals("23 giờ trước", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_YesterdayAtSecond() {
        val res = DateUtil.getPastTimeString(nowSecond - 86400, nowSecond, mockContext)
        assertEquals("Hôm qua lúc 12:00", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_YesterdayAtLastSecond() {
        val res = DateUtil.getPastTimeString(nowSecond - 86401, nowSecond, mockContext)
        assertEquals("Hôm qua lúc 11:59", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_SundayAt23h59() {
        val res = DateUtil.getPastTimeString(nowSecond - 129601, nowSecond, mockContext)
        assertEquals("Chủ nhật lúc 23:59", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_SundayAt12h00() {
        val res = DateUtil.getPastTimeString(nowSecond - 172800, nowSecond, mockContext)
        assertEquals("Chủ nhật lúc 12:00", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_WednesdayAt12h00() {
        val res = DateUtil.getPastTimeString(nowSecond - 518400, nowSecond, mockContext)
        assertEquals("Thứ tư lúc 12:00", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_WednesdayAt00h00() {
        val res = DateUtil.getPastTimeString(nowSecond - 561600, nowSecond, mockContext)
        assertEquals("Thứ tư lúc 00:00", res)
    }

    @Test
    fun getPastTimeString_AtMidDay_OldDay() {
        val res = DateUtil.getPastTimeString(nowSecond - 864001, nowSecond, mockContext)
        assertEquals("23/02/2019 lúc 11:59", res)
    }
}
