package app.ft.ftapp.utils

/**
 * Class for date formatting.
 */
expect class DateFormatter() {
    /**
     * Returns Long representation of date string.
     */
    fun convertDateString(
        timeStamp: String,
        format: String,
        timeZonePrefix: String
    ): Long

    /**
     * Returns Long representation of date long.
     */
    fun convertDateLong(
        timeStamp: Long,
        format: String,
        timeZonePrefix: String
    ): Long

    /**
     * Gets date string format.
     */
    fun getDate(
        dateString: String,
        dateFormat: String,
        datePattern: String
    ): String
}