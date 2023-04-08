package app.ft.ftapp.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * Context android class.
 */
actual typealias KMMContext = Application

const val SP_NAME = "kmm_app"

actual fun KMMContext.putInt(key: String, value: Int) {
    getSharedPrefEditor().putInt(key, value).apply()
}

actual fun KMMContext.getInt(key: String, default: Int): Int {
    return getSharedPref().getInt(key, default)
}

actual fun KMMContext.putString(key: String, value: String?) {
    getSharedPrefEditor().putString(key, value).apply()
}

actual fun KMMContext.getString(key: String, default: String?): String? {
    return getSharedPref().getString(key, default)
}

actual fun KMMContext.putBoolean(key: String, value: Boolean) {
    getSharedPrefEditor().putBoolean(key, value).apply()
}

actual fun KMMContext.getBoolean(key: String, default: Boolean): Boolean {
    return getSharedPref().getBoolean(key, default)
}

actual fun KMMContext.putFloat(key: String, value: Float) {
    getSharedPrefEditor().putFloat(key, value).apply()
}

actual fun KMMContext.getFloat(key: String, default: Float): Float {
    return getSharedPref().getFloat(key, default)
}

actual fun KMMContext.putLong(key: String, value: Long) {
    getSharedPrefEditor().putLong(key, value).apply()
}

actual fun KMMContext.getLong(key: String, default: Long): Long {
    return getSharedPref().getLong(key, default)
}

private fun KMMContext.getSharedPref(): SharedPreferences = this.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
private fun KMMContext.getSharedPrefEditor(): SharedPreferences.Editor = this.getSharedPref().edit()
