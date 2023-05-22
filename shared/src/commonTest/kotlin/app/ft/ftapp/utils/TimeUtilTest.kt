package app.ft.ftapp.utils

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Testing [TimeUtil] companion class.
 */
internal class TimeUtilTest {
    @Test
    fun testing_converting_string_date_to_LocalDateTime() {
        val expectedDate = LocalDateTime(
            year = 2020,
            monthNumber = 8,
            dayOfMonth = 30,
            hour = 18,
            minute = 43,
            second = 0,
            nanosecond = 123456789
        )
        val date = TimeUtil.fromStrToDate("2020-08-30T18:43:00.123456789")

        assertEquals(expectedDate, date)
    }

    @Test
    fun testing_converting_fromDateToString() {
        val date = LocalDateTime(
            year = 2020,
            monthNumber = 8,
            dayOfMonth = 30,
            hour = 18,
            minute = 43,
            second = 0,
            nanosecond = 123456789
        )
        val result = TimeUtil.fromDateToString(date)

        assertEquals("2020-08-30T18:43:00.123456789", result)
    }
}