package app.ft.ftapp.data.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import db.FTAppDatabase

/**
 * Android specific DatabaseDriverFactory for sqldelight.
 */
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(FTAppDatabase.Schema, context, "app.db")
    }
}