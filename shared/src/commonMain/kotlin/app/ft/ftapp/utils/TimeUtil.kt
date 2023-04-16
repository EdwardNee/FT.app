package app.ft.ftapp.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

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
}