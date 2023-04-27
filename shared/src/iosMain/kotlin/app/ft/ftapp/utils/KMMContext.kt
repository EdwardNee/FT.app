package app.ft.ftapp.utils

import platform.Foundation.NSUserActivity
import platform.Foundation.NSUserDefaults
import platform.darwin.NSObject

/**
 * Context ios class.
 */
actual typealias KMMContext = NSObject

//actual class KMMContext
actual fun KMMContext.putInt(key: String, value: Int) {
    NSUserDefaults.standardUserDefaults().setInteger(value.toLong(), key)
}

actual fun KMMContext.getInt(key: String, default: Int): Int {
    return NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
}

actual fun KMMContext.putString(key: String, value: String?) {
    NSUserDefaults.standardUserDefaults.setObject(value, key)
}

actual fun KMMContext.getString(key: String, default: String?): String? {
    return NSUserDefaults.standardUserDefaults.stringForKey(key)
}

actual fun KMMContext.putBoolean(key: String, value: Boolean) {
    NSUserDefaults.standardUserDefaults.setBool(value, key)
}

actual fun KMMContext.getBoolean(key: String, default: Boolean): Boolean {
    return NSUserDefaults.standardUserDefaults.boolForKey(key)
}

actual fun KMMContext.putFloat(key: String, value: Float) {
    NSUserDefaults.standardUserDefaults.setFloat(value, key)
}

actual fun KMMContext.getFloat(key: String, default: Float): Float {
    return NSUserDefaults.standardUserDefaults.floatForKey(key)
}

actual fun KMMContext.putLong(key: String, value: Long) {
    NSUserDefaults.standardUserDefaults.setInteger(value, key)
}

actual fun KMMContext.getLong(key: String, default: Long): Long {
    return NSUserDefaults.standardUserDefaults.integerForKey(key)
}