package app.ft.ftapp.android.utils

import app.ft.ftapp.android.utils.TimeUtil.ISO_8601_24H_FULL_FORMAT
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

/**
 * Date time class to calculate periods of time.
 */
object TimeUtil {
    const val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    var formatter: DateFormat = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)

    /**
     * Counts minutes left until [until] event.
     */
    fun getMinutesLeft(fromDate: Long = Date().time, until: Date): Long {
        return ceil((until.time - fromDate) / 60_000.0).toLong()
    }

    /**
     * Counts minutes left until [until] event.
     */
    fun getMinutesLeft(fromDate: Long = Date().time, until: Long): Long {
        return ceil((until - fromDate) / 60_000.0).toLong()
    }
}

/**
 * Extention method to convert string to date of [TimeUtil] formatter with [ISO_8601_24H_FULL_FORMAT] pattern.
 */
fun String.toDate(): Date {
    var date: Date = TimeUtil.formatter.parse(this)
    return date
}