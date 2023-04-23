package app.ft.ftapp.android.utils

import app.ft.ftapp.android.utils.TimeUtil.ISO_8601_24H_FULL_FORMAT
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.ceil

/**
 * Date time class to calculate periods of time.
 */
object TimeUtil {

    const val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm"
    var formatter: DateFormat = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
    val dateTimeFormatter = DateTimeFormatter.ofPattern(ISO_8601_24H_FULL_FORMAT)

    /**
     * Counts minutes left until [until] event.
     */
    fun getMinutesLeft(until: Date, fromDate: Long = Date().time): Long {
        return ceil((until.time - fromDate) / 60_000.0).toLong()
    }

    /**
     * Counts minutes left until [until] event.
     */
    fun getMinutesLeft(fromDate: Long = Date().time, until: Long): Long {
        return ceil((until - fromDate) / 60_000.0).toLong()
    }

    /**
     * Counts minutes left until [until] event.
     */
    fun getMinutesLeft(fromDate: Long = Date().time, until: String?): Long {
        val untilDate = until?.toDate()?.atZone(ZoneId.systemDefault())
            ?.toInstant()
            ?.toEpochMilli() ?: 0
        return ceil((untilDate - fromDate) / 60_000.0).toLong()
    }

    /**
     * converts given date to String.
     */
    fun dateToString(date: LocalDateTime): String {
        return date.format(dateTimeFormatter)
    }

    fun toStringDateParser(startTime: String?): String {
        val date = startTime?.toDate() ?: return ""
        val current = LocalDateTime.now()

        val month = date.format(DateTimeFormatter.ofPattern("MMMM"))

        //next day
        return if (date.dayOfMonth > current.dayOfMonth) {
            val resTime = date.format(DateTimeFormatter.ofPattern("HH:mm"))
            "${date.dayOfMonth} $month в $resTime"
        } else {
            val resTime = date.format(DateTimeFormatter.ofPattern("HH:mm"))
            "сегодня в $resTime"
        }
    }
}

/**
 * Extention method to convert string to date of [TimeUtil] formatter with [ISO_8601_24H_FULL_FORMAT] pattern.
 */
fun String.toDate(): LocalDateTime {
    val date = LocalDateTime.parse(this, TimeUtil.dateTimeFormatter)//TimeUtil.formatter.parse(this)
    return date
}