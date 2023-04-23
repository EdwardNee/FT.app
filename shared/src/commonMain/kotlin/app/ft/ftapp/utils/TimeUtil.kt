package app.ft.ftapp.utils

import kotlinx.datetime.*

/**
 * kotlinx.datetime time util converter.
 */
object TimeUtil {

    /**
     * Converts string [ioString] to [LocalDateTime] in ISO-8601.
     */
    fun fromStrToDate(ioString: String): LocalDateTime {
        return ioString.toLocalDateTime()
    }

    /**
     * Converts [LocalDateTime] to [String].
     */
    fun fromDateToString(date: LocalDateTime): String {
        return date.toString()
//        return LocalDate.parse()
    }

    /**
     * Calculates the applicable date for the given [hour] and [minute].
     */
    fun dateFormatProcess(hour: Int, minute: Int): String {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val time = LocalTime(hour, minute)

        //Entered not today date
        return LocalDateTime(
            year = currentDate.year,
            month = currentDate.month,
            dayOfMonth = if (currentDate.time > time) currentDate.dayOfMonth + 1 else currentDate.dayOfMonth,
            hour = hour,
            minute = minute
        ).toString()
    }
}