package app.ft.ftapp.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Date time formatter class.
 */
actual class DateFormatter {
    /**
     * Returns Long representation of date string.
     */
    actual fun convertDateString(
        timeStamp: String,
        format: String,
        timeZonePrefix: String
    ): Long {
        val sdf = setSimpleDateFormat(format, timeZonePrefix)

        val parseDate = sdf.parse(sdf.format(timeStamp))
        return parseDate?.time ?: -1L
    }

    /**
     * Returns Long representation of date long.
     */
    actual fun convertDateLong(
        timeStamp: Long,
        format: String,
        timeZonePrefix: String
    ): Long {
        val sdf = setSimpleDateFormat(format, timeZonePrefix)

        val parseDate = sdf.parse(sdf.format(timeStamp))
        return parseDate?.time ?: -1L
    }


    private fun setSimpleDateFormat(format: String, timeZonePrefix: String): SimpleDateFormat {
        val sdf = SimpleDateFormat(format, Locale.US)
        sdf.timeZone = TimeZone.getTimeZone(timeZonePrefix)
        return sdf
    }
    /**
     * Gets date string format.
     */
    actual fun getDate(
        dateString: String,
        dateFormat: String,
        datePattern: String
    ): String {
        val parser = SimpleDateFormat(datePattern, Locale.US)
        val formatter = SimpleDateFormat(dateFormat, Locale.US)

        val date =
            if (dateString.isNotEmpty()) parser.parse(dateString) else Calendar.getInstance().time
        return if (date != null) {
            formatter.format(date)
        } else {
            ""
        }
    }
}