package com.kenilt.skeleton.utils

import android.content.Context
import com.kenilt.skeleton.R
import org.apache.commons.lang3.time.FastDateFormat
import java.util.*


/**
 * Created by neal on 2/11/17.
 */

object DateUtil {

    var calendar: Calendar = Calendar.getInstance()
    var defaultTimeZone: TimeZone = TimeZone.getDefault()

    private fun getDateFormat(format: String): FastDateFormat {
        return FastDateFormat.getInstance(format, defaultTimeZone, Locale.US)
    }

    fun dateFromSeconds(seconds: Long): Date {
        calendar.timeInMillis = seconds * 1000
        return calendar.time
    }

    fun dateStringFromDate(date: Date, format: String): String {
        return getDateFormat(format).format(date)
    }

    fun dateStringFromSeconds(seconds: Long, format: String): String {
        val date = dateFromSeconds(seconds)
        return getDateFormat(format).format(date)
    }

    fun defaultDateStringFromSeconds(seconds: Long): String {
        return dateStringFromSeconds(seconds, "dd/MM/yyy hh:mm:ss a")
    }

    fun defaultShortDateStringFromSeconds(seconds: Long): String {
        return dateStringFromSeconds(seconds, "dd/MM/yyy")
    }

    fun getPastTimeString(seconds: Long, context: Context): String {
        val now = InstantClock.currentTimeMillis() / 1000
        return getPastTimeString(seconds, now, context)
    }

    fun getPastTimeString(seconds: Long, currentSeconds: Long, context: Context): String {
        val secondOffset = currentSeconds - seconds

        val minute = context.getString(R.string.minute)
        val hour = context.getString(R.string.hour)
        val ago = context.getString(R.string.ago)

        return when {
            secondOffset < 60 -> context.getString(R.string.just_done)
            secondOffset < 60 * 60 -> (secondOffset / 60).toString() + " " + minute + " " + ago
            secondOffset < 60 * 60 * 24 -> (secondOffset / 60 / 60).toString() + " " + hour + " " + ago
            else -> getPastTimeAgoCaseLargerThanDay(currentSeconds, seconds, context)
        }
    }

    private fun getPastTimeAgoCaseLargerThanDay(currentSeconds: Long, seconds: Long, context: Context): String {
        calendar.timeInMillis = currentSeconds * 1000
        val nowDay = calendar.get(Calendar.DAY_OF_YEAR) + 365 * calendar.get(Calendar.YEAR)

        calendar.timeInMillis = seconds * 1000
        val day = calendar.get(Calendar.DAY_OF_YEAR) + 365 * calendar.get(Calendar.YEAR)

        val dayOffset = nowDay - day
        return when {
            dayOffset <= 1 -> context.getString(R.string.yesterday_at) + " " + dateStringFromSeconds(seconds, "HH:mm")
            dayOffset < 7 -> {
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                val dayOfWeekTexts = context.resources.getStringArray(R.array.day_of_week)
                val atTime = context.getString(R.string.at_time)
                String.format("%s %s %s", dayOfWeekTexts[dayOfWeek - 1], atTime,
                        dateStringFromSeconds(seconds, "HH:mm"))
            }
            else -> dateStringFromSeconds(seconds, context.getString(R.string.default_date_format))
        }
    }
}
