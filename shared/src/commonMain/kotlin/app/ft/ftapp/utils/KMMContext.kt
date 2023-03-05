package app.ft.ftapp.utils

// reference https://medium.com/@shmehdi01/shared-preference-in-kmm-kotlin-multiplatform-2bca14214093
/**
 * KMM context class.
 */
expect class KMMContext

expect fun KMMContext.putInt(key: String, value: Int)
expect fun KMMContext.getInt(key: String, default: Int): Int
expect fun KMMContext.putString(key: String, value: String?)
expect fun KMMContext.getString(key: String, default: String?): String?
expect fun KMMContext.putBoolean(key: String, value: Boolean)
expect fun KMMContext.getBoolean(key: String, default: Boolean): Boolean
expect fun KMMContext.putFloat(key: String, value: Float)
expect fun KMMContext.getFloat(key: String, default: Float): Float
expect fun KMMContext.putLong(key: String, value: Long)
expect fun KMMContext.getLong(key: String, default: Long): Long
