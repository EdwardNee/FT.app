package app.ft.ftapp.data.db

import com.squareup.sqldelight.db.SqlDriver

/**
 * Platform specific DatabaseDriverFactory for sqldelight.
 */
expect class DatabaseDriverFactory {
    /**
     * Creates and returns [SqlDriver].
     */
    fun createDriver(): SqlDriver
}