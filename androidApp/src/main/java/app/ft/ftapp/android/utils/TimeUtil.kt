package app.ft.ftapp.android.utils

import java.util.*
import kotlin.math.ceil

/**
 * Date time class to calculate periods of time.
 */
object TimeUtil {
    /**
     * Counts minutes left until [until] event.
     */
    fun getMinutesLeft(until: Date): Long {
        return ceil((until.time - Date().time) / 60_000.0).toLong()
    }

    /**
     * Counts minutes left until [until] event.
     */
    fun getMinutesLeft(until: Long): Long {
        return ceil((until - Date().time) / 60_000.0).toLong()
    }
}

