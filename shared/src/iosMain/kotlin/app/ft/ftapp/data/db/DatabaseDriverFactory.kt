package app.ft.ftapp.data.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import db.FTAppDatabase

/**
 * Android specific DatabaseDriverFactory for sqldelight.
 */
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(FTAppDatabase.Schema, "app.db")
    }
}