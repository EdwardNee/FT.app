package app.ft.ftapp.utils

import app.ft.ftapp.domain.models.SessionState
import io.ktor.util.date.*
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


    fun convertExpirationDateToMillisUTC(dataEntry: String): Long {
        val parser = GMTDateParser(DATE_FORMAT)
        return DateFormatter().convertDateString(dataEntry, DATE_FORMAT, UTC_PREFIX)
    }

    fun matchDates(currentTime: Long, expirationTime: Long): SessionState {
        val currentDate = DateFormatter().convertDateLong(currentTime, DATE_FORMAT, UTC_PREFIX)
        return when {
            expirationTime == -1L -> SessionState.SessionInvalid
            currentDate < expirationTime -> SessionState.SessionValid
            currentDate > expirationTime -> SessionState.SessionExpired
            else -> SessionState.SessionInvalid
        }
    }

    private const val UTC_PREFIX = "UTC"
    private const val DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z"

}

