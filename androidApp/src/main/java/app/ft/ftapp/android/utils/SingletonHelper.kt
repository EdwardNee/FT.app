package app.ft.ftapp.android.utils

import app.ft.ftapp.android.ui.navigation.AppNavigatorImpl

/**
 * Singleton to process application instances.
 */
object SingletonHelper {
    val appNavigator = AppNavigatorImpl()
}